package ar.com.ak.bean;

import ar.com.ak.model.Parametro;
import ar.com.ak.repo.Repositories;
import ar.com.ak.util.FacesUtil;
import ar.com.ak.util.MessageFactory;
import ar.com.ak.util.Util;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;

public class ParametrosStockController implements Serializable {

    private static final long serialVersionUID = 6343538680604143999L;

    private Parametro currentItem;

    private List<SelectItem> proveedores;
    private List<SelectItem> categorias;
    private List<SelectItem> talles;

    public ParametrosStockController() {
        currentItem = Repositories.Parametro().load();

        proveedores = Util.populateSelectItems(Repositories.Empresa().listAll());
        categorias = Util.populateSelectItems(Repositories.Categoria().listAll());
        talles = Util.populateSelectItems(Repositories.Talle().listAll());
    }

    public void aceptar() {
        FacesContext.getCurrentInstance();
        Repositories.Parametro().saveOrUpdate(currentItem);
        FacesUtil.addMessage(MessageFactory.operacionExitoMessage());
    }

    // GETTERS & SETTERS
    public Parametro getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Parametro currentItem) {
        this.currentItem = currentItem;
    }

    public List<SelectItem> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<SelectItem> proveedores) {
        this.proveedores = proveedores;
    }

    public List<SelectItem> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<SelectItem> categorias) {
        this.categorias = categorias;
    }

    public List<SelectItem> getTalles() {
        return talles;
    }

    public void setTalles(List<SelectItem> talles) {
        this.talles = talles;
    }
}
