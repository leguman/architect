package com.hildeberto.architect.domain.datasource;

import com.hildeberto.architect.domain.Lifecycle;
import com.hildeberto.architect.domain.LifecycleState;
import com.hildeberto.architect.domain.datasource.DatabaseView;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Hildeberto Mendonca
 */
@Entity
@DiscriminatorValue("VIEW")
public class LifecycleView extends Lifecycle {

    @ManyToOne
    @JoinColumn(name="object")
    private DatabaseView databaseView;

    public DatabaseView getDatabaseView() {
        return databaseView;
    }

    public void setDatabaseView(DatabaseView databaseView) {
        this.databaseView = databaseView;
        if(this.databaseView.getState() != null) {
            this.state = this.databaseView.getState();
        }
        else {
            this.state = LifecycleState.getDefaultState();
        }
    }
}