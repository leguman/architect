package com.hildeberto.architect.datasource.domain;

/**
 * @author Hildeberto Mendonca
 */
public class ColumnIndex {
    private String  name;
    private Boolean nonUnique;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getNonUnique() {
        return nonUnique;
    }

    public void setNonUnique(Boolean nonUnique) {
        this.nonUnique = nonUnique;
    }
}
