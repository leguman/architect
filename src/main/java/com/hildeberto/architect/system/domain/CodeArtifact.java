package com.hildeberto.architect.system.domain;

import com.hildeberto.architect.domain.Identified;
import com.hildeberto.architect.domain.LifecycleState;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Hildeberto Mendonça
 */
@Entity
@Inheritance
@Table(name="code_artifact")
@DiscriminatorColumn(name="artifact_type")
public abstract class CodeArtifact implements Serializable, Identified<Integer> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "application")
    private Application application;

    @ManyToOne
    @JoinColumn(name = "module")
    private Module module;

    @ManyToOne
    @JoinColumn(name = "package")
    private Package pack;

    @Lob
    @Size(max = 32700)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_state")
    private LifecycleState state;

    public CodeArtifact() {
    }

    public CodeArtifact(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LifecycleState getState() {
        return state;
    }

    public void setState(LifecycleState state) {
        this.state = state;
    }

    /**
     * If the artifact has a name that also describes its package, then this
     * method simplifies it to its basic form.
     */
    public void simplifyName() {
        if(name.contains(".")) {
            this.name = name.substring(name.lastIndexOf(".") + 1);
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CodeArtifact)) {
            return false;
        }
        CodeArtifact other = (CodeArtifact) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return (this.pack != null ? this.pack.getName() + ".<b>" : "<b>") + this.name + "</b>";
    }
}