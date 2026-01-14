package com.example.ecomMyself.ecomMyself.service;

import com.example.ecomMyself.ecomMyself.model.Users;
import com.example.ecomMyself.ecomMyself.repository.User_Repo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class User_service {
    @Autowired
    private User_Repo user_repo;
    @PersistenceContext
    private EntityManager em;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public Users save(Users user)
    {
        if (user_repo.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists. Please choose another one.");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        return (Users)user_repo.save(user);
    }
    @Transactional
    public void changeVersion(String username)
    {
        Users user=user_repo.findByUsername(username);
        user.setVersion(user.getVersion()+1);
        user_repo.save(user);
//        em.flush();
//        em.refresh(user);
    }

    public Users findByUserName(String username)
    {
        return user_repo.findByUsername(username);
    }

}
