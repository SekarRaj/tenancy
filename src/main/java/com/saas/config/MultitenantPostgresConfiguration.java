package com.saas.config;

import com.saas.persistence.routing.PostgresTenantConnectionFactory;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;

import static java.util.Map.entry;

@Configuration
@EnableR2dbcRepositories
@EnableTransactionManagement
public class MultitenantPostgresConfiguration extends AbstractR2dbcConfiguration {
    @Bean
    public ConnectionFactory tOneConnectionFactory() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5432)
                .database("t1_db")
                .username("postgres")
                .password("password123")
                .build());
    }

    @Bean
    public ConnectionFactory tTwoConnectionFactory() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5432)
                .database("t2_db")
                .username("postgres")
                .password("password123")
                .build());
    }

    @Bean
    public ConnectionFactory tThreeConnectionFactory() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5432)
                .database("t3_db")
                .username("postgres")
                .password("password123")
                .build());
    }

    @Override
    public ConnectionFactory connectionFactory() {
        var connectionFactory = postgresConnectionFactory();
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    private AbstractRoutingConnectionFactory postgresConnectionFactory(){
        var routingConnectionFactory = new PostgresTenantConnectionFactory();

        routingConnectionFactory.setLenientFallback(false);
        routingConnectionFactory.setTargetConnectionFactories(tenants());

        return routingConnectionFactory;
    }

    private Map<String, ConnectionFactory> tenants() {
        return Map.ofEntries(
                entry("t1", tOneConnectionFactory()),
                entry("t2", tTwoConnectionFactory()),
                entry("t3", tThreeConnectionFactory())
        );
    }

}
