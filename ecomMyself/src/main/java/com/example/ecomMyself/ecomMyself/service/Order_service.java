package com.example.ecomMyself.ecomMyself.service;

import com.example.ecomMyself.ecomMyself.Coupons.Service.CouponService;
import com.example.ecomMyself.ecomMyself.DTO.*;
import com.example.ecomMyself.ecomMyself.model.*;
import com.example.ecomMyself.ecomMyself.DTO.*;
import com.example.ecomMyself.ecomMyself.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private User_Repo userRepo;
    @Autowired
    private CouponService couponService;
    @Autowired
    private OrderSummaryService orderSummaryService;


    public Cart_response[] cart(int id) {
        List<Cart> list= cartRepo.findAllByUserId(id);
        Cart_response cartResponse[]=new Cart_response[list.size()];
        for(int i=0;i<list.size();i++)
        {
            Cart c=list.get(i);

            Product product=c.getProduct();
            String name=product.getName();

            BigDecimal total=BigDecimal.valueOf(c.getQuantity()).multiply(product.getPrice());

            Optional<Product_size> productSize=productSizeRepo.findBySizeAndProductColors_ColorAndProductColors_Product_Id(c.getSize(),c.getColor(),product.getId());
            boolean isStockAvailable=productSize.get().getQuantity()-productSize.get().getReserved()>=c.getQuantity()?true:false;

            cartResponse[i]=new Cart_response(product.getId(),name,c.getColor(),c.getSize(),c.getQuantity(),total,isStockAvailable);
        }
        return cartResponse;
    }
    @Transactional
    public void placeOrder(Users user,String razorpayPaymentId)
    {
        System.out.println("placing order..");
        List<Cart> cart=cartRepo.findAllByUserId(user.getId());
        OrderSummary orderSummary=orderSummaryService.getOrderSummary(user);
        Orders o=new Orders();

        String orderId;
        do {
            orderId = "ORD" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
        } while(ordersRepo.existsByOrderId(orderId));

        o.setOrderId(orderId);
        o.setUser(user);
        o.setOrderDate(LocalDate.now());
        o.setStatus("Order Placed");
        o.setRazorpayPaymentId(razorpayPaymentId);
        if(orderSummary.getCoupon()!=null)
            o.setCouponCodeApplied(orderSummary.getCoupon().getCode());
        o.setDiscount(orderSummary.getDiscount());
        o.setTotal(orderSummary.getDiscountedTotal());
        o.setAddress(orderSummary.getAddress());
        o.setDiscount(orderSummary.getDiscount());
        o.setDiscountedTotal(orderSummary.getDiscountedTotal());

        List<Order_items> orderItems=new ArrayList<>();
        for(Cart c:cart)
        {
            Order_items orderItems1=new Order_items();
            orderItems1.setOrders(o);
            orderItems1.setProduct(c.getProduct());
            orderItems1.setSize(c.getSize());
            orderItems1.setColor(c.getColor());
            orderItems1.setQuantity(c.getQuantity());

            Product_size productSize=productSizeRepo.findBySizeAndProductColors_ColorAndProductColors_Product_Id(c.getSize(),c.getColor(),c.getProduct().getId()).orElseThrow(() -> new RuntimeException("Product not Found"));
            productSize.setQuantity(productSize.getQuantity()-c.getQuantity());
            productSizeRepo.save(productSize);

            orderItems.add(orderItems1);
        }
        o.setOrderItems(orderItems);

        ordersRepo.save(o);
        cartRepo.deleteAllByUserId(user.getId());
        orderSummaryService.orderPlaced(user);
    }

//    @Transactional
//    public void placeOrder(Users user,String razorpayPaymentId)
//    {
//        System.out.println("placing order..");
//        List<Cart> c=cartRepo.findAllByUserId(user.getId());
//        List<Order_item_request> orderItemRequestList=new ArrayList<>();
//        for(Cart items:c)
//        {
//            orderItemRequestList.add(new Order_item_request(items.getProductId(),items.getColor(),items.getSize(),items.getQuantity()));
//        }
//        Order_request request=new Order_request(orderItemRequestList);
//        Orders o=new Orders();
//        String orderId;
//        do {
//            orderId = "ORD" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
//        } while(ordersRepo.existsByOrderId(orderId));
//
//        o.setOrderId(orderId);
//        o.setUserId(user.getId());
//        o.setOrderDate(LocalDate.now());
//        o.setStatus("Order Placed");
//        o.setUserId(user.getId());
//        o.setRazorpayPaymentId(razorpayPaymentId);
//        List<Order_items> oi=new ArrayList<>();
//        BigDecimal total=BigDecimal.ZERO;
//        for(Order_item_request oir:request.orderItemRequests())
//        {
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
//                int updated = productSizeRepo.reduceStock(
//                        oir.productid(),
//                        oir.color(),
//                        oir.size(),
//                        oir.quantity()
//                );
//
//        }
//        o.setTotal(total);
//        o.setOrderItems(oi);
//
//        OrderSummary orderSummary=orderSummaryService.getOrderSummary(user);
//        o.setAddress(orderSummary.getAddress());
//        o.setDiscount(orderSummary.getDiscount());
//        o.setDiscountedTotal(orderSummary.getDiscountedTotal());
//        if(orderSummary.getCoupon()!=null)
//            o.setCouponCodeApplied(orderSummary.getCoupon().getCode());
//
//        ordersRepo.save(o);
//        cartRepo.deleteAllByUserId(user.getId());
//        orderSummaryService.orderPlaced(user);
//    }


    public List<Order_response> MyOrders(Users user) {

        List<Orders> orders=ordersRepo.findAllByUserId(user.getId());
        System.out.println(user.getUsername()+" "+orders.size());
//        if(orders.size()==0)
//            throw new RuntimeException("No Orders placed");
        List<Order_response> orderResponseList=new ArrayList<>();
        for(Orders o:orders)
        {
            String orderid=o.getOrderId();
            String status=o.getStatus();
            LocalDate orderDate=o.getOrderDate();
            BigDecimal total=o.getDiscountedTotal();
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
        BigDecimal total=o.getDiscountedTotal();
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

    @Transactional
    public void AddToCart(Users user,Order_item_request orderItemRequest)
    {
        Optional<Product> p=productRepo.findById(orderItemRequest.productid());
        Cart cp=cartRepo.findByUserIdAndProductIdAndColorAndSize(user.getId(),orderItemRequest.productid(),orderItemRequest.color(),orderItemRequest.size());
        Cart c= cp==null?new Cart(user,p.get(),orderItemRequest.color(),orderItemRequest.size(),0,true):cp;

        Optional<Product_size> productSize=productSizeRepo.findBySizeAndProductColors_ColorAndProductColors_Product_Id(orderItemRequest.size(),orderItemRequest.color(),orderItemRequest.productid());

        if(productSize.isEmpty() || productSize.get().getQuantity()<=0)
            throw new RuntimeException("Out Of Stock");

        c.setQuantity(c.getQuantity()+1);
        user.setCartValue(user.getCartValue().add(p.get().getPrice()));
        userRepo.save(user);
        cartRepo.save(c);
        orderSummaryService.refresh(user);
    }

    @Transactional
    public void RemoveFromCart(Users user,Order_item_request orderItemRequest) {

        Optional<Product> p=productRepo.findById(orderItemRequest.productid());
        Cart cp=cartRepo.findByUserIdAndProductIdAndColorAndSize(user.getId(),orderItemRequest.productid(),orderItemRequest.color(),orderItemRequest.size());
        if(cp==null || cp.getQuantity()==0)
            throw new RuntimeException("Not in Cart");
        Cart c=cp;

        c.setQuantity(c.getQuantity()-1);
        user.setCartValue(user.getCartValue().subtract(p.get().getPrice()));

        if(c.getQuantity()==0)
            cartRepo.delete(c);
        else {
            cartRepo.save(c);
        }

        userRepo.save(user);
        orderSummaryService.refresh(user);
    }
    public String getLatestOrderId(Users user) {

        List<Orders> orders=ordersRepo.findAllByUserId(user.getId());
        return orders.get(orders.size()-1).getOrderId();

    }
}
