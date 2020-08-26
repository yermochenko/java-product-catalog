package org.itstep.util.config;

import java.sql.Connection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = {"org.itstep"})
public class ApplicationConfiguration {
	@Bean
	@Scope("prototype")
	public Connection connection() throws OpenConnectionException {
		Connection connection = ConnectionThreadHolder.getConnection();
		if(connection != null) {
			return connection;
		} else {
			throw new OpenConnectionException();
		}
	}
}
