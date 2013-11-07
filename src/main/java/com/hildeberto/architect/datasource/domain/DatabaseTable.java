package com.hildeberto.architect.datasource.domain;

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