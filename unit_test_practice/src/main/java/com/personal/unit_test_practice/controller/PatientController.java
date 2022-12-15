package com.personal.unit_test_practice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.unit_test_practice.entities.Patient;
import com.personal.unit_test_practice.exception.InvalidRequestException;
import com.personal.unit_test_practice.service.patientService;

@RestController
@RequestMapping(value = "/patient")
public class PatientController {

	@Autowired
	private patientService patientService;

	@GetMapping
	public List<Patient> getAllRecords() {
		return patientService.getAllPatients();
	}

	@GetMapping(value = "{patientId}")
	public Patient getPatientById(@PathVariable(value = "patientId") Long patientId) {
		return patientService.getPatientById(patientId).get();
	}

	@PostMapping
	public Patient createPatientRecord(@RequestBody @Validated Patient patient) {
		return patientService.savePatient(patient);
	}

	@PutMapping
	public Patient updatePatient(@RequestBody Patient patient) throws NotFoundException {
		if(patient == null || patient.getPatientId() == null) {
			throw new InvalidRequestException("Patient or ID must not be null!");
		}
		
		Optional<Patient> optionalRecord = patientService.getPatientById(patient.getPatientId());
	    if (optionalRecord.isEmpty()) {
	        throw new NotFoundException();
	    }
	    Patient existingPatientRecord = optionalRecord.get();

	    existingPatientRecord.setName(patient.getName());
	    existingPatientRecord.setAge(patient.getAge());
	    existingPatientRecord.setAddress(patient.getAddress());
		return patientService.savePatient(existingPatientRecord);
	}
	
	@DeleteMapping(value = "{patientId}")
	public void deletePatientById(@PathVariable(value = "patientId") Long patientId) throws NotFoundException {
	    if (patientService.getPatientById(patientId).isEmpty()) {
	        throw new NotFoundException();
	    }
	    patientService.deletePatient(patientId);
	}

}
