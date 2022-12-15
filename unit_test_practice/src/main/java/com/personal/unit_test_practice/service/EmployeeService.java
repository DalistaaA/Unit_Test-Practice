package com.personal.unit_test_practice.service;

import java.util.List;
import java.util.Optional;

import com.personal.unit_test_practice.entities.Employee;

public interface EmployeeService {

	Employee saveEmployee(Employee employee);
	List<Employee> getAllEmployees();
	Optional<Employee> getEmployeeById(long id);
	Employee updateEmployee(long id,Employee updatedEmployee);
    void deleteEmployee(long id);
	
}
