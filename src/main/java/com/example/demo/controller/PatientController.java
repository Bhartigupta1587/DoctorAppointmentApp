package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entities.Patient;
import com.example.demo.exception.InformationExistException;
import com.example.demo.exception.InformationNotFoundException;
import com.example.demo.repository.PatientRepository;


@Controller
@RequestMapping("/api/patients")
public class PatientController {

	@Autowired
	private PatientRepository patientRepository;

    //http://localhost:9092/api/patients
    @GetMapping()
    public @ResponseBody List<Patient> getAllPatient() {
        System.out.println("controller calling patient");
        return patientRepository.findAll();

    }

    //http://localhost:9092/api/patients/{patientName}
    @GetMapping("/{patientName}")
    public @ResponseBody Optional<Patient> getPatient(@PathVariable String patientName) {
        System.out.println("controller calling getPatient");
        Optional<Patient> patient = patientRepository.findByFirstName(patientName);
        if (patient.isPresent()) {
            return patient;
        } else
            throw new InformationNotFoundException("Patient " + patientName + " is not found");
    }

    //http://localhost:9092/api/patients/add
    @PostMapping("/add")
    public @ResponseBody Patient createPatient(@RequestBody Patient patientObject) {
        System.out.println("controller calling createPatient");
        Optional<Patient> patient = patientRepository.findByFirstName(patientObject.getFirstName());
        if (patient.isPresent()) {
            throw new InformationExistException("Patient " + patientObject.getFirstName() + "already exist");
        } else
            return patientRepository.save(patientObject);
    }

    //http://localhost:9092/api/patients/delete/{patientid}
    @DeleteMapping("/delete/{patientId}")
    public @ResponseBody String deletePatient(@PathVariable Long patientId) {
        System.out.println("controller calling deletePatient");
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isPresent()) {
            patientRepository.deleteById(patientId);
            return "patient deleted";
        } else
            throw new InformationNotFoundException("Patient " + patientId + " is not exist");
    }
}