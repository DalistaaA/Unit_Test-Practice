package com.personal.unit_test_practice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.filters.CsrfPreventionFilter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.unit_test_practice.entities.Employee;
import com.personal.unit_test_practice.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@Autowired
	private ObjectMapper objectMapper;

	//@Test
	public void getAllEmployeesTest() throws Exception {
		List<Employee> listofEmployees = new ArrayList<>();
		listofEmployees.add(new Employee(1, "Arikarirajan", "Dalistaa", "adalistaa@gmail.com"));
		listofEmployees.add(new Employee(2, "Ravindran", "Keerthana", "keerthana@gmail.com"));
		listofEmployees.add(new Employee(3, "Ganeswaran", "Umasuthan", "umasuthan@gmail.com"));
		listofEmployees.add(new Employee(4, "Viswarupan", "Sinthuvamsan", "sinthuvamsan@gmail.com"));
		Mockito.when(employeeService.getAllEmployees()).thenReturn(listofEmployees);

		String url = "/employees";
		MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andDo(print()).andReturn();
		String actualJsonResponse = mvcResult.getResponse().getContentAsString();
		System.out.println(actualJsonResponse);

		String expectedJsonResponse = objectMapper.writeValueAsString(listofEmployees);
		System.out.println(expectedJsonResponse);

		assertThat(actualJsonResponse).isEqualToIgnoringCase(expectedJsonResponse);

	}

	//@Test
	public void createEmployeeTest() throws JsonProcessingException, Exception {
		Employee employee = new Employee("Arikarirajan", "Dalistaa", "adalistaa@gmail.com");
		Employee savedEmployee = new Employee(5, "Arikarirajan", "Dalistaa", "adalistaa@gmail.com");

		Mockito.when(employeeService.saveEmployee(employee)).thenReturn(savedEmployee);

		String url = "/employee";
		mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(employee)))
				.andExpect(status().isCreated()).andDo(print());

	}

	/* This is for @NotBlank Annotations */
//	@Test
	public void testFirstNameMustNotBeBlank() throws JsonProcessingException, Exception {
		Employee employee = new Employee(null, "Dalistaa", "adalistaa@gmail.com");

		String url = "/employee";
		mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(employee)))
				.andExpect(status().isBadRequest());

		Mockito.verify(employeeService, times(0)).saveEmployee(employee);
	}
	
	@Test
	public void updateEmployeeTest() throws JsonProcessingException, Exception {
		long employeeId = 1L;
		Employee existsEmployee = new Employee(employeeId,"Arikarirajan", "Dalistaa", "adalistaa@gmail.com");
		Employee savedEmployee = new Employee(employeeId, "Arikarirajan", "Dalistaa", "adalistaa@gmail.com");

		Mockito.when(employeeService.updateEmployee(employeeId,existsEmployee)).thenReturn(savedEmployee);

		String url = "/employee/{id}";
		mockMvc.perform(put(url, employeeId).contentType("application/json").content(objectMapper.writeValueAsString(existsEmployee)))
				.andExpect(status().isOk()).andDo(print());

	}
	
	
	

}

