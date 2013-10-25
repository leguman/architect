package com.hildeberto.architect.domain;

/**
 *
 * @author mendoncafilh
 */
public enum LifecycleState {
    DESIGNED, CREATED, INUSE, OBSOLETE, REMOVED;
    
    public static LifecycleState getDefaultState() {
        return INUSE;
    }
}