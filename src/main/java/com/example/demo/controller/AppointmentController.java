package com.example.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Appointment;
import com.example.demo.entities.Doctor;
import com.example.demo.entities.Patient;
import com.example.demo.entities.request.AppointmentRequest;
import com.example.demo.exception.InformationExistException;
import com.example.demo.exception.InformationNotFoundException;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

	 	private AppointmentRepository appointmentsRepository;
	 	private DoctorRepository doctorRepository;
	    private PatientRepository patientRepository;

	    public static boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
	        return start1.before(end2) && start2.before(end1);
	    }

	    @Autowired
	    public void setAppointmentsRepository(AppointmentRepository appointmentsRepository) {
	        this.appointmentsRepository = appointmentsRepository;
	    }

	    @Autowired
	    public void setDoctorRepository(DoctorRepository doctorRepository) {
	        this.doctorRepository = doctorRepository;
	    }

	    @Autowired
	    public void setPatientRepository(PatientRepository patientRepository) {
	        this.patientRepository = patientRepository;
	    }

	  //http://localhost:9092/api/appointments
	    @GetMapping()
	    public List<Appointment> getAllAppointments() {
	        System.out.println("controller calling getAllAppointment");
	        return appointmentsRepository.findAll();
	    }

	    //http://localhost:9092/api/appointments/add
	    @PostMapping("/add")
	    public Appointment createAppointment(@RequestBody AppointmentRequest appointmentObject) {
	        System.out.println("controller calling create Appointment");
	        Optional<Doctor> doctor = doctorRepository.findById(appointmentObject.getDoctorId());
	        if (!doctor.isPresent()) {
	            //Invalid doctor.
	            throw new InformationNotFoundException("Doctor " + appointmentObject.getDoctorId() + " not exist here");
	        }
	        
	        Optional<Patient> patient = patientRepository.findById(appointmentObject.getPatientId());
	        if (!patient.isPresent()) {
	            //Invalid patient.
	            throw new InformationNotFoundException("Patient " + appointmentObject.getPatientId() + "not exist here");
	        }
	        Doctor doc = doctor.get();
	        Patient pat = patient.get();
	        if (!doc.getAppointments().isEmpty()) {
	            //Check if the appointment exists with overlapping time for same patient doctor.
	            // This will avoid patient booking multiple overlapping appointments.
	            boolean appointmentExists = doc.getAppointments().stream().anyMatch(a -> a.getPatient().getId() == appointmentObject.getPatientId() &&
	                    a.getDate() == appointmentObject.getAppointmentDate() &&
	                    !AppointmentController.isOverlapping(a.getStartTime(), a.getEndTime(), appointmentObject.getStartTime(), appointmentObject.getEndTime()));
	            if (appointmentExists) {
	                throw new InformationExistException("Appointment is already present");
	            }
	        }
	        Appointment appointment = new Appointment();
	        appointment.setDoctor(doc);
	        appointment.setPatient(pat);
	        appointment.setDate(appointmentObject.getAppointmentDate());
	        appointment.setStartTime(appointmentObject.getStartTime());
	        appointment.setEndTime(appointmentObject.getEndTime());
	        appointmentsRepository.save(appointment);
	        return appointment;
	    }
	    @DeleteMapping("/delete/{appointmentId}")
	    public String deleteAppointment(Long patientId) {
	        System.out.println("controller calling deleteAppointment");
	        Optional<Appointment> appointment = appointmentsRepository.findById(patientId);
	        if (appointment.isPresent()){
	            appointmentsRepository.deleteById(patientId);
	            return "appointment delete";
	        } else
	            throw  new InformationNotFoundException("Appointment "+patientId+"not exist");
	    }
	}