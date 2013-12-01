package com.hildeberto.architect.datasource.business;

import com.hildeberto.architect.datasource.domain.DatabaseInstance;

import javax.ejb.Singleton;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Hildeberto Mendonca
 */
@Singleton
public class DatabaseConnectionBean {

    public Connection getConnection(DatabaseInstance databaseInstance) throws NamingException, SQLException {
        Context context = new InitialContext();
        DataSource dataSource = (DataSource) context.lookup(databaseInstance.getDataSource());
        return dataSource.getConnection();
    }
}
