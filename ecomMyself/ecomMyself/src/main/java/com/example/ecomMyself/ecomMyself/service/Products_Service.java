package com.example.ecomMyself.ecomMyself.service;

import com.example.ecomMyself.ecomMyself.model.DTO.AddProductSize_request;
import com.example.ecomMyself.ecomMyself.model.DTO.AddProduct_request;
import com.example.ecomMyself.ecomMyself.model.Product;
import com.example.ecomMyself.ecomMyself.model.Product_colors;
import com.example.ecomMyself.ecomMyself.model.Product_size;
import com.example.ecomMyself.ecomMyself.repository.Product_Repo;
import com.example.ecomMyself.ecomMyself.repository.Product_colors_repo;
import com.example.ecomMyself.ecomMyself.repository.Product_size_repo;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public List<Product> getAll()
    {
        List<Product> products=product_repo.findAll();
        return products;
    }

    public List<Product> getMenAll() {
        List<Product> products=product_repo.findAllByGender("Men");
        return products;
    }
    public List<Product> getWomenAll() {
        List<Product> products=product_repo.findAllByGender("Women");
        return products;
    }

    public Product productById(int id) {
        Optional<Product> p=product_repo.findById(id);
        if(p.isEmpty())
            throw new RuntimeException("");
        return p.get();
    }
}
