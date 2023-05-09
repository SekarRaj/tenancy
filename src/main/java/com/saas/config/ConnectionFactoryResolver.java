package com.saas.config;

import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;

public interface ConnectionFactoryResolver {
    ConnectionFactory resolve(ConnectionFactoryOptions options);
}
