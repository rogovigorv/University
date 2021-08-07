package com.foxminded.university.config;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.LectureDao;
import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.service.GroupService;
import com.foxminded.university.service.LectureService;
import com.foxminded.university.service.StudentService;
import com.foxminded.university.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.TransactionManager;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.foxminded.university",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SpringConfig.class,
                HibernateConfig.class, SpringMvcDispatcherServletInitializer.class}))
@PropertySource("classpath:h2.properties")
@PropertySource("classpath:database.properties")
@Slf4j
public class SpringConfigTest {
    private final Environment environment;

    @Value("${h2.driver}")
    String driver;

    @Value("${h2.url}")
    String url;

    @Value("${h2.user}")
    String user;

    @Value("${h2.password}")
    String password;

    public SpringConfigTest(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());

        return jdbcTemplate;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.foxminded.university.models");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
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

    @Bean
    public GroupDao groupDaoMock() {
        return Mockito.mock(GroupDao.class);
    }

    @Bean
    public GroupService groupServiceMock() {
        return new GroupService(groupDaoMock());
    }

    @Bean
    public TeacherDao teacherDaoMock() {
        return Mockito.mock(TeacherDao.class);
    }

    @Bean
    public TeacherService teacherServiceMock() {
        return new TeacherService(teacherDaoMock());
    }

    @Bean
    public LectureDao lectureDaoMock() {
        return Mockito.mock(LectureDao.class);
    }

    @Bean
    public LectureService lectureServiceMock() {
        return new LectureService(lectureDaoMock());
    }

    @Bean
    public StudentDao studentDaoMock() {
        return Mockito.mock(StudentDao.class);
    }

    @Bean
    public StudentService studentServiceMock() {
        return new StudentService(studentDaoMock());
    }
}
