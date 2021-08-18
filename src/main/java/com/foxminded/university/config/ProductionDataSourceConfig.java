package com.foxminded.university.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
@Profile("prod")
@PropertySource("classpath:persistence-jndi.properties")
@Slf4j
public class ProductionDataSourceConfig {
    private final Environment environment;

    @Autowired
    public ProductionDataSourceConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() throws NamingException {
        log.info("Production dataSource bean created");
        return (DataSource) new JndiTemplate().lookup(environment.getProperty("jdbc.url"));
    }
}
