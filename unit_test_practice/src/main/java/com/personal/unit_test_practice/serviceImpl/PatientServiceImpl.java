package com.personal.unit_test_practice.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.unit_test_practice.entities.Patient;
import com.personal.unit_test_practice.repositories.PatientRepository;
import com.personal.unit_test_practice.service.patientService;

@Service
public class PatientServiceImpl implements patientService {

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public Patient savePatient(Patient patient) {
		return patientRepository.save(patient);
	}

	@Override
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}

	@Override
	public Optional<Patient> getPatientById(long id) {
		return patientRepository.findById(id);
	}

	@Override
	public Patient updatePatient(long id, Patient updatedPatient) {
		Optional<Patient> savedPatient = patientRepository.findById(id);
		if (!(savedPatient.isPresent())) {
			System.out.print((updatedPatient.getPatientId() + "Employee is not exist with given email:"));
		}
		return patientRepository.save(updatedPatient);
	}

	@Override
	public void deletePatient(long id) {
		patientRepository.deleteById(id);

	}

}
