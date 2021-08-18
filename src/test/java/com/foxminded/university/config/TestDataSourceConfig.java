package com.foxminded.university.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:h2.properties")
@Profile("test")
@Slf4j
public class TestDataSourceConfig {
    @Value("${h2.driver}")
    String driver;

    @Value("${h2.url}")
    String url;

    @Value("${h2.user}")
    String user;

    @Value("${h2.password}")
    String password;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        log.info("Test dataSource bean created");

        return dataSource;
    }
}
