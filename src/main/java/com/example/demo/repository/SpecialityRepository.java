package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Speciality;

public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
	
	Optional<Speciality> findBySpecialityName(String splName);

}
