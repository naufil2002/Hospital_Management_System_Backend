package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private String status;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}
