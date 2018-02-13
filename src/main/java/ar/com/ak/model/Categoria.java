package ar.com.ak.model;

import ar.com.ak.util.EntityUtil.SortBy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

import javax.persistence.Entity;

@Entity
public class Categoria extends AuditableEntity {

    public static final String NOMBRE_PROPERTY_NAME = "nombre";
    private static final long serialVersionUID = -1189864527944348145L;
    @NotEmpty
    @NaturalId(mutable = true)
    @SortBy
    @Length(max = 100)
    private String nombre;

    //CONSTRUCTORES
    public Categoria() {
    }

    public Categoria(String nombre) {
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
