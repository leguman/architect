package com.hildeberto.architect.datasource.business;

import com.hildeberto.architect.business.AbstractBean;
import com.hildeberto.architect.datasource.domain.*;
import com.hildeberto.architect.domain.LifecycleState;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Hildeberto Mendonca
 */
@Stateless
public class DatabaseTableBean extends AbstractBean<DatabaseTable> {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private DatabaseElementBean databaseElementBean;

    @EJB
    private DatabaseConnectionBean databaseConnectionBean;

    public DatabaseTableBean() {
        super(DatabaseTable.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<DatabaseTable> findByDatabaseInstance(DatabaseInstance database, LifecycleState state) {
        if(state == null) {
            return em.createQuery("select dt from DatabaseTable dt where dt.databaseInstance = :database order by dt.name asc", DatabaseTable.class)
                     .setParameter("database", database)
                     .getResultList();
        }
        else {
            return em.createQuery("select dt from DatabaseTable dt where dt.databaseInstance = :database and dt.state = :state order by dt.name asc", DatabaseTable.class)
                     .setParameter("database", database)
                     .setParameter("state", state)
                     .getResultList();
        }
    }

    public List<DatabaseTable> findByDatabaseSchema(DatabaseSchema schema, LifecycleState state) {
        if(state == null) {
            return em.createQuery("select dt from DatabaseTable dt where dt.databaseSchema = :schema order by dt.name asc", DatabaseTable.class)
                     .setParameter("schema", schema)
                     .getResultList();
        }
        else {
            return em.createQuery("select dt from DatabaseTable dt where dt.databaseSchema = :schema and dt.state = :state order by dt.name asc", DatabaseTable.class)
                     .setParameter("schema", schema)
                     .setParameter("state", state)
                     .getResultList();
        }
    }

    public DatabaseTable findByName(DatabaseInstance database, DatabaseSchema schema, String name) {
        DatabaseTable databaseTable = null;
        if(database != null && name != null) {
            if(schema == null) {
                databaseTable = em.createQuery("select dt from DatabaseTable dt where dt.databaseInstance = :database and dt.name = :name", DatabaseTable.class)
                                  .setParameter("database", database)
                                  .setParameter("name", name)
                                  .getSingleResult();
            }
            else {
                databaseTable = em.createQuery("select dt from DatabaseTable dt where dt.databaseInstance = :database and dt.databaseSchema = :schema and dt.name = :name", DatabaseTable.class)
                        .setParameter("database", database)
                        .setParameter("schema", schema)
                        .setParameter("name", name)
                        .getSingleResult();
            }
        }
        return databaseTable;
    }

    public List<DatabaseTable> findPhysicalTables(DatabaseInstance databaseInstance, DatabaseSchema databaseSchema) {
        List<DatabaseTable> tables = new ArrayList<>();

        try {
            Connection connection = databaseConnectionBean.getConnection(databaseInstance);
            DatabaseMetaData dbMetaData = connection.getMetaData();
            ResultSet rs;
            if(databaseSchema != null) {
                rs = dbMetaData.getTables(null, databaseSchema.getName(), "%" , new String[]{"TABLE"});
            }
            else {
                rs = dbMetaData.getTables(null, null, "%", new String[]{"TABLE"});
            }
            DatabaseTable table;
            while(rs.next()) {
                table = new DatabaseTable();
                table.setName(rs.getString(3));
                table.setDescription(rs.getString(5));
                table.setDatabaseInstance(databaseInstance);
                table.setDatabaseSchema(databaseSchema);
                tables.add(table);
            }
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }

        return tables;
    }

    public List<ElementColumn> findPhysicalColumns(DatabaseElement databaseElement) {
        List<ElementColumn> columns = new ArrayList<>();

        try {
            Connection connection = databaseConnectionBean.getConnection(databaseElement.getDatabaseInstance());
            DatabaseMetaData dbMetaData = connection.getMetaData();

            // Load table's columns
            ResultSet rs;
            if(databaseElement.getDatabaseSchema() != null) {
                rs = dbMetaData.getColumns(null, databaseElement.getDatabaseSchema().getName(), databaseElement.getName() , "%");
            }
            else {
                rs = dbMetaData.getColumns(null, null, databaseElement.getName(), "%");
            }
            while(rs.next()) {
                ElementColumn column = new ElementColumn();
                column.setName(rs.getString(4));
                column.setType(rs.getString(6));
                column.setSize(rs.getInt(7));
                column.setPrecision(rs.getInt(9));
                column.setDefaultValue(rs.getString(13));
                switch(rs.getString(18)) {
                    case "YES":
                        column.setNullable(true);
                        break;
                    case "NO":
                        column.setNullable(false);
                        break;
                    default:
                        column.setNullable(true);
                }
                column.setDatabaseElement(databaseElement);
                columns.add(column);
            }

            // Load table's primary keys.
            if(databaseElement.getDatabaseSchema() != null) {
                rs = dbMetaData.getPrimaryKeys(null, databaseElement.getDatabaseSchema().getName(), databaseElement.getName());
            }
            else {
                rs = dbMetaData.getPrimaryKeys(null, null, databaseElement.getName());
            }
            while (rs.next()) {
                PrimaryKeyColumn primaryKey = new PrimaryKeyColumn();
                primaryKey.setName(rs.getString("PK_NAME"));
                for(ElementColumn column: columns) {
                    if(column.getName().equals(rs.getString("COLUMN_NAME"))) {
                        column.setPrimaryKey(primaryKey);
                        break;
                    }
                }
            }

            // Load table's indexes and set the columns with their indexes.
            if(databaseElement.getDatabaseSchema() != null) {
                rs = dbMetaData.getIndexInfo(null, databaseElement.getDatabaseSchema().getName(), databaseElement.getName() , false, true);
            }
            else {
                rs = dbMetaData.getIndexInfo(null, null, databaseElement.getName(), false, true);
            }
            while (rs.next()) {
                ColumnIndex columnIndex = new ColumnIndex();
                columnIndex.setNonUnique(rs.getBoolean(4));
                columnIndex.setName(rs.getString(6));
                for(ElementColumn column: columns) {
                    if(column.getName().equals(rs.getString(9))) {
                        column.setColumnIndex(columnIndex);
                        column.setIndexed(true);
                        break;
                    }
                }
            }

            // load table's relationships.
            if(databaseElement.getDatabaseSchema() != null) {
                rs = dbMetaData.getImportedKeys(null, databaseElement.getDatabaseSchema().getName(), databaseElement.getName());
            }
            else {
                rs = dbMetaData.getImportedKeys(null, null, databaseElement.getName());
            }
            while (rs.next()) {
                ForeignKeyColumn foreignKey = new ForeignKeyColumn();
                foreignKey.setName(rs.getString("FK_NAME"));
                foreignKey.setParentTable(new DatabaseTable(rs.getString("PKTABLE_NAME")));
                String schemaName = rs.getString("PKTABLE_SCHEM");
                if(schemaName != null) {
                    foreignKey.setParentSchema(new DatabaseSchema());
                }
                for(ElementColumn column: columns) {
                    if(column.getName().equals(rs.getString("FKCOLUMN_NAME"))) {
                        column.setForeignKey(foreignKey);
                        break;
                    }
                }
            }
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }

        return columns;
    }

    @Override
    public DatabaseTable save(DatabaseTable databaseTable) {
        return (DatabaseTable) databaseElementBean.save(databaseTable);
    }
}