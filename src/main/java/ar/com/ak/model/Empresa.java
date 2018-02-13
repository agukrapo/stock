package ar.com.ak.model;

import ar.com.ak.util.EntityUtil.SortBy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

import javax.persistence.Entity;

@Entity
public class Empresa extends AuditableEntity {

    private static final long serialVersionUID = -1115303483593132490L;

    @NotEmpty
    @NaturalId(mutable = true)
    @SortBy
    @Length(max = 100)
    private String nombre;

    //CONSTRUCTORES
    public Empresa() {
    }

    public Empresa(String nombre) {
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
