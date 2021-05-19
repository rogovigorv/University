package com.foxminded.university.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
@ComponentScan("com.foxminded.university")
@PropertySource("classpath:postgres.properties")
@Slf4j
public class SpringConfig {
    @Value("${postgres.driver}")
    String driver;

    @Value("${postgres.url}")
    String url;

    @Value("${postgres.user}")
    String user;

    @Value("${postgres.password}")
    String password;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        log.info("DataSource bean created");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());

        log.info("JdbcTemplate bean created");

        return jdbcTemplate;
    }
}
