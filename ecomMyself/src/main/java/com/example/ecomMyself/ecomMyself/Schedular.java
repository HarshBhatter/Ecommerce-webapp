package com.example.ecomMyself.ecomMyself;

import com.example.ecomMyself.ecomMyself.Enums.ReservationStatus;
import com.example.ecomMyself.ecomMyself.model.Inventory_reservation;
import com.example.ecomMyself.ecomMyself.repository.Inventory_reservation_repo;
import com.example.ecomMyself.ecomMyself.repository.OrderSummary_repo;
import com.example.ecomMyself.ecomMyself.service.InventoryReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
@Component
@EnableScheduling
public class Schedular {
    @Autowired
    private Inventory_reservation_repo inventoryReservationRepo;
    @Autowired
    private OrderSummary_repo orderSummary_repo;
    @Autowired
    private InventoryReservationService inventoryReservationService;

    @Scheduled(fixedDelay = 60000)
    public void schedule()
    {
        List<Inventory_reservation> inventoryReservations=inventoryReservationRepo.findByActiveAndExpired(ReservationStatus.ACTIVE, LocalDateTime.now(ZoneOffset.UTC));
        for(Inventory_reservation ir:inventoryReservations)
            inventoryReservationService.rollbackReservation(ir);
        System.out.println(inventoryReservations.toString()+" Scheduled Delete done "+inventoryReservations.size());
    }
}
