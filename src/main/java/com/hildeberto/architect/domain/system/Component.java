package com.hildeberto.architect.domain.system;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Hildeberto Mendonca
 */
@Entity
@DiscriminatorValue("COMPONENT")
public class Component extends CodeArtifact {
}
