package com.example.ecomMyself.ecomMyself.controller;

import com.example.ecomMyself.ecomMyself.DTO.AddProduct_request;
import com.example.ecomMyself.ecomMyself.DTO.Auth_response;
import com.example.ecomMyself.ecomMyself.DTO.Individual_Product_Response;
import com.example.ecomMyself.ecomMyself.DTO.Product_Response;
import com.example.ecomMyself.ecomMyself.model.Product;
import com.example.ecomMyself.ecomMyself.repository.User_Repo;
import com.example.ecomMyself.ecomMyself.service.*;
import com.example.ecomMyself.ecomMyself.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin()
public class mainController {
    @Autowired
    private User_service user_service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MyUserDetailService myUserDetailService;
//    @Autowired
//    private User_Repo user_repo;
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
        System.out.println(user.toString()+" enters create account");
        try {
            Users savedUser = user_service.saveNewUser(user);
            String token=jwtService.generateToken(user.getUsername(), user.getVersion());
            String role=user.getRoles().getRole();
            Auth_response authResponse=new Auth_response(token,role);
            return ResponseEntity.ok(authResponse);
        }
        catch (Exception e)
        {
            System.out.println(user.toString()+" account creation issue:"+e.getMessage());

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("Login")
    public ResponseEntity<?> login(@RequestBody Users user) {

        if(user.getPassword()==null)
            throw new BadCredentialsException("Password is required");

        System.out.println(user.getUsername() +"trying to log in..");

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated()) {
            Users user2=user_service.findByUserName(user.getUsername());
            String token=jwtService.generateToken(user.getUsername(), user2.getVersion());
            String role=user2.getRoles().getRole();
            Auth_response authResponse=new Auth_response(token,role);
            return ResponseEntity.ok(authResponse);
        }
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
    }

    @PostMapping("Logout")// 'l' will cause problem as spring security has a method of logout so 'L'
    public String logout(@AuthenticationPrincipal UserPrincipal principal)
    {
        System.out.println(principal.getUser().getUsername()+" is logging out..");
        if(principal!=null) {
            user_service.changeVersion(principal.getUsername());
            return "logging out";
        }
        return "logged out";
    }
    @GetMapping("All")
    public ResponseEntity<?> products(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size)
    {
        System.out.println("getting all products..");
        try {
            Page<Product_Response> products=products_service.getAll(page,size);
//            System.out.println(products);
            return ResponseEntity.ok(products);
        }
        catch (Exception e)
        {
            System.out.println("problem in getting product : "+e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
    @GetMapping("Mens")
    public ResponseEntity<?> mensproduct(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size)
    {
        System.out.println("Getting mens products..");
        try {
            Page<Product_Response> products=products_service.getMenAll(page,size);
            return ResponseEntity.ok(products);
        }
        catch (Exception e)
        {
            System.out.println("Problem in getting mens products : "+e.getMessage() );

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
    @GetMapping("Womens")
    public ResponseEntity<?> womensproduct(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size)
    {
        System.out.println("Getting womens products..");
        try {
            Page<Product_Response> products=products_service.getWomenAll(page,size);
            return ResponseEntity.ok(products);
        }
        catch (Exception e)
        {
            System.out.println("Problem in getting womens products : "+e.getMessage() );

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
    @GetMapping("All/")
    public ResponseEntity<?> ProductById(@RequestParam int id)
    {
        System.out.println("Getting product with id="+id);
        try {
            Individual_Product_Response products=products_service.productById(id);
            return ResponseEntity.ok(products);
        }
        catch (Exception e)
        {
            System.out.println("Problem in getting products with id="+id+" : "+e.getMessage());

            return ResponseEntity
                    .badRequest()
                    .body("Product Not Found");
        }
    }
    @PostMapping("Admin/AddProducts")
    public ResponseEntity<?> addProducts(@RequestPart AddProduct_request addProductRequest, @RequestPart("image") MultipartFile image)
    {
        System.out.println("Admin is adding product");
        try{
            Product product=products_service.AddProducts(addProductRequest,image);
            return ResponseEntity.ok(product);
        }
        catch (Exception e)
        {
            System.out.println("Problem in adding product : "+e.getMessage());
            return ResponseEntity.badRequest().body(e);
        }
    }
    @GetMapping("Product/image/{id}")
    public ResponseEntity<?> getImage(@PathVariable int id,@RequestParam(defaultValue ="0") int colorId)
    {
        try{
//            System.out.println(products_service.getImage(id,colorId));
            return ResponseEntity.ok(products_service.getImage(id,colorId));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
