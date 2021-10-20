package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.controller.DoctorController;
import com.example.demo.entities.Doctor;
import com.example.demo.repository.DoctorRepository;

@SpringBootTest
class AppointmentApplicationTests {
	
	private Doctor doctor1;
	private Doctor doctor2;
	
	@Mock
	DoctorRepository doctorRepositoryMock;
	
	@InjectMocks
	DoctorController doctorController;
	
	//Testing get all the doctors method
	@Test
	public void testgetAllDoctor() {
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors.add(doctor1);
		doctors.add(doctor2);
		when(doctorRepositoryMock.findAll()).thenReturn(doctors);
		assertEquals(2, doctorController.getAllDoctors().size());
	}
}
