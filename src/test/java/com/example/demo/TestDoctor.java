package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.controller.DoctorController;
import com.example.demo.entities.Doctor;
import com.example.demo.repository.DoctorRepository;


@SpringBootTest
public class TestDoctor {
	private Doctor doctor1;
	private Doctor doctor2;

	@Mock
	DoctorRepository doctorRepositoryMock;

	@InjectMocks
	DoctorController doctorController;

	// Testing get all the Doctor method
	@Test
	public void testgetAllDoctor() {
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors.add(doctor1);
		doctors.add(doctor2);
		when(doctorRepositoryMock.findAll()).thenReturn(doctors);
		assertEquals(2, doctorController.getAllDoctors().size());
	}

	// Testing the update Patient
	@Test
	public void testUpdateDoctor() {
		Doctor doctor = new Doctor("sam", "Gupta", "44525");
		when(doctorRepositoryMock.findByName(doctor.getName())).thenReturn(null);
		when(doctorRepositoryMock.save(doctor)).thenReturn(doctor);
		assertEquals("sam", doctorController.updateDoctor(doctor).getName());
	}

	// Test Delete the Doctor
	@Test
	public void checkDeleteDoctor() {
		Doctor doctor = new Doctor();
		String message = "Doctor deleted";
		when(doctorRepositoryMock.findById(2L)).thenReturn(Optional.of(doctor));
		assertEquals(message, doctorController.deleteDoctor(2L));
	}
}
