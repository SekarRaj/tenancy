package com.saas.web.filter;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.saas.entity.constant.TenantConstants.TENANT_KEY;
import static com.saas.web.entity.RequestHeaderConstants.TENANT_HTTP_HEADER;

public class TenantIdentifierFilter implements WebFilter {
    private final int FIRST = 0;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        var tenantHeaders = exchange.getRequest().getHeaders().get(TENANT_HTTP_HEADER);

        if(Objects.isNull(tenantHeaders)) {
            return chain.filter(exchange);
        }

        var tenant = tenantHeaders.get(FIRST);
        return chain
                .filter(exchange)
                .contextWrite(ctx -> ctx.put(TENANT_KEY, tenant));

    }
}
