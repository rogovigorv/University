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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.foxminded.university",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SpringConfig.class))
@PropertySource("classpath:h2.properties")
@Slf4j
public class SpringConfigTest {
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

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());

        return jdbcTemplate;
    }

    @Bean
    public GroupDao groupDaoMock() {
        return Mockito.mock(GroupDao.class);
    }

    @Bean
    public GroupService groupService() {
        return new GroupService(groupDaoMock());
    }

    @Bean
    public TeacherDao teacherDaoMock() {
        return Mockito.mock(TeacherDao.class);
    }

    @Bean
    public TeacherService teacherService() {
        return new TeacherService(teacherDaoMock());
    }

    @Bean
    public LectureDao lectureDaoMock() {
        return Mockito.mock(LectureDao.class);
    }

    @Bean
    public LectureService lectureService() {
        return new LectureService(lectureDaoMock());
    }

    @Bean
    public StudentDao studentDaoMock() {
        return Mockito.mock(StudentDao.class);
    }

    @Bean
    public StudentService studentService() {
        return new StudentService(studentDaoMock());
    }
}
