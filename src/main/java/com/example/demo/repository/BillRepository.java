package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Bill;

@Repository  // Use @Repository annotation, NOT @RestController
public interface BillRepository extends JpaRepository<Bill, Long> {

    // Fetch all Bills with Appointment and Patient details eagerly
    @Query("SELECT b FROM Bill b JOIN FETCH b.appointment a JOIN FETCH a.patient")
    List<Bill> findAllBillsWithDetails();

    // Fetch one Bill by id with Appointment and Patient details eagerly
    @Query("SELECT b FROM Bill b JOIN FETCH b.appointment a JOIN FETCH a.patient WHERE b.id = :id")
    Bill findBillWithAppointmentAndPatient(@Param("id") Long id);
}
