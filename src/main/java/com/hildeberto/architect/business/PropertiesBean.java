package com.hildeberto.architect.business;

import com.hildeberto.architect.domain.Properties;
import com.hildeberto.architect.domain.PropertiesType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Hildeberto Mendonca
 */
@Stateless
@LocalBean
public class PropertiesBean {
    
    @PersistenceContext
    private EntityManager em;

    public Map<String, String> findProperties() {
        Map<String, String> propertiesMap = new HashMap<>();
        List<Properties> properties = em.createQuery("select ap from Properties ap", Properties.class).getResultList();
        for(Properties property: properties) {
            propertiesMap.put(property.getPropertyKey(), property.getPropertyValue());
        }

        // If there is no property in the database, it creates all properties according to the enumeration Properties.
        if(propertiesMap.isEmpty()) {
            PropertiesType[] props = PropertiesType.values();
            for(int i = 0;i < props.length;i++) {
                propertiesMap.put(props[i].name(), null);
            }
            create(propertiesMap);
        }
        // If there is more properties in the enumeration than in the database, then additional enumerations are created.
        else if(PropertiesType.values().length > propertiesMap.size()) {
            PropertiesType[] props = PropertiesType.values();
            for(int i = 0;i < props.length;i++) {
                if(!propertiesMap.containsKey(props[i].name())) {
                    propertiesMap.put(props[i].name(), null);
                    create(props[i].name(), null);
                }
            }
        }
        // If there is more persisted properties than in the enumeration, then exceding properties are removed.
        else if(PropertiesType.values().length < propertiesMap.size()) {
            // entries from database
            Set<Map.Entry<String, String>> propEntries = propertiesMap.entrySet();

            Iterator<Map.Entry<String, String>> iProps = propEntries.iterator();
            Map.Entry<String, String> entry;
            PropertiesType[] props = PropertiesType.values();
            while(iProps.hasNext()) {
                entry = iProps.next();
                for(int i = 0; i < props.length; i++) {
                    if(!entry.getKey().equals(props[i].name())) {
                        remove(entry.getKey());
                    }
                }
            }
        }
        return propertiesMap;
    }

    /**
     * Returns the ApplicationProperty that corresponds to the informed enum
     * property. If the ApplicationProperty does not exist, then it creates one
     * with the default value.
     */
    public Properties findProperties(PropertiesType propertiesType) {
        Properties properties;
        try {
            properties = (Properties)em.createQuery("select ap from Properties ap where ap.propertyKey = :key")
                                                                         .setParameter("key", propertiesType.name())
                                                                         .getSingleResult();
        }
        catch(NoResultException nre) {
            Map<String, String> applicationProperties = findProperties();
            String key = propertiesType.name();
            properties = new Properties(key, (String)applicationProperties.get(key));
        }
        return properties;
    }

    public void save(Map<String, String> properties) {
        List<Properties> existingProperties = em.createQuery("select ap from Properties ap").getResultList();
        String value;
        for(Properties property: existingProperties) {
            value = properties.get(property.getPropertyKey());
            property.setPropertyValue(value);
            em.merge(property);
        }
    }

    private void create(Map<String, String> properties) {
        Set<Map.Entry<String, String>> props = properties.entrySet();
        Iterator<Map.Entry<String, String>> iProps = props.iterator();
        Properties appProp;
        Map.Entry<String, String> entry;
        while(iProps.hasNext()) {
            entry = iProps.next();
            appProp = new Properties(entry.getKey(), entry.getValue());
            em.persist(appProp);
        }
    }

    private void create(String key, String value) {
        Properties appProp = new Properties(key, value);
        em.persist(appProp);
    }

    private void remove(String key) {
        Properties properties = em.find(Properties.class, key);
        em.remove(properties);
    }
}