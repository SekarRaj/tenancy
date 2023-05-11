package com.saas.web.entity.response;

import lombok.Builder;

@Builder
public record ExceptionResponse (
        int status,
        String requestId,
        ExceptionResponseData data
) implements Response {
}
