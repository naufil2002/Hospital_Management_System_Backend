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
import com.example.demo.HospitalManagementApplication;
import com.example.demo.models.Patient;
import com.example.demo.service.PatientService;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

	@Autowired
	private PatientService patientservice;
	
	@GetMapping
	public Page<Patient> getAllPatients(@RequestParam(defaultValue = "0") int page,
		    @RequestParam(defaultValue = "10") int size,
		    @RequestParam(defaultValue = "name") String sortBy,
		    @RequestParam(defaultValue = "asc") String sortDir) {
		System.out.println("Fetching the patients");
		  return patientservice.getAllPatients(page, size, sortBy, sortDir);
	}
	
	@PostMapping
	public Patient createPatient(@RequestBody Patient patient) {
		System.out.println("Creating patient...");
		return patientservice.createPatient(patient);
	}
	
	@GetMapping("/{id}")
	public Patient getPatientById(@PathVariable Long id) {
		System.out.println("Fetching patient by id...");
		return patientservice.getPatientById(id);
	}
	
	@PutMapping("/{id}")
	public void updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
		System.out.println("Updating patient...");
		patientservice.updatePatient(id, patient);
	}
	
	@DeleteMapping("/{id}")
	public void deletePatient(@PathVariable Long id) {
		System.out.println("Deleting the patient");
		patientservice.deletePatient(id);
	}
	
	
}
