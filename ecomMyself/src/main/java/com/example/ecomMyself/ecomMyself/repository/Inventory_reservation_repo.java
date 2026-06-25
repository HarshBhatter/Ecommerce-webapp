package com.example.ecomMyself.ecomMyself.repository;

import com.example.ecomMyself.ecomMyself.Enums.ReservationStatus;
import com.example.ecomMyself.ecomMyself.model.Inventory_reservation;
import com.example.ecomMyself.ecomMyself.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface Inventory_reservation_repo extends JpaRepository<Inventory_reservation,Long> {
    List<Inventory_reservation> findByUser(Users user);
    @Query("""
        SELECT i
        FROM Inventory_reservation i
        WHERE i.status = :status
        AND i.expireTime < :currentTime
        """)
    List<Inventory_reservation> findByActiveAndExpired(@Param("status") ReservationStatus status, @Param("currentTime")LocalDateTime currentTime);
}
