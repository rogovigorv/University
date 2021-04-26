package com.foxminded.university;

import com.foxminded.university.config.SpringConfig;
import com.foxminded.university.generate.SqlRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";

    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(SpringConfig.class);

        context.getBean(SqlRunner.class).runScript(CREATE_SCRIPT);
    }
}
