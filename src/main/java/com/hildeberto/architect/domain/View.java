package com.hildeberto.architect.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Hildeberto Mendonca
 */
@Entity
@DiscriminatorValue("VIEW")
public class View extends DatabaseElement {
}