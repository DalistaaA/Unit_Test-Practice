package com.personal.unit_test_practice.service;

import java.util.List;
import java.util.Optional;

import com.personal.unit_test_practice.entities.Patient;

public interface patientService {

	Patient savePatient(Patient patient);
	List<Patient> getAllPatients();
	Optional<Patient> getPatientById(long id);
	Patient updatePatient(long id,Patient updatedPatient);
    void deletePatient(long id);
}
