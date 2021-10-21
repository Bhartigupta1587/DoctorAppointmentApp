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

import com.example.demo.controller.SpecialityController;
import com.example.demo.entities.Speciality;
import com.example.demo.repository.SpecialityRepository;

@SpringBootTest
public class testSpeciality {
	
	private Speciality speciality1;
	private Speciality speciality2;

	@Mock
	SpecialityRepository specialityRepositoryMock;

	@InjectMocks
	SpecialityController specialityController;

	// Testing get all the specialty 
	@Test
	public void testgetAllDoctor() {
		List<Speciality> specialities = new ArrayList<Speciality>();
		specialities.add(speciality1);
		specialities.add(speciality2);
		when(specialityRepositoryMock.findAll()).thenReturn(specialities);
		assertEquals(2, specialityController.getAllSpeciality().size());
	}

	// Testing the Create Specialty
		@Test
		public void testCreatePatient() {
			Speciality speciality = new Speciality("General");
			when(specialityRepositoryMock.findBySpecialityName(speciality.getSpecialityName())).thenReturn(Optional.empty());
			when(specialityRepositoryMock.save(speciality)).thenReturn(speciality);
			assertEquals("General", specialityController.createSpeciality(speciality).getSpecialityName());
		}
}
