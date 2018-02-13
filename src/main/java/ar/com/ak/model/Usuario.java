package ar.com.ak.model;

import ar.com.ak.util.EntityUtil.SortBy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import javax.persistence.Entity;

@Entity
public class Usuario extends IdentifiableEntity {

    private static final long serialVersionUID = 6951570521282306475L;

    @NotEmpty
    @NaturalId(mutable = true)
    @SortBy
    @Length(max = 30)
    private String nombre;

    @NotEmpty
    @Length(min = 4, max = 30)
    private String contraseña;

    @NotNull
    private Boolean superusuario;

    //CONSTRUCTORES
    public Usuario() {
    }

    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    public Usuario(String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.superusuario = Boolean.FALSE;
    }

    public Usuario(String nombre, String contraseña, Boolean superusuario) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.superusuario = superusuario;
    }

    // GETTERS & SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Boolean getSuperusuario() {
        return superusuario;
    }

    public void setSuperusuario(Boolean superusuario) {
        this.superusuario = superusuario;
    }
}