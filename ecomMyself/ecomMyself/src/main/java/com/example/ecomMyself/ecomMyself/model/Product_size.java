package com.example.ecomMyself.ecomMyself.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"product_colors_id", "size"})
        }
)
public class Product_size {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private int size;
    @ManyToOne
    @JsonIgnore //to stop infite loop as one table contains key of other table and same for the other table
    private Product_colors productColors;

    public Product_size() {
    }

    public Product_size(int id, int quantity, int size, Product_colors productColors) {
        this.id = id;
        this.quantity = quantity;
        this.size = size;
        this.productColors = productColors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Product_colors getProductColors() {
        return productColors;
    }

    public void setProductColors(Product_colors productColors) {
        this.productColors = productColors;
    }
}
