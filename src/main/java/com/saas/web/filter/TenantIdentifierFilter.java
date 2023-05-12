package com.saas.web.filter;

import com.saas.web.entity.exception.TenantNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

import static com.saas.entity.constant.TenantConstants.CONN_FACTORY;
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

        var connectionFactories = Map.of("TenantOne", "t1", "TenantTwo", "t2");
        return chain
                .filter(exchange)
                .contextWrite(ctx -> ctx.put(CONN_FACTORY, connectionFactories.getOrDefault(tenant, "t3")));
    }
}
