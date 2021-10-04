package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	 Doctor findByName(String docName);

	 Optional<Doctor> findById(Long doctorId);
}
