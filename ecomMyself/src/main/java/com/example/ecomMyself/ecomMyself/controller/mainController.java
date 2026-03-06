package com.example.ecomMyself.ecomMyself.controller;

import com.example.ecomMyself.ecomMyself.model.DTO.AddProduct_request;
import com.example.ecomMyself.ecomMyself.model.Product;
import com.example.ecomMyself.ecomMyself.repository.User_Repo;
import com.example.ecomMyself.ecomMyself.service.*;
import com.example.ecomMyself.ecomMyself.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin()
public class mainController {
    @Autowired
    private User_service user_service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private User_Repo user_repo;
    @Autowired
    private Products_Service products_service;
    @Autowired
    AuthenticationManager authenticationManager;
    @GetMapping("/")
    public String home()
    {
        return "Hello World";
    }
    @PostMapping("create_account")
    public ResponseEntity<?> creating_account(@RequestBody Users user) {
        try {
            Users savedUser = user_service.save(user);
            return ResponseEntity.ok(jwtService.generateToken(user.getUsername(), user.getVersion()));
        }
        catch (Exception e)
        {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("Login")
    public String login(@RequestBody Users user) {
        if(user.getPassword()==null)
            throw new BadCredentialsException("Password is required");
        System.out.println("Login");
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated()) {
            Users user2=user_service.findByUserName(user.getUsername());
            return jwtService.generateToken(user.getUsername(), user2.getVersion());
        }
        else
            return "Login Failed";
    }

    @PostMapping("Logout")// 'l' will cause problem as spring security has a method of logout so 'L'
    public String logout(@AuthenticationPrincipal UserPrincipal principal)
    {
        if(principal!=null) {
            System.out.println("logout "+principal.toString());
            user_service.changeVersion(principal.getUsername());
            return "logging out";
        }
        return "logged out";
    }
    @GetMapping("All")
    public ResponseEntity<?> products(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size)
    {
        System.out.println(page+" "+size);
        try {
            Page<Product> products=products_service.getAll(page,size);
            System.out.println(products);
            return ResponseEntity.ok(products);
        }
        catch (Exception e)
        {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
    @GetMapping("Mens")
    public ResponseEntity<?> mensproduct(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size)
    {
        try {
            Page<Product> products=products_service.getMenAll(page,size);
            return ResponseEntity.ok(products);
        }
        catch (Exception e)
        {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
    @GetMapping("Womens")
    public ResponseEntity<?> womensproduct(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size)
    {
        try {
            Page<Product> products=products_service.getWomenAll(page,size);
            return ResponseEntity.ok(products);
        }
        catch (Exception e)
        {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
    @GetMapping("All/")
    public ResponseEntity<?> ProductById(@RequestParam int id)
    {
        try {
            Product products=products_service.productById(id);
            return ResponseEntity.ok(products);
        }
        catch (Exception e)
        {
            return ResponseEntity
                    .badRequest()
                    .body("Product Not Found");
        }
    }
    @PostMapping("AddProducts")
    public ResponseEntity<?> addProducts(@RequestPart AddProduct_request addProductRequest, @RequestPart("image") MultipartFile image)
    {
        try{
            Product product=products_service.AddProducts(addProductRequest,image);
            return ResponseEntity.ok(product);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
