package com.saas.web.entity.exception;

import lombok.val;

public class PatientException extends RuntimeException {

    public static enum ExceptionCode {
        NOT_FOUND("Patient not found");

        private String value;

        private ExceptionCode(String context) {
            this.value = context;
        }
    }
    public PatientException(ExceptionCode code) {
            super(code.value);
    }
}
