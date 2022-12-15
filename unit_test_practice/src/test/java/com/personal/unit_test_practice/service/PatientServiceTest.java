package com.personal.unit_test_practice.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.personal.unit_test_practice.entities.Patient;
import com.personal.unit_test_practice.repositories.PatientRepository;
import com.personal.unit_test_practice.serviceImpl.PatientServiceImpl;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

	@Mock
	private PatientRepository patientRepository;
	
	@InjectMocks
	private PatientServiceImpl patientServiceImpl;
	
	@BeforeEach
	private void setup() {
		Patient patient = new Patient(1l, "Rayven Yor", 23, "Cebu Philippines");

	}
	
	@Test
	final void testSavePatient() {
//		given
	}

	@Test
	final void testGetAllPatients() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetPatientById() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testUpdatePatient() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testDeletePatient() {
		fail("Not yet implemented"); // TODO
	}

}
