package com.example.ecomMyself.ecomMyself.service;

import com.example.ecomMyself.ecomMyself.model.*;
import com.example.ecomMyself.ecomMyself.model.DTO.*;
import com.example.ecomMyself.ecomMyself.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class Order_service {
    @Autowired
    private Orders_repo ordersRepo;
    @Autowired
    private Product_Repo productRepo;
    @Autowired
    private Product_colors_repo productColorsRepo;
    @Autowired
    private Product_size_repo productSizeRepo;
    @Autowired
    private Cart_repo cartRepo;

    public Cart_response[] cart(int id) {
        List<Cart> list= cartRepo.findAllByUserId(id);
        Cart_response cartResponse[]=new Cart_response[list.size()];
        for(int i=0;i< list.size();i++)
        {
            Cart c=list.get(i);
            Optional<Product> product=productRepo.findById(c.getProductId());
            String name=product.get().getName();
            BigDecimal total=BigDecimal.valueOf(c.getQuantity()).multiply(product.get().getPrice());

            cartResponse[i]=new Cart_response(product.get().getId(),name,c.getColor(),c.getSize(),c.getQuantity(),total);
        }
        return cartResponse;
    }

//    @Transactional
//    public void placeOrder()
//    {
//        List<Cart> c=cartRepo.findAllByUserId(0);
//        List<Order_item_request> orderItemRequestList=new ArrayList<>();
//        for(Cart items:c)
//        {
//            orderItemRequestList.add(new Order_item_request(items.getProductId(),items.getColor(),items.getSize(),items.getQuantity()));
//        }
//        Order_request request=new Order_request(orderItemRequestList);
//        Orders o=new Orders();
////        o.setUserId(principal.getUser().getId());
//        BigDecimal total= BigDecimal.valueOf(0);
//        String orderId = "ORD" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
//        o.setOrderId(orderId);
//        o.setOrderDate(LocalDate.now());
//        o.setStatus("Order Placed");
//        List<Order_items> oi=new ArrayList<>();
//        for(Order_item_request oir:request.orderItemRequests())
//        {
//            System.out.println(oir.productid()+" "+oir.color()+" "+oir.size()+" "+oir.quantity());
//            Order_items oi2=new Order_items();
//            Product product=productRepo.findById(oir.productid())
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//            Optional<Product_size> productSize=productSizeRepo.findBySizeAndProductColors_ColorAndProductColors_Product_Id(oir.size(),oir.color(),oir.productid());
//
//            if(productSize.isEmpty())
//                throw new RuntimeException("Out Of Stock");
//            if(productSize.get().getQuantity()< oir.quantity())
//                throw new RuntimeException("Only "+productSize.get().getQuantity()+ " pieces are left for " + product.getName());
//
//            oi2.setColor(oir.color());
//            oi2.setSize(oir.size());
//            oi2.setQuantity(oir.quantity());
//            oi2.setProduct(product);
//            oi2.setOrders(o);
//            total=total.add((product.getPrice().multiply(BigDecimal.valueOf(oir.quantity()))));
//            oi.add(oi2);
//
//            int updated = productSizeRepo.reduceStock(
//                    oir.productid(),
//                    oir.color(),
//                    oir.size(),
//                    oir.quantity()
//            );
//        }
//        o.setTotal(total);
//        o.setOrderItems(oi);
//        ordersRepo.save(o);
//        cartRepo.deleteAllByUserId(0);
//
//    }


    @Transactional
    public void placeOrder(UserPrincipal principal)
    {
        List<Cart> c=cartRepo.findAllByUserId(principal.getUser().getId());
        List<Order_item_request> orderItemRequestList=new ArrayList<>();
        for(Cart items:c)
        {
            orderItemRequestList.add(new Order_item_request(items.getProductId(),items.getColor(),items.getSize(),items.getQuantity()));
        }
        Order_request request=new Order_request(orderItemRequestList);
        Orders o=new Orders();
        String orderId = "ORD" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
        o.setOrderId(orderId);
        o.setUserId(principal.getUser().getId());
        o.setOrderDate(LocalDate.now());
        o.setStatus("Order Placed");
        o.setUserId(principal.getUser().getId());
        List<Order_items> oi=new ArrayList<>();
        BigDecimal total=BigDecimal.ZERO;
        for(Order_item_request oir:request.orderItemRequests())
        {
            Order_items oi2=new Order_items();
            Product product=productRepo.findById(oir.productid())
                .orElseThrow(() -> new RuntimeException("Product not found"));

            Optional<Product_size> productSize=productSizeRepo.findBySizeAndProductColors_ColorAndProductColors_Product_Id(oir.size(),oir.color(),oir.productid());

            if(productSize.isEmpty())
                throw new RuntimeException("Out Of Stock");
            if(productSize.get().getQuantity()< oir.quantity())
                throw new RuntimeException("Only "+productSize.get().getQuantity()+ " pieces are left for " + product.getName());

            oi2.setColor(oir.color());
            oi2.setSize(oir.size());
            oi2.setQuantity(oir.quantity());
            oi2.setProduct(product);
            oi2.setOrders(o);
            total=total.add((product.getPrice().multiply(BigDecimal.valueOf(oir.quantity()))));
            oi.add(oi2);

                int updated = productSizeRepo.reduceStock(
                        oir.productid(),
                        oir.color(),
                        oir.size(),
                        oir.quantity()
                );

        }
        o.setTotal(total);
        o.setOrderItems(oi);
        ordersRepo.save(o);
        cartRepo.deleteAllByUserId(principal.getUser().getId());
    }
//    public List<Order_response> MyOrders() {
//
//        List<Orders> orders=ordersRepo.findAllByUserId(0);
//        if(orders.size()==0)
//            throw new RuntimeException("No Orders placed");
//        List<Order_response> orderResponseList=new ArrayList<>();
//        for(Orders o:orders)
//        {
//            String orderid=o.getOrderId();
//            String status=o.getStatus();
//            LocalDate orderDate=o.getOrderDate();
//            BigDecimal total=o.getTotal();
//            List<Order_item_response> orderItemResponseList=new ArrayList<>();
//            for(Order_items o2:o.getOrderItems())
//            {
//                String productName=o2.getProduct().getName();
//                int quantity=o2.getQuantity();
//                BigDecimal total2=o2.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity));
//                Order_item_response orderItemResponse=new Order_item_response(productName,quantity,o2.getColor(),o2.getSize(),total2);
//                orderItemResponseList.add(orderItemResponse);
//            }
//            Order_response orderResponse=new Order_response(o.getId(),orderid,status,orderDate,orderItemResponseList,total);
//            orderResponseList.add(orderResponse);
//        }
//        return (orderResponseList);
//    }


    public List<Order_response> MyOrders(UserPrincipal principal) {

        List<Orders> orders=ordersRepo.findAllByUserId(principal.getUser().getId());
        System.out.println(principal.getUser().getUsername()+" "+orders.size());
//        if(orders.size()==0)
//            throw new RuntimeException("No Orders placed");
        List<Order_response> orderResponseList=new ArrayList<>();
        for(Orders o:orders)
        {
            String orderid=o.getOrderId();
            String status=o.getStatus();
            LocalDate orderDate=o.getOrderDate();
            BigDecimal total=o.getTotal();
            List<Order_item_response> orderItemResponseList=new ArrayList<>();
            for(Order_items o2:o.getOrderItems())
            {
                String productName=o2.getProduct().getName();
                int quantity=o2.getQuantity();
                BigDecimal total2=o2.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity));
                Order_item_response orderItemResponse=new Order_item_response(productName,quantity,o2.getColor(),o2.getSize(),total2);
                orderItemResponseList.add(orderItemResponse);
            }
            Order_response orderResponse=new Order_response(o.getId(),orderid,status,orderDate,orderItemResponseList,total);
//            Order_response orderResponse=new Order_response(orderid,customerName,status,orderDate,orderItemResponseList,total);
            orderResponseList.add(orderResponse);
        }
        return (orderResponseList);
    }
    public Order_response myOrderId(int id)
    {
        Orders o=ordersRepo.findById(id).orElseThrow(()->new RuntimeException("No Such Order"));
        String orderId=o.getOrderId();
        String status=o.getStatus();
        LocalDate date=o.getOrderDate();
        BigDecimal total=o.getTotal();
        List<Order_item_response> orderItemResponseList=new ArrayList<>();
        for(Order_items o2:o.getOrderItems())
        {
            String productName=o2.getProduct().getName();
            int quantity=o2.getQuantity();
            BigDecimal total2=o2.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity));
            Order_item_response orderItemResponse=new Order_item_response(productName,quantity,o2.getColor(),o2.getSize(),total2);
            orderItemResponseList.add(orderItemResponse);
        }
        Order_response orderResponse=new Order_response(id,orderId,status,date,orderItemResponseList,total);
        return orderResponse;
    }
//    @Transactional
//    public void AddToCart(Order_item_request orderItemRequest)
//    {
//        Cart cp=cartRepo.findByUserIdAndProductIdAndColorAndSize(0,orderItemRequest.productid(),orderItemRequest.color(),orderItemRequest.size());
//        Cart c= cp==null?new Cart():cp;
//        c.setUserId(0);
//        c.setColor(orderItemRequest.color());
//        c.setSize(orderItemRequest.size());
//        Optional<Product_size> productSize=productSizeRepo.findBySizeAndProductColors_ColorAndProductColors_Product_Id(orderItemRequest.size(),orderItemRequest.color(),orderItemRequest.productid());
//        if(productSize.isEmpty())
//            throw new RuntimeException("Out Of Stock");
//        c.setQuantity(c.getQuantity()+1);
//        int quantity_available=productSize.get().getQuantity();
//        if(c.getQuantity()>quantity_available)
//            throw new RuntimeException("Limited quantity available");
//        c.setProductid(orderItemRequest.productid());
//        cartRepo.save(c);
//    }

    @Transactional
    public void AddToCart(UserPrincipal principal,Order_item_request orderItemRequest)
    {
        Cart cp=cartRepo.findByUserIdAndProductIdAndColorAndSize(principal.getUser().getId(),orderItemRequest.productid(),orderItemRequest.color(),orderItemRequest.size());
        Cart c= cp==null?new Cart():cp;
        c.setUserId(principal.getUser().getId());
        c.setColor(orderItemRequest.color());
        c.setSize(orderItemRequest.size());
        Optional<Product_size> productSize=productSizeRepo.findBySizeAndProductColors_ColorAndProductColors_Product_Id(orderItemRequest.size(),orderItemRequest.color(),orderItemRequest.productid());
        if(productSize.isEmpty())
            throw new RuntimeException("Out Of Stock");
        c.setQuantity(c.getQuantity()+1);
        int quantity_available=productSize.get().getQuantity();
        if(c.getQuantity()>quantity_available)
            throw new RuntimeException("Not Availaible");
        c.setProductid(orderItemRequest.productid());
        cartRepo.save(c);
    }
//    @Transactional
//    public void RemoveFromCart(Order_item_request orderItemRequest) {
//        Cart cp=cartRepo.findByUserIdAndProductIdAndColorAndSize(0,orderItemRequest.productid(),orderItemRequest.color(),orderItemRequest.size());
//        if(cp==null)
//            throw new RuntimeException("Not in Cart");
//        Cart c=cp;
//        c.setUserId(0);
//        c.setColor(orderItemRequest.color());
//        c.setSize(orderItemRequest.size());
//        if(c.getQuantity()==0)
//            throw new RuntimeException("Not Applicable");
//        c.setQuantity(c.getQuantity()-1);
//        if(c.getQuantity()==0)
//            cartRepo.delete(c);
//        else {
//            c.setProductid(orderItemRequest.productid());
//            cartRepo.save(c);
//        }
//    }
    @Transactional
    public void RemoveFromCart(UserPrincipal principal,Order_item_request orderItemRequest) {
        Cart cp=cartRepo.findByUserIdAndProductIdAndColorAndSize(principal.getUser().getId(),orderItemRequest.productid(),orderItemRequest.color(),orderItemRequest.size());
        if(cp==null)
            throw new RuntimeException("Not in Cart");
        Cart c=cp;
        c.setUserId(principal.getUser().getId());
        c.setColor(orderItemRequest.color());
        c.setSize(orderItemRequest.size());
        if(c.getQuantity()==0)
            throw new RuntimeException("Not Applicable");
        c.setQuantity(c.getQuantity()-1);
        if(c.getQuantity()==0)
            cartRepo.delete(c);
        else {
            c.setProductid(orderItemRequest.productid());
            cartRepo.save(c);
        }
    }
}
