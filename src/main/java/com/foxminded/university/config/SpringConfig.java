package com.foxminded.university.config;

import com.foxminded.university.generate.SqlRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import java.util.Objects;

@Configuration
@ComponentScan("com.foxminded.university")
@PropertySource("classpath:persistence-jndi.properties")
@EnableWebMvc
@Slf4j
public class SpringConfig implements WebMvcConfigurer {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";
    private static final String DATA_SCRIPT = "insert_test_data.sql";

    private final ApplicationContext applicationContext;
    private final Environment environment;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext, Environment environment) {
        this.applicationContext = applicationContext;
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() throws NamingException {
        log.info("DataSource bean created");
        return (DataSource) new JndiTemplate().lookup(Objects.requireNonNull(environment.getProperty("jdbc.url")));
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

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }
}
