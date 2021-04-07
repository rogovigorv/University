package com.foxminded.university;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.generate.SqlRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.sql.SQLException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfigTest.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestExecutionListeners()
@ActiveProfiles("h2")
public class GroupDaoTest {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() throws SQLException {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(SpringConfigTest.class);
        context.getBean(SqlRunner.class).runScript(CREATE_SCRIPT);
    }

    @Test
    void anyTest() {

    }
}
