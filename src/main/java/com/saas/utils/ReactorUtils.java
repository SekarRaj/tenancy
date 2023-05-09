package com.saas.utils;

import reactor.core.publisher.Mono;

import java.util.function.Supplier;

public class ReactorUtils {
    // https://github.com/reactor/reactor-core/issues/917
    public static <T> Mono<T> errorIfEmpty(Mono<T> mono, Supplier<Throwable> throwableSupplier) {
        return mono.switchIfEmpty(Mono.defer(() -> Mono.error(throwableSupplier.get())));
    }
}
