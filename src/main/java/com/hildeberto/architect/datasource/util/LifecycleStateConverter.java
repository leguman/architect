package com.hildeberto.architect.datasource.util;

import com.hildeberto.architect.domain.LifecycleState;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author mendoncafilh
 */
@FacesConverter(value = "lifecycleStateConverter")
public class LifecycleStateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if ("DESIGNED".equals(value)) {
            return LifecycleState.DESIGNED;
        }
        if ("CREATED".equals(value)) {
            return LifecycleState.CREATED;
        }
        if ("OBSOLETE".equals(value)) {
            return LifecycleState.OBSOLETE;
        }
        if ("REMOVED".equals(value)) {
            return LifecycleState.REMOVED;
        }
        if ("INUSE".equals(value)) {
            return LifecycleState.INUSE;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        LifecycleState lifecycleState = null;
        if(value instanceof LifecycleState) {
            lifecycleState = (LifecycleState) value;
        }
        String lifecycleStateStr = null;
        if (lifecycleState != null) {
            switch (lifecycleState) {
                case DESIGNED:
                    lifecycleStateStr = "Designed";
                    break;
                case CREATED:
                    lifecycleStateStr = "Created";
                    break;
                case INUSE:
                    lifecycleStateStr = "In Use";
                    break;
                case OBSOLETE:
                    lifecycleStateStr = "Obsolete";
                    break;
                case REMOVED:
                    lifecycleStateStr = "Removed";
                    break;
            }
        }
        return lifecycleStateStr;
    }
}