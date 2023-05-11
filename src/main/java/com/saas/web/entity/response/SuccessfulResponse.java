package com.saas.web.entity.response;

import lombok.Builder;

@Builder
public record SuccessfulResponse<T>(int status,
                                    String requestId,
                                    T data) implements Response {
}
