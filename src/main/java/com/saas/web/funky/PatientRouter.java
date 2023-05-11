package com.saas.web.funky;

import com.saas.entity.model.Patient;
import com.saas.service.PatientService;
import com.saas.web.entity.exception.TenantNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebFilter;

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
                req -> ok().body(patientService.findById(Long.valueOf(req.pathVariable("id"))), Patient.class));
    }

//    @Bean
//    WebFilter tenantIdExceptionToBadRequest() {
//        return ((exchange, chain) -> chain.filter(exchange)
//                .onErrorResume(TenantNotFoundException.class, e -> {
//                    ServerHttpResponse response = exchange.getResponse();
//                    response.setStatusCode(HttpStatus.BAD_REQUEST);
//                    return response.setComplete();
//                })
//        );
//    }
}
