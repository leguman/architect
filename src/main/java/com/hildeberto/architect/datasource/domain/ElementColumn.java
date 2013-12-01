package com.hildeberto.architect.datasource.domain;

/**
 * @author Hildeberto Mendonca
 */
public class ElementColumn {
    private String name;
    private String type;
    private Integer size;
    private Integer precision;
    private boolean isNullable;
    private String defaultValue;
    private boolean indexed;
    private DatabaseElement databaseElement;
    private PrimaryKeyColumn primaryKey;
    private ColumnIndex columnIndex;
    private ForeignKeyColumn foreignKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean nullable) {
        isNullable = nullable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isIndexed() {
        return indexed;
    }

    public void setIndexed(boolean indexed) {
        this.indexed = indexed;
    }

    public DatabaseElement getDatabaseElement() {
        return databaseElement;
    }

    public void setDatabaseElement(DatabaseElement databaseElement) {
        this.databaseElement = databaseElement;
    }

    public PrimaryKeyColumn getPrimaryKey() {
        return this.primaryKey;
    }

    public void setPrimaryKey(PrimaryKeyColumn primaryKey) {
        this.primaryKey = primaryKey;
    }

    public ColumnIndex getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(ColumnIndex columnIndex) {
        this.columnIndex = columnIndex;
    }

    public ForeignKeyColumn getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(ForeignKeyColumn foreignKey) {
        this.foreignKey = foreignKey;
    }
}
