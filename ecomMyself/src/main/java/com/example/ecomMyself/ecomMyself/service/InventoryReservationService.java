package com.example.ecomMyself.ecomMyself.service;

import com.example.ecomMyself.ecomMyself.Enums.ReservationStatus;
import com.example.ecomMyself.ecomMyself.model.Cart;
import com.example.ecomMyself.ecomMyself.model.Inventory_reservation;
import com.example.ecomMyself.ecomMyself.model.Product_size;
import com.example.ecomMyself.ecomMyself.model.Users;
import com.example.ecomMyself.ecomMyself.repository.Cart_repo;
import com.example.ecomMyself.ecomMyself.repository.Inventory_reservation_repo;
import com.example.ecomMyself.ecomMyself.repository.OrderSummary_repo;
import com.example.ecomMyself.ecomMyself.repository.Product_size_repo;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

@Service

public class InventoryReservationService {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private Cart_repo cartRepo;
    @Autowired
    private Product_size_repo productSizeRepo;
    @Autowired
    private Inventory_reservation_repo inventoryReservationRepo;
    @Autowired
    private OrderSummary_repo orderSummary_repo;
    @Transactional
    public void reserve(Users user)
    {
        List<Cart> cart=cartRepo.findAllByUserId(user.getId());
        Collections.sort(cart,(x,y)->Long.compare(x.getProduct().getId(),y.getProduct().getId()));
        for(Cart cartitem:cart)
        {
            Product_size productSize=productSizeRepo.findBySizeAndProductColors_ColorAndProductColors_Product_Id(cartitem.getSize(),cartitem.getColor(),cartitem.getProduct().getId()).orElseThrow(()->new RuntimeException("Product not found"));
            if(productSize.getQuantity()-productSize.getReserved()>=cartitem.getQuantity())
                productSize.setReserved(productSize.getReserved()+cartitem.getQuantity());
            else
                throw new RuntimeException("Out Of Stock");
            productSizeRepo.saveAndFlush(productSize);
            inventoryReservationRepo.save(new Inventory_reservation(user,productSize,cartitem.getQuantity(), LocalDateTime.now(ZoneOffset.UTC),LocalDateTime.now(ZoneOffset.UTC).plusMinutes(5), ReservationStatus.ACTIVE));
        }
    }
    @Transactional
    public void processingPayment(Users user)
    {
        List<Inventory_reservation> inventoryReservations=inventoryReservationRepo.findByUser(user);
        for(Inventory_reservation ir:inventoryReservations)
        {
            ir.setStatus(ReservationStatus.PROCESSING_PAYMENT);
            inventoryReservationRepo.saveAndFlush(ir);
        }
    }
    @Transactional
    public void orderPlaced(Users user) {
        List<Inventory_reservation> inventoryReservations=inventoryReservationRepo.findByUser(user);
        for(Inventory_reservation ir:inventoryReservations)
        {
            while(true) {
                try {
                    Product_size productSize = productSizeRepo.findById(ir.getProduct_size().getId()).orElseThrow(() -> new RuntimeException("Product Not Found While reducing stock of product_size on payment"));
                    productSize.setReserved(productSize.getReserved()-ir.getQuantity());
                    productSizeRepo.saveAndFlush(productSize);
                    break;
                }catch (ObjectOptimisticLockingFailureException e){
                }
                catch(Exception e) {
                    throw new RuntimeException(e);
                }
            }
            inventoryReservationRepo.delete(ir);
            inventoryReservationRepo.flush();
        }
    }
    @Transactional
    public void paymentFailed(Users user) {
        List<Inventory_reservation> inventoryReservations=inventoryReservationRepo.findByUser(user);
        for(Inventory_reservation ir:inventoryReservations)
        {
            ir.setStatus(ReservationStatus.ACTIVE);
            inventoryReservationRepo.saveAndFlush(ir);
        }
    }
    @Transactional
    public void rollbackReservation(Inventory_reservation ir) {
        try {
            while (true) {
                try {
                    Product_size productSize = productSizeRepo.findById(ir.getProduct_size().getId()).orElseThrow(() -> new RuntimeException("Product Not Found While reducing stock of product_size on payment"));
                    productSize.setReserved(productSize.getReserved() - ir.getQuantity());
                    productSizeRepo.saveAndFlush(productSize);
                    break;
                } catch (ObjectOptimisticLockingFailureException e) {
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            inventoryReservationRepo.deleteById(ir.getId());
            inventoryReservationRepo.flush();
            orderSummary_repo.deleteAllByUser(ir.getUser());
            System.out.println("Deleted reservation");
        }catch (Exception e)
        {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
