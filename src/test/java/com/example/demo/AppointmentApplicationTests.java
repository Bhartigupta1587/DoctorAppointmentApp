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

import com.example.demo.controller.PatientController;
import com.example.demo.entities.Patient;

import com.example.demo.repository.PatientRepository;

@SpringBootTest
class AppointmentApplicationTests {

	private Patient Patient1;
	private Patient Patient2;

	@Mock
	PatientRepository patientRepositoryMock;

	@InjectMocks
	PatientController patientController;

	// Testing get all the Patient method
	@Test
	public void testgetAllPatient() {
		List<Patient> patients = new ArrayList<Patient>();
		patients.add(Patient1);
		patients.add(Patient2);
		when(patientRepositoryMock.findAll()).thenReturn(patients);
		assertEquals(2, patientController.getAllPatient().size());
	}

	// Testing the Create Patient
	@Test
	public void testCreatePatient() {
		Patient patient = new Patient("Bharti", "Gupta", 25);
		when(patientRepositoryMock.findByFirstName(patient.getFirstName())).thenReturn(Optional.empty());
		when(patientRepositoryMock.save(patient)).thenReturn(patient);
		assertEquals("Bharti", patientController.createPatient(patient).getFirstName());
	}

	// Testing the update Patient
	@Test
	public void testUpdatePatient() {
		Patient patient = new Patient("sam", "Gupta", 25);
		when(patientRepositoryMock.findByFirstName(patient.getFirstName())).thenReturn(Optional.of(patient));
		when(patientRepositoryMock.save(patient)).thenReturn(patient);
		assertEquals("sam", patientController.updatePatient(patient).getFirstName());
	}

	// Test Delete the Doctor
	@Test
	public void checkDeleteDoctor() {
		Patient patient = new Patient();
		String message = "patient deleted";
		when(patientRepositoryMock.findById(2L)).thenReturn(Optional.of(patient));
		assertEquals(message, patientController.deletePatient(2L));
	}
}
