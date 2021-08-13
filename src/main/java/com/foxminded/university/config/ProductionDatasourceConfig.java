package com.foxminded.university.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Component
@Profile("prod")
@PropertySource("classpath:database.properties")
@Slf4j
public class ProductionDatasourceConfig implements DatasourceConfig {
    private final Environment environment;

    @Autowired
    public ProductionDatasourceConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public DataSource setup() throws NamingException {
        log.info("Production dataSource bean created");
        return (DataSource) new JndiTemplate().lookup(environment.getProperty("jdbc.url"));
    }
}
