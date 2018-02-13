package ar.com.ak.model;

import org.hibernate.validator.Min;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Valid;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Parametro extends AuditableEntity {

    private static final long serialVersionUID = -3856515570841696630L;

    @NotNull
    @Min(value = 0)
    private Integer stockMinimo;

    @NotNull
    @Min(value = 1)
    private Double coeficientePrecioInicial;

    @ManyToOne
    @NotNull
    @Valid
    private Empresa proveedorPorDefecto = new Empresa();

    @ManyToOne
    @NotNull
    private Categoria categoriaPorDefecto = new Categoria();

    @ManyToOne
    @NotNull
    private Talle tallePorDefecto = new Talle();

    //CONSTRUCTORES
    public Parametro() {
    }

    // GETTERS & SETTERS
    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Double getCoeficientePrecioInicial() {
        return coeficientePrecioInicial;
    }

    public void setCoeficientePrecioInicial(Double coeficientePrecioInicial) {
        this.coeficientePrecioInicial = coeficientePrecioInicial;
    }

    public Empresa getProveedorPorDefecto() {
        return proveedorPorDefecto;
    }

    public void setProveedorPorDefecto(Empresa proveedorPorDefecto) {
        this.proveedorPorDefecto = proveedorPorDefecto;
    }

    public Talle getTallePorDefecto() {
        return tallePorDefecto;
    }

    public void setTallePorDefecto(Talle tallePorDefecto) {
        this.tallePorDefecto = tallePorDefecto;
    }

    public Categoria getCategoriaPorDefecto() {
        return categoriaPorDefecto;
    }

    public void setCategoriaPorDefecto(Categoria categoriaPorDefecto) {
        this.categoriaPorDefecto = categoriaPorDefecto;
    }
}
