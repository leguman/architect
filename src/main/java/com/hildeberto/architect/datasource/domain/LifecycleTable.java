package com.hildeberto.architect.datasource.domain;

import com.hildeberto.architect.domain.Lifecycle;
import com.hildeberto.architect.domain.LifecycleState;

import javax.persistence.*;

/**
 *
 * @author Hildeberto Mendonca
 */
@Entity
@DiscriminatorValue("TABLE")
public class LifecycleTable extends Lifecycle {
    
    @ManyToOne
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private DatabaseTable databaseTable;

    public DatabaseTable getDatabaseTable() {
        return databaseTable;
    }

    public void setDatabaseTable(DatabaseTable databaseTable) {
        this.databaseTable = databaseTable;
        if(this.databaseTable.getState() != null) {
            this.state = this.databaseTable.getState();
        }
        else {
            this.state = LifecycleState.getDefaultState();
        }
    }
}