package ar.com.ak.model;

import ar.com.ak.util.EntityUtil;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

@MappedSuperclass
public class IdentifiableEntity implements Serializable, Comparable<IdentifiableEntity> {

    private static final long serialVersionUID = 178872321850179114L;

    @Id
    @GeneratedValue
    protected Long id;

    @Version
    protected Integer version;

    //CONSTRUCTORES
    public IdentifiableEntity() {
    }

    public IdentifiableEntity clone() {
        IdentifiableEntity result = null;
        try {
            result = (IdentifiableEntity) BeanUtils.cloneBean(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //FUNCIONES OBJECT
    @Override
    public boolean equals(Object obj) {
        return EntityUtil.equals(this, obj);
    }

    @Override
    public int hashCode() {
        return EntityUtil.hashCode(this);
    }

    @Override
    public String toString() {
        return EntityUtil.toString(this);
    }

    @Override
    public int compareTo(IdentifiableEntity entity) {
        return EntityUtil.compareTo(this, entity);
    }

    // GETTERS & SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
