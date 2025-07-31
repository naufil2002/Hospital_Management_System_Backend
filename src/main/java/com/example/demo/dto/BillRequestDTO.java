package com.example.demo.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillRequestDTO {
    public String patientName;
    public String doctorName;
    public String date;
    public Double amount;
    public String status;
}


