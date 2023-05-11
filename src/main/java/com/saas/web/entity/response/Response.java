package com.saas.web.entity.response;

public sealed interface Response permits ExceptionResponse, SuccessfulResponse {
}
