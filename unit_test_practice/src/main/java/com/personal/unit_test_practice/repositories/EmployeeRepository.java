package com.personal.unit_test_practice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal.unit_test_practice.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Optional<Employee> findByEmail(String email);

}
