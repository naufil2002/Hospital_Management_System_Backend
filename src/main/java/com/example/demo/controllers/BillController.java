package com.example.demo.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.BillRequestDTO;
import com.example.demo.models.Appointment;
import com.example.demo.models.Bill;
import com.example.demo.models.Doctor;
import com.example.demo.models.Patient;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.service.BillService;

@RestController
@RequestMapping("/api/v1/bills")
public class BillController {

    @Autowired
    private BillService billservice;
    
    @Autowired
    private AppointmentRepository appointmentrepository;
    
    @Autowired
    private BillRepository billrepository;
    
    @Autowired
    private PatientRepository patientrepository;
    
    @Autowired
    private DoctorRepository doctorrepository;

    // Changed return type to List<Bill> because service returns List now (JOIN FETCH)
    @GetMapping
    public List<Bill> getAllBills() {
        System.out.println("Fetching all bills...");
        return billservice.getAllBills();  // Changed: removed paging params
    }

    @PostMapping
    public Bill createBill(@RequestBody BillRequestDTO dto) {
        // use name (String), not ID (Long)
        Doctor doctor = doctorrepository.findByName(dto.doctorName)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Patient patient = patientrepository.findByName(dto.patientName)
            .orElseThrow(() -> new RuntimeException("Patient not found"));

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(LocalDate.parse(dto.date));

        Appointment savedAppt = appointmentrepository.save(appointment);

        Bill bill = new Bill();
        bill.setAppointment(savedAppt);
        bill.setAmount(dto.amount);
        bill.setStatus(dto.status);

        return billrepository.save(bill);
    }





    @GetMapping("/{id}")
    public Bill getBillById(@PathVariable Long id) {
        System.out.println("Fetching bill by id...");
        return billservice.getBillById(id);
    }

    // Changed return type to Bill to return updated entity
    @PutMapping("/{id}")
    public Bill updateBill(@PathVariable Long id, @RequestBody Bill bill) {
        System.out.println("Updating bill...");
        return billservice.updateBill(id, bill);  // Changed: return updated Bill
    }

    @DeleteMapping("/{id}")
    public void deleteBill(@PathVariable Long id) {
        System.out.println("Deleting bill...");
        billservice.deleteBill(id);
    }
}
