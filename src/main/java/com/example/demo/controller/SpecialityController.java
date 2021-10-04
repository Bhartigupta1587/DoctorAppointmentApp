package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Speciality;
import com.example.demo.exception.InformationExistException;
import com.example.demo.repository.SpecialityRepository;

@RestController
@RequestMapping("/api/speciality")
public class SpecialityController {

	private SpecialityRepository specialityRepository;

	@Autowired
	public void setSpecialityRepository(SpecialityRepository specialityRepository) {
		this.specialityRepository = specialityRepository;
	}

	@GetMapping()
	public List<Speciality> getAllSpeciality() {
		System.out.println("controller calling getAllSpeciality");
		return specialityRepository.findAll();
	}

	//http://localhost:8080/api/speciality/add
	@PostMapping("/add")
	public Speciality createSpeciality(@RequestBody Speciality specialityObject) {
		System.out.println("controller calling createSpeciality");
		Optional<Speciality> speciality = specialityRepository
				.findBySpecialityName(specialityObject.getSpecialityName());
		if (speciality.isPresent()) {
			throw new InformationExistException("speciality " + specialityObject.getSpecialityName() + "already exist");
		} else
			return specialityRepository.save(specialityObject);
	}
}