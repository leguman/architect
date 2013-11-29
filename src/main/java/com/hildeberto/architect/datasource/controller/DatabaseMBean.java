package com.hildeberto.architect.datasource.controller;

import com.hildeberto.architect.datasource.business.DatabaseInstanceBean;
import com.hildeberto.architect.datasource.business.DatabaseSchemaBean;
import com.hildeberto.architect.datasource.business.DatabaseTableBean;
import com.hildeberto.architect.datasource.business.DatabaseViewBean;
import com.hildeberto.architect.datasource.domain.DatabaseInstance;
import com.hildeberto.architect.datasource.domain.DatabaseSchema;
import com.hildeberto.architect.datasource.domain.DatabaseTable;
import com.hildeberto.architect.datasource.domain.DatabaseView;
import com.hildeberto.architect.domain.LifecycleState;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Hildeberto Mendonca
 */
@ManagedBean
@RequestScoped
public class DatabaseMBean {

    @EJB
    private DatabaseInstanceBean databaseInstanceBean;

    @EJB
    private DatabaseSchemaBean databaseSchemaBean;

    @EJB
    private DatabaseTableBean databaseTableBean;

    @EJB
    private DatabaseViewBean databaseViewBean;

    private List<DatabaseInstance> databases;
    private List<DatabaseSchema> relatedSchemas;
    private List<DatabaseTable> relatedTables;
    private List<DatabaseView> relatedViews;

    @ManagedProperty(value="#{param.id}")
    private Integer id;

    private DatabaseInstance database;

    private String connectionResult;

    public List<DatabaseInstance> getDatabases() {
        if(databases == null) {
            databases = databaseInstanceBean.findAll();
        }
        return databases;
    }

    public List<DatabaseSchema> getRelatedSchemas() {
        if(relatedSchemas == null && database != null) {
            relatedSchemas = databaseSchemaBean.findByDatabaseInstance(database);
        }
        return relatedSchemas;
    }

    public List<DatabaseTable> getRelatedTables() {
        if(relatedTables == null && database != null) {
            relatedTables = databaseTableBean.findByDatabaseInstance(database, LifecycleState.INUSE);
        }
        return relatedTables;
    }

    public List<DatabaseView> getRelatedViews() {
        if(relatedViews == null && database != null) {
            relatedViews = databaseViewBean.findByDatabaseInstance(database);
        }
        return relatedViews;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DatabaseInstance getDatabase() {
        return this.database;
    }

    public String getConnectionResult() {
        return this.connectionResult;
    }

    @PostConstruct
    public void load() {
        if(id != null) {
            this.database = databaseInstanceBean.find(id);
        }
        else {
            this.database = new DatabaseInstance();
        }
    }

    public String save() {
        databaseInstanceBean.save(this.database);
        return "databases";
    }

    public void testConnection(ActionEvent ae) {
        try {
            System.out.println("start connection to "+ this.database.getDataSource());
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup(this.database.getDataSource());
            Connection connection = dataSource.getConnection();
            this.connectionResult = "Successful!";
            connection.close();
            System.out.println("connected");
        } catch(NamingException | SQLException e) {
            this.connectionResult = "Failure";
            System.out.println("not connected" + e.getMessage());
        }
        System.out.println("end");
    }
}