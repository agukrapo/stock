package ar.com.ak.model;

import org.hibernate.validator.Length;
import org.hibernate.validator.Min;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Transaccion extends AuditableEntity {

    private static final long serialVersionUID = -4473528037090501607L;

    @Column(nullable = false, updatable = false)
    private Long idArticulo;

    @Column(nullable = false, updatable = false)
    @Length(max = 100)
    private String codigoArticulo;

    @Column(updatable = false)
    @Length(max = 255)
    private String codigoBarraArticulo;

    @Column(nullable = false, updatable = false)
    @Length(max = 1000)
    private String descripcionArticulo;

    @Column(nullable = false, updatable = false)
    @Length(max = 100)
    private String nombreTalleArticulo;

    @Column(nullable = false, updatable = false)
    @Length(max = 100)
    private String nombreCategoriaArticulo;

    @Column(nullable = false, updatable = false)
    @Length(max = 100)
    private String nombreProveedorArticulo;

    @Column(updatable = false)
    @Min(0)
    private Double costoArticulo;

    @Column(nullable = false, updatable = false)
    private Double precioUnitarioArticulo;

    @Column(nullable = false, updatable = false)
    private Integer cantidad;

    //CONSTRUCTORES
    public Transaccion() {
    }

    public Transaccion(Articulo articulo, Integer cantidad) {
        this.idArticulo = articulo.getId();
        this.codigoArticulo = articulo.getCodigo();
        this.codigoBarraArticulo = articulo.getCodigoBarra();
        this.descripcionArticulo = articulo.getDescripcion();
        this.nombreTalleArticulo = articulo.getTalle().getNombre();
        this.nombreCategoriaArticulo = articulo.getCategoria().getNombre();
        this.nombreProveedorArticulo = articulo.getProveedor().getNombre();
        this.costoArticulo = articulo.getCosto();
        this.precioUnitarioArticulo = articulo.getPrecioUnitario();

        this.cantidad = cantidad;
    }

    // GETTERS & SETTERS

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
    }

    public String getCodigoBarraArticulo() {
        return codigoBarraArticulo;
    }

    public void setCodigoBarraArticulo(String codigoBarraArticulo) {
    }

    public String getDescripcionArticulo() {
        return descripcionArticulo;
    }

    public void setDescripcionArticulo(String descripcionArticulo) {
    }

    public String getNombreTalleArticulo() {
        return nombreTalleArticulo;
    }

    public void setNombreTalleArticulo(String nombreTalleArticulo) {
    }

    public String getNombreCategoriaArticulo() {
        return nombreCategoriaArticulo;
    }

    public void setNombreCategoriaArticulo(String nombreCategoriaArticulo) {
    }

    public String getNombreProveedorArticulo() {
        return nombreProveedorArticulo;
    }

    public void setNombreProveedorArticulo(String nombreProveedorArticulo) {
    }

    public Double getCostoArticulo() {
        return costoArticulo;
    }

    public void setCostoArticulo(Double costoArticulo) {
    }

    public Double getPrecioUnitarioArticulo() {
        return precioUnitarioArticulo;
    }

    public void setPrecioUnitarioArticulo(Double precioUnitarioArticulo) {
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
    }
}
