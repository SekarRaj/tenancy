package com.saas.web.config;

import com.saas.web.advise.ExceptionHandlerAdvice;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebConfig implements WebFluxConfigurer {
    private final ExceptionHandlerAdvice exceptionHandlerAdvice;

    public WebConfig(ExceptionHandlerAdvice exceptionHandlerAdvice) {
        this.exceptionHandlerAdvice = exceptionHandlerAdvice;
    }
//    @Override
//    public void configureExceptionHandler(ExceptionHandlerConfigurer configurer) {
//        configurer.defaultContentType(MediaType.APPLICATION_JSON);
//        configurer.handler(exceptionHandlerAdvice);
//    }

}
