package com.foxminded.university;

import com.foxminded.university.config.SpringConfig;
import com.foxminded.university.generate.SqlRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Main {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";

    public static void main(String[] args) {
        log.info("Application started");

        ApplicationContext context =
                new AnnotationConfigApplicationContext(SpringConfig.class);

        log.info("Application context created");

        context.getBean(SqlRunner.class).runScript(CREATE_SCRIPT);

        log.info("The application worked correctly");
    }
}
