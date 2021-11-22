package com.withccm.sws.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;

@Configuration
@EnableTransactionManagement
@EnableR2dbcRepositories
public class DataSourceR2DBCConfig extends AbstractR2dbcConfiguration {

	@Bean
	@Primary
	public ConnectionFactory connectionFactory() {
		ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
			.option(ConnectionFactoryOptions.DRIVER, "pool")
			.option(ConnectionFactoryOptions.PROTOCOL, "mysql")
			.option(ConnectionFactoryOptions.HOST, "localhost")
			.option(ConnectionFactoryOptions.PORT, 3306)
			.option(ConnectionFactoryOptions.DATABASE, "mytest")
			.option(ConnectionFactoryOptions.USER, "github")
			.option(ConnectionFactoryOptions.PASSWORD, "test1234")
			.option(ConnectionFactoryOptions.CONNECT_TIMEOUT, Duration.ofMillis(3000))
			.option(Option.valueOf("socketTimeout"), 3000)
			.option(Option.valueOf("allowMultiQueries"), true)
			.option(Option.valueOf("useSSL"), false)
			.build();

		ConnectionFactory connectionFactory = ConnectionFactories.get(options);
		ConnectionPoolConfiguration configuration = ConnectionPoolConfiguration.builder(connectionFactory)
			.build();

		return new ConnectionPool(configuration);
	}

	@Bean
	ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
		return new R2dbcTransactionManager(connectionFactory);
	}

	@Bean
	public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
		R2dbcEntityTemplate r2dbcEntityTemplate = new R2dbcEntityTemplate(connectionFactory);
		return r2dbcEntityTemplate;
	}

}