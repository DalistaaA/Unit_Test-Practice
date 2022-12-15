package com.personal.unit_test_practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personal.unit_test_practice.entities.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{

}
