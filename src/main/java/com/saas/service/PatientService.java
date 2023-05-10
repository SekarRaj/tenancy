package com.saas.service;

import com.saas.entity.model.Patient;
import com.saas.repository.PatientRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository patientRepository) {
        this.repository = patientRepository;
    }


    public Mono<Patient> findById(Long id) {
        return repository.findById(id);
//        return Mono.just(Patient.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .id(123L)
//                .build());
    }
}
