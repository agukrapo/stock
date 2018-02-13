package ar.com.ak.model;

import ar.com.ak.util.EntityUtil.SortBy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.Length;
import org.hibernate.validator.Min;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Articulo extends AuditableEntity {

    public static final String CODIGO_PROPERTY_NAME = "codigo";
    public static final String PROVEEDOR_PROPERTY_NAME = "proveedor";
    public static final String CATEGORIA_PROPERTY_NAME = "categoria";
    public static final String TALLE_PROPERTY_NAME = "talle";
    public static final String CODIGO_BARRA_PROPERTY_NAME = "codigoBarra";
    public static final String STOCK_PROPERTY_NAME = "stock";
    private static final long serialVersionUID = -8471307890369314177L;
    @NotEmpty
    @NaturalId(mutable = true)
    @SortBy
    @Length(max = 100)
    private String codigo;

    @Column(unique = true)
    @Length(max = 255)
    private String codigoBarra;

    @NotEmpty
    @Length(max = 1000)
    private String descripcion;

    @ManyToOne
    @NotNull
    private Talle talle;

    @ManyToOne
    @NotNull
    private Categoria categoria;

    @ManyToOne
    @NotNull
    private Empresa proveedor;

    @Min(0)
    private Double costo;

    @Min(0)
    private Double coeficientePrecio;

    @NotNull
    private Double precioUnitario;

    @NotNull
    @Min(0)
    private Integer stock;

    //CONSTRUCTORES
    public Articulo() {
    }

    public Articulo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public int compareTo(IdentifiableEntity entity) {
        Articulo articulo = (Articulo) entity;

        Integer result = getCodigoPrefix().compareTo(articulo.getCodigoPrefix());

        if (result.equals(0)) {
            result = new Long(getCodigoSufix()).compareTo(new Long(articulo.getCodigoSufix()));
        }

        return result;
    }

    public String getCodigoPrefix() {
        return codigo.substring(0, codigo.lastIndexOf("-"));
    }

    public String getCodigoSufix() {
        return codigo.substring(codigo.lastIndexOf("-") + 1);
    }

    // GETTERS & SETTERS

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Talle getTalle() {
        return talle;
    }

    public void setTalle(Talle talle) {
        this.talle = talle;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Empresa getProveedor() {
        return proveedor;
    }

    public void setProveedor(Empresa proveedor) {
        this.proveedor = proveedor;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getCoeficientePrecio() {
        return coeficientePrecio;
    }

    public void setCoeficientePrecio(Double coeficientePrecio) {
        this.coeficientePrecio = coeficientePrecio;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
