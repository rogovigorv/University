package com.foxminded.university.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

public interface DatasourceConfig {
    DataSource setup() throws NamingException;
}
