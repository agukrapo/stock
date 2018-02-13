package ar.com.ak.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class AuditableEntity extends IdentifiableEntity {

    private static final long serialVersionUID = 341162233100725347L;

    @Column(updatable = false)
    protected Date creado;

    @ManyToOne
    @JoinColumn(updatable = false)
    protected Usuario creador;

    @Column(insertable = false)
    protected Date actualizado;

    @ManyToOne
    @JoinColumn(insertable = false)
    protected Usuario actualizador;

    //CONSTRUCTORES
    public AuditableEntity() {
    }

    // GETTERS & SETTERS
    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public Date getActualizado() {
        return actualizado;
    }

    public void setActualizado(Date actualizado) {
        this.actualizado = actualizado;
    }

    public Usuario getActualizador() {
        return actualizador;
    }

    public void setActualizador(Usuario actualizador) {
        this.actualizador = actualizador;
    }


}
