package com.foxminded.university;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.sql.SQLException;
import java.util.Objects;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class GroupDaoTest {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";

    @Autowired
    private static JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void setup() throws SQLException {
        System.setProperty("spring.profiles.active", "test");
        ScriptUtils.executeSqlScript(Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection(),
               new ClassPathResource(CREATE_SCRIPT));
    }

    @Test
    void create() {

    }
}
