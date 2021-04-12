package com.foxminded.university.generate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import javax.sql.DataSource;
import java.sql.SQLException;

public class SqlRunner extends ScriptUtils {
    private final String scriptFile;

    @Autowired
    private final DataSource dataSource;

    public SqlRunner(String scriptFile, DataSource dataSource) {
        this.scriptFile = scriptFile;
        this.dataSource = dataSource;
    }

    public void runScript() {
        try {
            executeSqlScript(dataSource.getConnection(), new FileSystemResource(scriptFile));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("Victory!");
    }
}
