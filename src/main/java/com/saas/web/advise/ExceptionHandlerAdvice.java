package com.saas.web.advise;

import com.saas.utils.JsonUtils;
import com.saas.web.entity.response.ExceptionResponse;
import com.saas.web.entity.exception.TenantNotFoundException;
import com.saas.web.entity.response.ExceptionResponseData;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerAdvice implements WebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof TenantNotFoundException) {
            return handleTenantNotFound(exchange, (TenantNotFoundException) ex);
        }
        return Mono.error(ex);
    }


    private Mono<Void> handleTenantNotFound(ServerWebExchange exchange, TenantNotFoundException ex) {
        exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
        var response = ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .requestId(exchange.getRequest().getId())
                .data(
                        ExceptionResponseData.builder()
                                .path(exchange.getRequest().getPath().toString())
                                .error(ex.getLocalizedMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                )
                .build();

        return exchange.getResponse()
                .writeWith(
                        Mono.just(exchange.getResponse()
                                .bufferFactory()
                                .wrap(JsonUtils.convertToJson(response).getBytes(StandardCharsets.UTF_8))
                        )
                );
    }
}
