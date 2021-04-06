package com.foxminded.university.generate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import java.sql.SQLException;
import java.util.Objects;

@Component
public class SqlRunner extends ScriptUtils {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SqlRunner(JdbcTemplate jdbcTemplat) {
                this.jdbcTemplate = jdbcTemplat;
    }

    public void runScript(String scriptFile) {
        try {
            executeSqlScript(Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection(),
                    new ClassPathResource(scriptFile));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
