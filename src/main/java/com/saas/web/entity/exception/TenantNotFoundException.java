package com.saas.web.entity.exception;

public class TenantNotFoundException extends RuntimeException{
    public TenantNotFoundException(String message) {
        super(message);
    }
}
