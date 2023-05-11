package com.saas.web.advise;

import com.saas.web.entity.exception.TenantNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

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

        return exchange.getResponse()
                .writeWith(
                        Mono.just(exchange.getResponse()
                                .bufferFactory()
                                .wrap(ex.getMessage().getBytes(StandardCharsets.UTF_8))
                        )
                );
    }
}
