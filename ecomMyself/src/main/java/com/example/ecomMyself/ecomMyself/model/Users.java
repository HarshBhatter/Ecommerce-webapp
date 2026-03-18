package com.example.ecomMyself.ecomMyself.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Optional;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String username;
    private String password;
    private int version;
    private BigDecimal cartValue;
    @ManyToOne
    private Roles role;

    public Users() {
        version=1;
        cartValue=BigDecimal.valueOf(0);
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getCartValue() {
        return cartValue;
    }

    public void setCartValue(BigDecimal cartValue) {
        this.cartValue=cartValue;
    }

    public Roles getRoles() {
        return role;
    }

    public void setRoles(Roles roles) {
        this.role = roles;
    }
}
