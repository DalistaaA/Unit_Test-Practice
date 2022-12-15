package com.personal.unit_test_practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal.unit_test_practice.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
