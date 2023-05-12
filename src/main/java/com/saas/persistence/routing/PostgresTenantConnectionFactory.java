package com.saas.persistence.routing;

import io.r2dbc.spi.ConnectionFactoryMetadata;
import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory;
import reactor.core.publisher.Mono;

import static com.saas.entity.constant.TenantConstants.CONN_FACTORY;
import static com.saas.utils.ReactorUtils.errorIfEmpty;

public class PostgresTenantConnectionFactory extends AbstractRoutingConnectionFactory {

    static final class PostgresConnectionFactoryMetadata implements ConnectionFactoryMetadata {

        static final PostgresConnectionFactoryMetadata INSTANCE = new PostgresConnectionFactoryMetadata();

        public static final String NAME = "PostgreSQL";

        @Override
        public String getName() {
            return NAME;
        }
    }

    private final String TENANT_MISSING_ERROR = String.format("ContextView does not contain the Lookup Key '%s'", CONN_FACTORY);

    @Override
    protected Mono<Object> determineCurrentLookupKey() {
        return Mono.deferContextual(ctx -> Mono.just(ctx))
                .filter(it -> it.hasKey(CONN_FACTORY))
                .map(it -> it.get(CONN_FACTORY))
                .transform(m -> errorIfEmpty(m, () -> new RuntimeException(TENANT_MISSING_ERROR)));
    }

    @Override
    public ConnectionFactoryMetadata getMetadata() {
        return PostgresConnectionFactoryMetadata.INSTANCE;
    }
}
