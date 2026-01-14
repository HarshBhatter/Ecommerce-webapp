package com.example.ecomMyself.ecomMyself.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id", "color"})})
public class Product_colors {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String color;
    @OneToMany(mappedBy = "productColors" , cascade = CascadeType.ALL)
    private List<Product_size> size;
    @ManyToOne
    @JsonIgnore //to stop infite loop as one table contains key of other table and same for the other table
    private Product product;

    @Lob
    private byte[] picture;

    public Product_colors() {
    }

    public Product_colors(int id, String color, List<Product_size> size, Product product,byte[] picture) {
        this.id = id;
        this.color = color;
        this.size = size;
        this.product = product;
        this.picture=picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Product_size> getSize() {
        return size;
    }

    public void setSize(List<Product_size> size) {
        this.size = size;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
