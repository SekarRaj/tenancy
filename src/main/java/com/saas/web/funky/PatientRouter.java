package com.saas.web.funky;

import com.saas.entity.model.Patient;
import com.saas.repository.PatientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class PatientRouter {
    private final PatientService patientService;

    public PatientRouter(PatientService patientService) {
        this.patientService = patientService;
    }

    @Bean
    RouterFunction<ServerResponse> getPatientIdRoute() {
        return route(GET("/patient/{id}"),
                req -> ok().body(patientService.findById(req.pathVariable("id")), Patient.class));
    }
}
