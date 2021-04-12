package com.foxminded.university.config;

import com.foxminded.university.generate.SqlRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private static final String CREATE_SCRIPT = "src\\main\\resources\\create_university_tables.sql";

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/university");
        dataSource.setUsername("jhon_doe");
        dataSource.setPassword("829893");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public SqlRunner sqlRunner() {
        return new SqlRunner(CREATE_SCRIPT, dataSource());
    }
}
