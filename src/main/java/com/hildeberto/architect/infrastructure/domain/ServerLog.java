package com.hildeberto.architect.infrastructure.domain;

import com.hildeberto.architect.domain.Identified;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Hildeberto Mendonca
 */
@Entity
@Table(name="server_log")
public class ServerLog implements Serializable, Identified<Integer> {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "server")
    private Server server;
    
    private String name;
    
    private String location;
    
    @Column(name="filename_pattern")
    private String filenamePattern;
    
    @Column(name="record_pattern")
    private String recordPattern;
    
    private String filter;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFilenamePattern() {
        return filenamePattern;
    }

    public void setFilenamePattern(String filenamePattern) {
        this.filenamePattern = filenamePattern;
    }

    public String getRecordPattern() {
        return recordPattern;
    }

    public void setRecordPattern(String recordPattern) {
        this.recordPattern = recordPattern;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ServerLog)) {
            return false;
        }
        ServerLog other = (ServerLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}