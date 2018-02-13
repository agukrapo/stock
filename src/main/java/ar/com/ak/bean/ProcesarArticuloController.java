package ar.com.ak.bean;

import ar.com.ak.model.Articulo;
import ar.com.ak.model.Transaccion;
import ar.com.ak.repo.Repositories;
import ar.com.ak.util.FacesUtil;
import ar.com.ak.util.MessageFactory;

import java.io.Serializable;
import java.util.List;

public class ProcesarArticuloController implements Serializable {

    public static final String ERROR_CODIGO_BARRA = "error_codigo_barra";
    private static final long serialVersionUID = -657887180081618658L;
    private String codigoBarra;
    private Articulo aProcesar;
    private Integer cantidad;

    private Boolean mostrarProcesarArticulo;

    private List<Articulo> articuloList;
    private Articulo currentArticulo;

    private Integer stockMinimo;

    private Boolean mostrarSeleccionArticulo;
    private ArticuloController articuloController;

    public ProcesarArticuloController() {
        reiniciar();
    }

    public void buscar() {
        aProcesar = Repositories.Articulo().loadByCodigoBarra(codigoBarra);

        if (aProcesar == null) {
            FacesUtil.addMessage(MessageFactory.error(MessageFactory.getMessage(ERROR_CODIGO_BARRA)));

            articuloList = Repositories.Articulo().listEmptyCodigoBarra();
        } else {
            articuloList = null;
            mostrarProcesarArticulo = Boolean.TRUE;
        }
    }

    public void procesar() {
        Integer stockNuevo = (aProcesar.getStock() - cantidad < 0) ?
                0 : aProcesar.getStock() - cantidad;

        aProcesar.setStock(stockNuevo);

        Repositories.Articulo().saveOrUpdate(aProcesar);
        Repositories.Transaccion().saveOrUpdate(new Transaccion(aProcesar, cantidad));

        reiniciar();
        FacesUtil.addMessage(MessageFactory.operacionExitoMessage());
    }

    public void asociarCodigoBarraArticulo() {
        currentArticulo.setCodigoBarra(codigoBarra);

        Repositories.Articulo().saveOrUpdate(currentArticulo);

        reiniciar();
        FacesUtil.addMessage(MessageFactory.operacionExitoMessage());
    }

    public void reiniciar() {
        codigoBarra = null;
        aProcesar = null;
        cantidad = 1;

        mostrarProcesarArticulo = Boolean.FALSE;

        articuloList = null;
        currentArticulo = null;

        stockMinimo = Repositories.Parametro().getStockMinimo();

        mostrarSeleccionArticulo = Boolean.FALSE;
        articuloController = ArticuloController.getDummyInstance();
    }

    public void reiniciarCodigoBarra() {
        codigoBarra = null;
    }

    public void buscarArticulosPorTalle() {
        mostrarSeleccionArticulo = Boolean.TRUE;
        articuloController = new ArticuloController(aProcesar.getTalle());
    }

    public void buscarArticulosPorCategoria() {
        mostrarSeleccionArticulo = Boolean.TRUE;
        articuloController = new ArticuloController(aProcesar.getCategoria());
    }

    public void buscarArticulosPorProveedor() {
        mostrarSeleccionArticulo = Boolean.TRUE;
        articuloController = new ArticuloController(aProcesar.getProveedor());
    }

    public void cerrarSeleccionArticulo() {
        mostrarSeleccionArticulo = Boolean.FALSE;
    }

    // GETTERS & SETTERS
    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Articulo getaProcesar() {
        return aProcesar;
    }

    public void setaProcesar(Articulo aProcesar) {
        this.aProcesar = aProcesar;
    }

    public List<Articulo> getArticuloList() {
        return articuloList;
    }

    public void setArticuloList(List<Articulo> articuloList) {
        this.articuloList = articuloList;
    }

    public Articulo getCurrentArticulo() {
        return currentArticulo;
    }

    public void setCurrentArticulo(Articulo currentArticulo) {
        this.currentArticulo = currentArticulo;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public ArticuloController getArticuloController() {
        return articuloController;
    }

    public void setArticuloController(ArticuloController articuloController) {
        this.articuloController = articuloController;
    }

    public Boolean getMostrarSeleccionArticulo() {
        return mostrarSeleccionArticulo;
    }

    public void setMostrarSeleccionArticulo(Boolean mostrarSeleccionArticulo) {
        this.mostrarSeleccionArticulo = mostrarSeleccionArticulo;
    }

    public Boolean getMostrarProcesarArticulo() {
        return mostrarProcesarArticulo;
    }

    public void setMostrarProcesarArticulo(Boolean mostrarProcesarArticulo) {
        this.mostrarProcesarArticulo = mostrarProcesarArticulo;
    }
}
