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
import com.example.demo.models.Patient;
import com.example.demo.service.AppointmentService;


@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentservice;
	
	@GetMapping
	public Page<Appointment> getAllAppointments(@RequestParam(defaultValue = "0") int page,
		    @RequestParam(defaultValue = "10") int size,
		    @RequestParam(defaultValue = "date") String sortBy,
		    @RequestParam(defaultValue = "asc") String sortDir) {
		System.out.println("Fetching the appointments...");
		return appointmentservice.getAllAppointments(page, size, sortBy, sortDir);
	}
	
	@PostMapping
	public Appointment createAppointment(@RequestBody Appointment appointment) {
		System.out.println("Creating appointment...");
		return appointmentservice.createAppointment(appointment);
	}
	
	@GetMapping("/{id}")
	public Appointment getAppointmentById(@PathVariable Long id) {
		System.out.println("Fetching appointment by id...");
		return appointmentservice.getAppointmentById(id);
	}
	
	@PutMapping("/{id}")
	public void updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
		System.out.println("Updating appointment...");
		appointmentservice.updateAppointment(id, appointment);
	}
	
	@DeleteMapping("/{id}")
	public void deleteAppointment(@PathVariable Long id) {
		System.out.println("Deleting the appointment");
		appointmentservice.deleteAppointment(id);
	}
}
