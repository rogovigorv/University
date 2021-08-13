package com.foxminded.university.config;

import com.foxminded.university.generate.SqlRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
@ComponentScan("com.foxminded.university")
@Slf4j
public class HibernateConfig {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";
    private static final String DATA_SCRIPT = "insert_test_data.sql";

    private final Environment environment;
    private final DatasourceConfig datasourceConfig;

    @Autowired
    public HibernateConfig(Environment environment, DatasourceConfig datasourceConfig) {
        this.environment = environment;
        this.datasourceConfig = datasourceConfig;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws NamingException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.foxminded.university.models");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
//        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }

    @Bean
    public TransactionManager transactionManager() throws NamingException {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    public DataSource dataSource() throws NamingException {
        return datasourceConfig.setup();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws NamingException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());

        log.info("JdbcTemplate bean created");

        return jdbcTemplate;
    }

    @Bean
    public void mainSqlRunner() throws NamingException {
        SqlRunner sqlRunner = new SqlRunner(jdbcTemplate());
        sqlRunner.runScript(CREATE_SCRIPT);
        sqlRunner.runScript(DATA_SCRIPT);
    }
}
