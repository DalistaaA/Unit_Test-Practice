package com.personal.unit_test_practice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long patientId;
    
    @NonNull
    private String name;
 
    @NonNull
    private Integer age;
    
    @NonNull 
    private String address;
}
