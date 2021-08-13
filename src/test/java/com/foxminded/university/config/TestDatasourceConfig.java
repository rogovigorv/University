package com.foxminded.university.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Component
@PropertySource("classpath:h2.properties")
@Slf4j
public class TestDatasourceConfig implements DatasourceConfig {
    @Value("${h2.driver}")
    String driver;

    @Value("${h2.url}")
    String url;

    @Value("${h2.user}")
    String user;

    @Value("${h2.password}")
    String password;

    @Override
    public DataSource setup() throws NamingException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        log.info("Test dataSource bean created");

        return dataSource;
    }
}
