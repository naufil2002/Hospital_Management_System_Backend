package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.models.Patient;
import com.example.demo.repository.PatientRepository;

@Service
public class PatientService {

	public static final Logger logger = LoggerFactory.getLogger(PatientService.class);
	
	@Autowired
	private PatientRepository patientrepository;
	
	public Page<Patient> getAllPatients(int page, int size, String sortBy, String sortDir){
		try {
			 Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
			    Pageable pageable = PageRequest.of(page, size, sort);
			    return patientrepository.findAll(pageable);
		} catch (Exception e) {
			System.out.println("Error msg" + e.getMessage());
			logger.error("An error occured during fetching all the Patients: {}", e.getMessage());
			return null;
		}
	}
	
	public Patient createPatient(Patient patient) {
		try {
			patientrepository.save(patient);
			return patient;
		} catch (Exception e) {
			System.out.println("Error msg" + e.getMessage());
			logger.error("An error occured during creating the Patients: {}", e.getMessage());
			return null;
		}
	}
	
	public Patient getPatientById(Long id) {
		try {
			Optional<Patient> patient = patientrepository.findById(id);
			return patient.orElse(null);
		} catch (Exception e) {
			System.out.println("Error msg" + e.getMessage());
			logger.error("An error occured during fetching the Patients by Id {} : {}", id, e.getMessage());
			return null;
		}
	}
	
	public Patient updatePatient(Long id, Patient updatedPatient) {
		try {
			Optional<Patient> existingPatient = patientrepository.findById(id);
			if(existingPatient.isPresent()) {
				Patient p = existingPatient.get();
				p.setName(updatedPatient.getName());
				p.setAge(updatedPatient.getAge());
				p.setGender(updatedPatient.getGender());
				patientrepository.save(p);
				
				return updatedPatient;
			} else {
				logger.error("Patient with ID {} not found", id);
				return null;
			}
		} catch (Exception e) {
			System.out.println("Error msg" + e.getMessage());
			logger.error("An error occured during updating the Patients by Id {} : {}", id, e.getMessage());
			return null;
		}
	}
	
	public void deletePatient(Long id) {
		try {
			logger.info("Deleting patient with id : {}", id);
			patientrepository.deleteById(id);
		} catch (Exception e) {
			System.out.println("Error msg" + e.getMessage());
			logger.error("An error occured during deleting the Patients by Id {} : {}", id, e.getMessage());
		}
	}
}







