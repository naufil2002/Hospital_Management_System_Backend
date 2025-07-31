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

import com.example.demo.models.Appointment;
import com.example.demo.models.Doctor;
import com.example.demo.models.Patient;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;

@Service
public class AppointmentService {

	public static final Logger logger = LoggerFactory.getLogger(PatientService.class);

	@Autowired
	private AppointmentRepository appointmentrepository;
	
	@Autowired
	private PatientRepository patientrepository;
	
	@Autowired
	private DoctorRepository doctorrepository;

	public Page<Appointment> getAllAppointments(int page, int size, String sortBy, String sortDir) {
		try {
			Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
			Pageable pageable = PageRequest.of(page, size, sort);
			System.out.println("Into service layer");
			return appointmentrepository.findAll(pageable);
		} catch (Exception e) {
			System.out.println("Error msg" + e.getMessage());
			logger.error("An error occured during fetching all the Appointments: {}", e.getMessage());
			return null;
		}
	}

	public Appointment createAppointment(Appointment appointment) {
		try {
			  // Fetch full Patient and Doctor from DB using IDs
			Patient patient = patientrepository.findByName(appointment.getPatient().getName())
				    .orElseThrow(() -> new RuntimeException("Patient not found"));
				Doctor doctor = doctorrepository.findByName(appointment.getDoctor().getName())
				    .orElseThrow(() -> new RuntimeException("Doctor not found"));

		    appointment.setPatient(patient);
		    appointment.setDoctor(doctor);

		    return appointmentrepository.save(appointment);
		} catch (Exception e) {
			System.out.println("Error msg" + e.getMessage());
			logger.error("An error occured during creating the Appointments: {}", e.getMessage());
			return null;
		}
	}

	public Appointment getAppointmentById(Long id) {
		try {
			Optional<Appointment> appointment = appointmentrepository.findById(id);
			return appointment.orElse(null);
		} catch (Exception e) {
			System.out.println("Error msg" + e.getMessage());
			logger.error("An error occured during fetching the Appointments by Id {} : {}", id, e.getMessage());
			return null;
		}
	}

	public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
		try {
			Optional<Appointment> existingAppointment = appointmentrepository.findById(id);
			if (existingAppointment.isPresent()) {
				Appointment a = existingAppointment.get();
				a.setDate(updatedAppointment.getDate());
				a.setDoctor(updatedAppointment.getDoctor());  // ✅ CHANGED: setDocterId → setDocter
				a.setPatient(updatedAppointment.getPatient()); // ✅ CHANGED: setPatientId → setPatient

				appointmentrepository.save(a);
				return a; // ✅ CHANGED: return updatedAppointment → return a
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

	public void deleteAppointment(Long id) {
	    try {
	        if (!appointmentrepository.existsById(id)) {
	            throw new RuntimeException("Appointment not found with ID: " + id);
	        }
	        appointmentrepository.deleteById(id);
	        System.out.println("Deleted appointment with ID: " + id);
	    } catch (Exception e) {
	        System.out.println("Error msg: " + e.getMessage());
	        logger.error("An error occurred while deleting appointment ID {}: {}", id, e.getMessage());
	    }
	}

}
