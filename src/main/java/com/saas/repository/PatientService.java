package com.saas.repository;

import com.saas.entity.model.Patient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class PatientService {

//    private final DatabaseClient dbClient;
    public Mono<Patient> findById(String id) {
        return Mono.just(Patient.builder()
                .firstName("John")
                .lastName("Doe")
                .id(123L)
                .build());
    }
}
