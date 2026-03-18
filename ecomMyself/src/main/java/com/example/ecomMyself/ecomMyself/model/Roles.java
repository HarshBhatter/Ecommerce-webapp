package com.example.ecomMyself.ecomMyself.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"role"})
)
public class Roles {
    @Id
    private int Id;
    private String role;
    @OneToMany(mappedBy = "role")
    private List<Users> user;

    public Roles() {
    }

    public Roles(int id, String role) {
        Id = id;
        this.role = role;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
