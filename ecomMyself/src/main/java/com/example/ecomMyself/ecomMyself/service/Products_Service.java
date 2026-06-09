package com.example.ecomMyself.ecomMyself.service;

import com.example.ecomMyself.ecomMyself.model.DTO.*;
import com.example.ecomMyself.ecomMyself.model.Product;
import com.example.ecomMyself.ecomMyself.model.Product_colors;
import com.example.ecomMyself.ecomMyself.model.Product_size;
import com.example.ecomMyself.ecomMyself.repository.Product_Repo;
import com.example.ecomMyself.ecomMyself.repository.Product_colors_repo;
import com.example.ecomMyself.ecomMyself.repository.Product_size_repo;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.ecomMyself.ecomMyself.model.DTO.Individual_Product_Response;
import com.example.ecomMyself.ecomMyself.model.DTO.Individual_Product_Colors_Response;
import com.example.ecomMyself.ecomMyself.model.DTO.Individual_Product_Size_Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class Products_Service {
    @Autowired
    private Product_Repo product_repo;

    public Product AddProducts(AddProduct_request addProductRequest, MultipartFile image) throws IOException
    {
        String name=addProductRequest.name();
        String type=addProductRequest.Type();
        String fit=addProductRequest.fit();
        BigDecimal price=addProductRequest.price();
        String description=addProductRequest.description();
        String gender=addProductRequest.gender();
        String color=addProductRequest.color();
        Optional<Product> product=product_repo.findByNameAndTypeAndFitAndPriceAndDescriptionAndGender(name,type,fit,price,description,gender);
        if(!product.isEmpty())
        {
            System.out.println("contains same product");
            List<Product_colors> productColors=product.get().getColor();
            Product_colors c=new Product_colors();
            List<Product_size> productSizes=new ArrayList<>();
            boolean color_present=false;
            for(Product_colors colors:productColors)
            {
                if(colors.getColor().equals(color))
                {
                    c=colors;
                    productSizes=colors.getSize();
                    color_present=true;
                    break;
                }
            }
            HashMap<Integer,Product_size> sizespresent=new HashMap<>();
            for(Product_size ps:productSizes)
            {
                sizespresent.put(ps.getSize(),ps);
            }
            c.setColor(color);
            for(AddProductSize_request sizes:addProductRequest.addProductSizeRequest())
            {
                if(!sizespresent.containsKey(sizes.size())) {
                    Product_size product_size = new Product_size();
                    product_size.setSize(sizes.size());
                    product_size.setQuantity(sizes.quantity());
                    product_size.setProductColors(c);
                    productSizes.add(product_size);
                }
                else
                {
                    Product_size product_size=sizespresent.get(sizes.size());
                    product_size.setQuantity(product_size.getQuantity()+sizes.quantity());
                }
            }
            if(image!=null && !image.isEmpty())
                c.setPicture(image.getBytes());
            c.setSize(productSizes);
            if(!color_present)
                productColors.add(c);
            c.setProduct(product.get());
            product.get().setColor(productColors);
            return product_repo.save(product.get());
        }
        else
        {
            System.out.println("New Product "+addProductRequest.toString());
            Product p=new Product();
            p.setName(name);
            p.setDescription(description);
            p.setFit(fit);
            p.setGender(gender);
            p.setType(type);
            p.setPrice(price);
            Product_colors c=new Product_colors();
            c.setColor(color);
            List<Product_size> productSizes=new ArrayList<>();
            for(AddProductSize_request sizes:addProductRequest.addProductSizeRequest())
            {
                Product_size product_size=new Product_size();
                product_size.setSize(sizes.size());
                product_size.setQuantity(sizes.quantity());
                product_size.setProductColors(c);
                productSizes.add(product_size);
            }
            if(image!=null && !image.isEmpty()) {
                c.setPicture(image.getBytes());
            }
            c.setSize(productSizes);
            c.setProduct(p);
            p.setColor(List.of(c));
            return product_repo.save(p);
        }
    }

    public Page<Product_Response> getAll(int page,int size)
    {
//        System.out.println("service : "+page+" "+size);
        Pageable pageable= ((Pageable) PageRequest.of(page,size));
        Page<Product> products=product_repo.findAll(pageable);
        return products.map(p ->
                new Product_Response(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        "/Product/image/" + p.getId()
                )
        );
    }

    public Page<Product_Response> getMenAll(int page,int size) {
        Pageable pageable= ((Pageable) PageRequest.of(page,size));
        Page<Product> products=product_repo.findAllByGender("Men",pageable);
        return products.map(p ->
                new Product_Response(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        "/Product/image/" + p.getId()
                )
        );
    }
    public Page<Product_Response> getWomenAll(int page,int size) {
        Pageable pageable= ((Pageable) PageRequest.of(page,size));
        Page<Product> products=product_repo.findAllByGender("Women",pageable);
        return products.map(p ->
                new Product_Response(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        "/Product/image/" + p.getId()
                )
        );
    }

    public Individual_Product_Response productById(int id) {
        Optional<Product> p=product_repo.findById(id);
        if(p.isEmpty())
            throw new RuntimeException("");
        Product p2=p.get();
        List<Product_colors> pc=p2.getColor();
        List<Individual_Product_Colors_Response> pcr=new ArrayList<>();
        for(int i=0;i<pc.size();i++)
        {
            Product_colors pci=pc.get(i);
            List<Individual_Product_Size_Response> productSizeResponses=new ArrayList<>();
            List<Product_size> ps=pci.getSize();
            for(Product_size psi:ps)
                productSizeResponses.add(new Individual_Product_Size_Response(psi.getId(),psi.getQuantity(),psi.getSize()));

            Individual_Product_Colors_Response pcr2=new Individual_Product_Colors_Response(pci.getId(), pci.getColor(),productSizeResponses,"/Product/image/"+p2.getId()+"?colorId="+i);
            pcr.add(pcr2);
        }
        return new Individual_Product_Response(
                p2.getId(),
                p2.getName(),
                p2.getType(),
                p2.getFit(),
                p2.getPrice(),
                p2.getDescription(),
                p2.getGender(),
                pcr
        );

    }

    public Object getImage(int productId, int colorId) {
        Product p=product_repo.findById(productId).get();
        Product_colors pc=p.getColor().get(colorId);
        System.out.println(pc.getSize().size());
        return pc.getPicture();
    }
}
