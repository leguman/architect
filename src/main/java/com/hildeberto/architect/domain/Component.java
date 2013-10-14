package com.hildeberto.architect.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author mendoncafilh
 */
@Entity
@DiscriminatorValue("COMPONENT")
public class Component extends CodeArtifact {
}
