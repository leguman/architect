package com.hildeberto.architect.domain;

/**
 * To be implemented by entity classes.
 *
 * @author Hildeberto Mendonca
 * @param <T>
 */
public interface Identified<T extends Number> {
    public T getId();
    public void setId(T id);
}