package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Appointment;
import com.example.demo.models.Doctor;
import com.example.demo.service.DoctorService;

@RestController
@RequestMapping("api/v1/doctors")
public class DoctorController {

	@Autowired
	private DoctorService doctorservice;
	
	@GetMapping
	public Page<Doctor> getAllDoctors(@RequestParam(defaultValue = "0") int page,
		    @RequestParam(defaultValue = "10") int size,
		    @RequestParam(defaultValue = "name") String sortBy,
		    @RequestParam(defaultValue = "asc") String sortDir) {
		System.out.println("Fetching the doctors...");
		return doctorservice.getAllDoctors(page, size, sortBy, sortDir);
	}
	
	@PostMapping
	public Doctor createDoctor(@RequestBody Doctor doctor) {
		System.out.println("Creating doctor...");
		return doctorservice.createDoctor(doctor);
	}
	
	@GetMapping("/{id}")
	public Doctor getDoctorById(@PathVariable Long id) {
		System.out.println("Fetching doctor by id...");
		return doctorservice.getDoctorById(id);
	}
	
	@PutMapping("/{id}")
	public void updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
		System.out.println("Updating doctor...");
		doctorservice.updateDoctor(id, doctor);
	}
	
	@DeleteMapping("/{id}")
	public void deleteDoctor(@PathVariable Long id) {
		System.out.println("Deleting the doctor...");
		doctorservice.deleteDoctor(id);
	}
}
