package com.hildeberto.architect.datasource.domain;

import com.hildeberto.architect.domain.Lifecycle;
import com.hildeberto.architect.domain.LifecycleState;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Hildeberto Mendonca
 */
@Entity
@DiscriminatorValue("TABLE")
public class LifecycleTable extends Lifecycle {
    
    @ManyToOne
    @JoinColumn(name="object")
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