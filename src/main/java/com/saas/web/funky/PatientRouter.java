package com.saas.web.funky;

import com.saas.entity.model.Patient;
import com.saas.service.PatientService;
import com.saas.web.entity.exception.PatientException;
import com.saas.web.entity.response.SuccessfulResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.saas.utils.ReactorUtils.errorIfEmpty;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class PatientRouter {
    private final PatientService patientService;

    private static final String PATIENT_NOT_FOUND = "Patient not ";

    public PatientRouter(PatientService patientService) {
        this.patientService = patientService;
    }

    @Bean
    RouterFunction<ServerResponse> getPatientIdRoute() {
        return route(GET("/patient/{id}"),
                req -> {
                    var patientMono = patientService.findById(Long.valueOf(req.pathVariable("id")));
                    var requestId = req.attributes().get("org.springframework.web.server.ServerWebExchange.LOG_ID").toString();
                    var response = patientMono
                            .transform(m -> errorIfEmpty(m, () -> new PatientException(PatientException.ExceptionCode.NOT_FOUND)))
                            .map(patient -> SuccessfulResponse.<Patient>builder()
                                    .status(HttpStatus.OK.value())
                                    .requestId(requestId)
                                    .data(patient)
                                    .build());

                    return ok().body(response, SuccessfulResponse.class);
                });
    }
}
