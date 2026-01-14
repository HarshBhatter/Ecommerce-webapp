package com.example.ecomMyself.ecomMyself.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Product {
    @Id
//    @GeneratedValue : this creates an extra table in myswl for counter to stop it we use :-
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;
    private String fit;
    private BigDecimal price;
    private String description;
    private String gender;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Product_colors> color;

    public Product() {
        this.name="-";
        this.type="-";
        this.fit="-";
        this.price = BigDecimal.ZERO;
        this.description="-";
        gender="Bi";
    }

    public Product(int id, String name, String type, String fit, List<Integer> size, int count, BigDecimal price, String description,List<Product_colors> color) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.fit = fit;
        this.price = price;
        this.description = description;
        this.color=color;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product_colors> getColor() {
        return color;
    }

    public void setColor(List<Product_colors> color) {
        this.color = color;
    }
}
