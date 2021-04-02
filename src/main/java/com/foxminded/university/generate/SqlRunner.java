package com.foxminded.university.generate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.SQLException;

@Component
public class SqlRunner extends ScriptUtils {
    private final DataSource dataSource;

    @Autowired
    public SqlRunner(DataSource dataSource) {
                this.dataSource = dataSource;
    }

    public void runScript(String scriptFile) {
        try {
            executeSqlScript(dataSource.getConnection(), new FileSystemResource(scriptFile));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
