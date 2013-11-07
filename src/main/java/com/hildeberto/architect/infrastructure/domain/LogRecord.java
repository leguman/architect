package com.hildeberto.architect.infrastructure.domain;

import com.hildeberto.architect.domain.Identified;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Hildeberto Mendonca
 */
@Entity
@Table(name="log_record")
public class LogRecord implements Serializable, Identified<Long> {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="server")
    private Server server;
    
    @ManyToOne
    @JoinColumn(name="log")
    private ServerLog serverLog;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date instant;
    
    @Enumerated(EnumType.STRING)
    private LogLevel level;

    private String product;
    
    @Column(name="logger_name")
    private String loggerName;
    
    @Column(name="key_value_pairs")
    private String keyValuePairs;
    
    private String message;
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public ServerLog getServerLog() {
        return serverLog;
    }

    public void setServerLog(ServerLog serverLog) {
        this.serverLog = serverLog;
    }

    public Date getInstant() {
        return instant;
    }

    public void setInstant(Date instant) {
        this.instant = instant;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getKeyValuePairs() {
        return keyValuePairs;
    }

    public void setKeyValuePairs(String keyValuePairs) {
        this.keyValuePairs = keyValuePairs;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LogRecord)) {
            return false;
        }
        LogRecord other = (LogRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hildeberto.architect.domain.infrastructure.LogRecord[ id=" + id + " ]";
    }
}