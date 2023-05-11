package com.saas.web.filter;

import com.saas.web.entity.exception.TenantNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.saas.entity.constant.TenantConstants.TENANT_KEY;
import static com.saas.sidecar.TenantMapping.VALID_TENANTS;
import static com.saas.web.entity.RequestHeaderConstants.TENANT_HTTP_HEADER;

@Component
public class TenantIdentifierFilter implements WebFilter {
    private final int FIRST = 0;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var tenantHeaders = exchange.getRequest().getHeaders().get(TENANT_HTTP_HEADER);
        if (Objects.isNull(tenantHeaders)) {
            throw new TenantNotFoundException("Tenant ID header not found");
        }

        var tenant = tenantHeaders.get(FIRST);
        if (!VALID_TENANTS.contains(tenant)) {
            exchange.getResponse().writeWith(Mono.error(new TenantNotFoundException("Not a valid tenant")));
            return chain.filter(exchange);
        }

        return chain
                .filter(exchange)
                .contextWrite(ctx -> ctx.put(TENANT_KEY, tenant));
    }
}
