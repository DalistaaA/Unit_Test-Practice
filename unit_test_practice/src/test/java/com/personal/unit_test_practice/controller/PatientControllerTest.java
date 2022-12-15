package com.personal.unit_test_practice.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.unit_test_practice.entities.Patient;
import com.personal.unit_test_practice.exception.InvalidRequestException;
import com.personal.unit_test_practice.service.patientService;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	patientService patientService;

	Patient patient1 = new Patient(1l, "Rayven Yor", 23, "Cebu Philippines");
	Patient patient2 = new Patient(2l, "David Landup", 27, "New York USA");
	Patient patient3 = new Patient(3l, "Jane Doe", 31, "New York USA");

	@Test
	public void getAllRecordsTest_Success() throws Exception {
		List<Patient> patientRecords = new ArrayList<>(Arrays.asList(patient1, patient2, patient3));

		Mockito.when(patientService.getAllPatients()).thenReturn(patientRecords);

		mockMvc.perform(MockMvcRequestBuilders.get("/patient")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(3)))
				.andExpect(jsonPath("$[2].name", is("Jane Doe")));
//		.andExpect(jsonPath("$[2].name", Matchers.is("Jane Doe")));
	}

	@Test
	public void getPatientByIdTest_Success() throws Exception {
		Mockito.when(patientService.getPatientById(patient1.getPatientId())).thenReturn(Optional.of(patient1));

		mockMvc.perform(MockMvcRequestBuilders.get("/patient/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Rayven Yor")));
	}
	
	@Test
	public void createPatientRecord_Success() throws Exception {
		Mockito.when(patientService.savePatient(patient1)).thenReturn(patient1);
		
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/patient")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(patient1));
		
		mockMvc.perform(mockHttpServletRequestBuilder)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.name", is("Rayven Yor")));
	}
	
	@Test
	public void updatePatient_Success() throws Exception {
		Patient updatedRecord = Patient.builder().patientId(1l).name("Rayven Yor").age(47).address("Philippines").build();
		Mockito.when(patientService.getPatientById(patient1.getPatientId())).thenReturn(Optional.of(patient1));
		Mockito.when(patientService.savePatient(updatedRecord)).thenReturn(updatedRecord);
		
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.put("/patient")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(updatedRecord));
		
		mockMvc.perform(mockHttpServletRequestBuilder)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.name", is("Rayven Yor")));
	}
	
	@Test
	public void updatePatient_withNullId() throws Exception {
		Patient updatedRecord = Patient.builder().name("Sherlock Holmes").age(40).address("221B Baker Street").build();

		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.put("/patient")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(updatedRecord));
		
		mockMvc.perform(mockHttpServletRequestBuilder)
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidRequestException))
		.andExpect(result -> assertEquals("Patient or ID must not be null!", result.getResolvedException().getMessage()));
	}
	
	@Test
	public void updatePatient_recordNotFound() throws Exception {
		Patient updatedRecord = Patient.builder().patientId(5l).name("Sherlock Holmes").age(40).address("221B Baker Street").build();

//		Optional<Patient> patient = patientService.getPatientById(updatedRecord.getPatientId());
		Mockito.when(patientService.getPatientById(5l)).thenReturn(null);
		
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.put("/patient")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(updatedRecord));
		
		mockMvc.perform(mockHttpServletRequestBuilder)
		.andExpect(status().isBadRequest());
//		.andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
//		.andExpect(result -> assertEquals("Patient with ID 5 does not exist", result.getResolvedException().getMessage()))
//		.andDo(result -> System.out.println(result.getResolvedException().getMessage()));
	}
	
	@Test
	public void deletePatientById_Success() throws Exception {
		Mockito.when(patientService.getPatientById(patient2.getPatientId())).thenReturn(Optional.of(patient2));
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/patient/2")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void deletePatientById_NotFound() throws Exception {
		Mockito.when(patientService.getPatientById(5l)).thenReturn(Optional.of(null));
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/patient/5")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		assertThrows(NullPointerException.class, () -> patientService.deletePatient(5l));
//		.andExpect(result -> assertThrows(NullPointerException.class, () -> patientService.getPatientById(5l)));
//		.andExpect(result -> assertEquals("Patient with ID 5 does not exist.", result.getResolvedException().getMessage()));
//		
	}

	
}
