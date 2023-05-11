package com.saas.web.entity.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ExceptionResponseData(String path,
                                    LocalDateTime timestamp,
                                    String error) {
}
