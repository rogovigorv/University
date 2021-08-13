package com.foxminded.university.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.foxminded.university",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SpringConfig.class,
                HibernateConfig.class, SpringMvcDispatcherServletInitializer.class}))
@ComponentScan("com.foxminded.university")
@PropertySource("classpath:h2.properties")
@Profile("test")
@Slf4j
public class SpringConfigTest {
    private final DatasourceConfig datasourceConfig;

    public SpringConfigTest(DatasourceConfig datasourceConfig) {
        this.datasourceConfig = datasourceConfig;
    }

    public DataSource dataSource() throws NamingException {
        return datasourceConfig.setup();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws NamingException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());

        return jdbcTemplate;
    }
}
