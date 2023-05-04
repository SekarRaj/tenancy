package com.saas.entity.model;

import lombok.Builder;

@Builder
public record Patient(Long id, String firstName, String lastName) { }
