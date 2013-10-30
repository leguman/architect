package com.hildeberto.architect.domain.datasource;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Hildeberto Mendonca
 */
@Entity
@DiscriminatorValue("TABLE")
public class DatabaseTable extends DatabaseElement {
}