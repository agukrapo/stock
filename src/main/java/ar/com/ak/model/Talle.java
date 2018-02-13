package ar.com.ak.model;

import ar.com.ak.util.EntityUtil.SortBy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

import javax.persistence.Entity;

@Entity
public class Talle extends AuditableEntity {

    private static final long serialVersionUID = -2409844221865543102L;

    @NotEmpty
    @NaturalId(mutable = true)
    @SortBy
    @Length(max = 100)
    private String nombre;

    //CONSTRUCTORES
    public Talle() {
    }

    public Talle(String nombre) {
        this.nombre = nombre;
    }

    // GETTERS & SETTERS

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
