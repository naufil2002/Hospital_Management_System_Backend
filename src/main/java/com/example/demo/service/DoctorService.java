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

import com.example.demo.models.Doctor;
import com.example.demo.models.Patient;
import com.example.demo.repository.DoctorRepository;

@Service
public class DoctorService {

	public static final Logger logger = LoggerFactory.getLogger(PatientService.class);
	
	@Autowired
	private DoctorRepository doctorrepository;
	
	public Page<Doctor> getAllDoctors(int page, int size, String sortBy, String sortDir){
		try {
			Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
			Pageable pageable = PageRequest.of(page, size, sort);
			System.out.println("Into service layer");
			return doctorrepository.findAll(pageable);
		} catch (Exception e) {
			System.out.println("Error msg" + e.getMessage());
			logger.error("An error occured during fetching all the Doctors : {}", e.getMessage());
			return null;
		}
	}
	
	public Doctor createDoctor(Doctor doctor) {
		try {
			doctorrepository.save(doctor);
			return doctor;
		} catch (Exception e) {
			System.out.println("Error msg" + e.getMessage());
			logger.error("An error occured during creating the Appointments: {}", e.getMessage());
			return null;
		}
	}
	
	public Doctor getDoctorById(Long id) {
		try {
			Optional<Doctor> doctor = doctorrepository.findById(id);
			return doctor.orElse(null);
		} catch (Exception e) {
			System.out.println("Error msg" + e.getMessage());
			logger.error("An error occured during fetching the Appointments by Id {} : {}", id, e.getMessage());
			return null;
		}
	}
	
	public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
		try {
			Optional<Doctor> existingDoctor = doctorrepository.findById(id);
			if(existingDoctor.isPresent()) {
				Doctor d = existingDoctor.get();
				d.setName(updatedDoctor.getName());
				d.setSpeciality(updatedDoctor.getSpeciality());
				doctorrepository.save(d);
				
				return updatedDoctor;
			} else {
				logger.error("Doctor with ID {} not found", id);
				return null;
			}
		} catch (Exception e) {
			System.out.println("Error msg" + e.getMessage());
			logger.error("An error occured during updating the Appointments by Id {} : {}", id, e.getMessage());
			return null;
		}
	}
	
	public void deleteDoctor(Long id) {
		try {
			logger.info("Deleting doctor with id : {}", id);
			doctorrepository.deleteById(id);
		} catch (Exception e) {
			System.out.println("Error msg" + e.getMessage());
			logger.error("An error occured during deleting the Appointments by Id {} : {}", id, e.getMessage());
		}
	}
}
