package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Doctor;
import com.example.demo.entities.Speciality;
import com.example.demo.exception.InformationExistException;
import com.example.demo.exception.InformationNotFoundException;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.SpecialityRepository;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
	
	 private DoctorRepository doctorRepository;
	    private SpecialityRepository specialityRepository;

	    @Autowired
	    public void setSpecialityRepository(SpecialityRepository specialityRepository) {
	        this.specialityRepository = specialityRepository;
	    }

	    @Autowired
	    public void setDoctorRepository(DoctorRepository doctorRepository) {
	        this.doctorRepository = doctorRepository;
	    }
	
	//http://localhost:9092/api/doctors
    @GetMapping()
    public List<Doctor> getAllDoctors() {
        System.out.println("controller calling alldoc");
        return doctorRepository.findAll();
    }

    //http://localhost:9092/api/doctors/{doctorName}
    @GetMapping("{doctorName}")
    public Doctor getDoctor(@PathVariable String doctorName) {
        System.out.println("service valling get doc");
        Doctor doctor = doctorRepository.findByName(doctorName);
        if (doctor != null) {
            return doctor;
        } else
            throw new InformationNotFoundException("Doctor " + doctorName + "not exist here");
    }

    //http://localhost:9092/api/doctors/add/{specialityId}
    @PostMapping("/add/{specialityId}")
    public Doctor createDoctor(@RequestBody Doctor doctorObject, @PathVariable Long specialityId) {
        System.out.println("controller calling createDoc");
        Optional<Speciality> speciality = specialityRepository.findById(specialityId);
        if (speciality.isPresent()) {
            doctorObject.setSpeciality(speciality.get());
        }
        Doctor doctor = doctorRepository.findByName(doctorObject.getName());
        if (doctor != null) {
            throw new InformationExistException("doctor " + doctor.getName() + "already exist");
        } else
        
            return doctorRepository.save(doctorObject);
    }

    //http://localhost:9092/api/doctors/delete/{doctorId}
    @DeleteMapping("/delete/{doctorId}")
    public String deleteDoctor(@PathVariable Long doctorId) {
        System.out.println("controller calling deleteDoctor");
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if (doctor.isPresent()) {
            doctorRepository.deleteById(doctorId);
            return "Doctor " + doctorId + " deleted";
        } else
            throw new InformationNotFoundException("Doctor " + doctorId + "is not exist");
    }
}