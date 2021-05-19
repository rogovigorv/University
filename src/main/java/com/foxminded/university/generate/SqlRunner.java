package com.foxminded.university.generate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import java.sql.SQLException;
import java.util.Objects;

@Component
@Slf4j
public class SqlRunner extends ScriptUtils {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SqlRunner(JdbcTemplate jdbcTemplate) {
                this.jdbcTemplate = jdbcTemplate;
    }

    public void runScript(String scriptFile) {
        log.info("SqlRunner started");

        try {
            executeSqlScript(Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection(),
                    new ClassPathResource(scriptFile));
        } catch (SQLException throwables) {
            log.info("A SQL exception occurred ", throwables);
            throwables.printStackTrace();
        }
    }
}
