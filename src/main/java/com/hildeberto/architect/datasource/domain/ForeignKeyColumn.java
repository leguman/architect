package com.hildeberto.architect.datasource.domain;

/**
 * @author Hildeberto Mendonca
 */
public class ForeignKeyColumn {

    private String name;
    private DatabaseElement parentTable;
    private DatabaseSchema parentSchema;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DatabaseElement getParentTable() {
        return parentTable;
    }

    public void setParentTable(DatabaseElement parentTable) {
        this.parentTable = parentTable;
    }

    public DatabaseSchema getParentSchema() {
        return this.parentSchema;
    }

    public void setParentSchema(DatabaseSchema parentSchema) {
        this.parentSchema = parentSchema;
    }
}