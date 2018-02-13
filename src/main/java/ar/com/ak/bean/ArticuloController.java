package ar.com.ak.bean;

import ar.com.ak.model.*;
import ar.com.ak.repo.Repositories;
import ar.com.ak.util.*;
import org.apache.commons.lang.BooleanUtils;
import org.richfaces.event.UploadEvent;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

public class ArticuloController extends GenericCRUDController<Articulo> {

    public static final String ERROR_OBTENIENDO = "Error procesando valor de la celda ";
    public static final String REPORT_FILENAME = "articulo.jasper";
    private static final long serialVersionUID = -1130715545196621386L;
    private List<SelectItem> proveedores;
    private List<SelectItem> categorias;
    private List<SelectItem> talles;

    private Empresa filtroProveedor;
    private Categoria filtroCategoria;
    private Talle filtroTalle;

    private Boolean filtroStockBajo;

    private Boolean abortarImportacionError;

    private String log;

    // CONTRUCTORS
    public ArticuloController() {
        super.setCrudClass(Articulo.class);
        super.setCrudDAO(Repositories.Articulo());
        reiniciar();
    }

    public ArticuloController(Talle talle) {
        super.setCrudClass(Articulo.class);
        super.setCrudDAO(Repositories.Articulo());
        filtroTalle = talle;
        reiniciar();
    }

    public ArticuloController(Categoria categoria) {
        super.setCrudClass(Articulo.class);
        super.setCrudDAO(Repositories.Articulo());
        filtroCategoria = categoria;
        reiniciar();
    }

    public ArticuloController(Empresa proveedor) {
        super.setCrudClass(Articulo.class);
        super.setCrudDAO(Repositories.Articulo());
        filtroProveedor = proveedor;
        reiniciar();
    }

    private ArticuloController(Boolean dummy) {
        proveedores = new ArrayList<SelectItem>();
        categorias = new ArrayList<SelectItem>();
        talles = new ArrayList<SelectItem>();
    }

    public static ArticuloController getDummyInstance() {
        return new ArticuloController(true);
    }

    @Override
    public void agregar() {
        super.agregar();

        Parametro param = Repositories.Parametro().load();

        super.getCurrentItem().setCoeficientePrecio(param.getCoeficientePrecioInicial());

        super.getCurrentItem().setProveedor(param.getProveedorPorDefecto());
        super.getCurrentItem().setCategoria(param.getCategoriaPorDefecto());
        super.getCurrentItem().setTalle(param.getTallePorDefecto());
    }

    public void generarCodigo() {
        Repositories.Articulo().generarCodigo(super.getCurrentItem());
    }

    public void calcularPrecioUnitario() {
        Repositories.Articulo().calcularPrecioUnitario(super.getCurrentItem());
    }

    @Override
    public void reiniciar() {
        initParameters();
        super.reiniciar();

        proveedores = Util.populateSelectItems(Repositories.Empresa().listAll());
        categorias = Util.populateSelectItems(Repositories.Categoria().listAll());
        talles = Util.populateSelectItems(Repositories.Talle().listAll());

        abortarImportacionError = Boolean.TRUE;

        log = null;
    }

    private void initParameters() {
        if (filtroProveedor != null) {
            super.addListEqParameter(Articulo.PROVEEDOR_PROPERTY_NAME, filtroProveedor);
        } else {
            super.removeListEqParameter(Articulo.PROVEEDOR_PROPERTY_NAME);
        }

        if (filtroCategoria != null) {
            super.addListEqParameter(Articulo.CATEGORIA_PROPERTY_NAME, filtroCategoria);
        } else {
            super.removeListEqParameter(Articulo.CATEGORIA_PROPERTY_NAME);
        }

        if (filtroTalle != null) {
            super.addListEqParameter(Articulo.TALLE_PROPERTY_NAME, filtroTalle);
        } else {
            super.removeListEqParameter(Articulo.TALLE_PROPERTY_NAME);
        }

        if (BooleanUtils.isTrue(filtroStockBajo)) {
            super.addListBetweenParameter(
                    Articulo.STOCK_PROPERTY_NAME, 0, Repositories.Parametro().getStockMinimo());
        } else {
            super.removeListEqParameter(Articulo.STOCK_PROPERTY_NAME);
        }
    }

    public void exportarListado() {
        ReportUtil.printListToPDF(REPORT_FILENAME, getItemList(), null);
    }

    public void importarExcel() {
    }

    public void cerrarImportacion() {
        reiniciar();
    }

    public void fileUploadListener(UploadEvent event) throws Exception {
        List<List<Object>> lineasExcel = MSOfficeUtil.getLinesExcel(event.getUploadItem().getFile());

        StringBuffer log = new StringBuffer();

        Integer bien = 0;
        Integer conError = 0;

        for (int i = 0; i < lineasExcel.size(); i++) {
            if (i != 0) {
                List<Object> linea = lineasExcel.get(i);

                Articulo articulo = new Articulo();

                String causaError = null;

                try {
                    // Descripcion
                    try {
                        String descripcion = Util.trim((String) linea.get(2));
                        if (Check.isNotEmpty(descripcion)) {
                            articulo.setDescripcion(descripcion);
                        } else {
                            throw new RuntimeException();
                        }

                    } catch (Exception e) {
                        causaError = ERROR_OBTENIENDO + "Descripcion";
                        throw new RuntimeException();
                    }

                    // Stock
                    try {
                        Integer stock = Util.doubleToInteger((Double) linea.get(6));
                        if (stock != null) {
                            articulo.setStock(stock);
                        } else {
                            throw new RuntimeException();
                        }

                    } catch (Exception e) {
                        causaError = ERROR_OBTENIENDO + "Stock";
                        throw new RuntimeException();
                    }

                    // Codigo de barra
                    try {
                        String codigoBarra;

                        if (linea.get(1) instanceof Double) {
                            codigoBarra = Util.doubleToInteger((Double) linea.get(1)).toString();
                        } else {
                            codigoBarra = (linea.get(1) != null) ? Util.trim(linea.get(1).toString()) : null;
                        }

                        if (Check.isNotEmpty(codigoBarra)) {
                            if (Repositories.Articulo().loadByCodigoBarra(codigoBarra) != null) {
                                throw new RuntimeException();
                            }
                            articulo.setCodigoBarra(codigoBarra);
                        }

                    } catch (Exception e) {
                        causaError = ERROR_OBTENIENDO + "Codigo de barra";
                        throw new RuntimeException();
                    }

                    // Coeficiente de precio
                    try {
                        Double coeficientePrecio = (Double) linea.get(4);
                        if (coeficientePrecio != null) {
                            articulo.setCoeficientePrecio(coeficientePrecio);
                        } else {
                            articulo.setCoeficientePrecio(
                                    Repositories.Parametro().load().getCoeficientePrecioInicial());
                        }

                    } catch (Exception e) {
                        causaError = ERROR_OBTENIENDO + "Coeficiente de precio";
                        throw new RuntimeException();
                    }

                    // Costo
                    try {
                        Double costo = (Double) linea.get(3);
                        articulo.setCosto(costo);

                    } catch (Exception e) {
                        causaError = ERROR_OBTENIENDO + "Costo";
                        throw new RuntimeException();
                    }

                    // Precio Unitario
                    try {
                        Double precioUnitario = (Double) linea.get(5);
                        if (precioUnitario != null) {
                            articulo.setPrecioUnitario(precioUnitario);
                        } else {
                            if (articulo.getCosto() != null) {
                                Repositories.Articulo().calcularPrecioUnitario(articulo);
                            } else {
                                throw new RuntimeException();
                            }
                        }

                    } catch (Exception e) {
                        causaError = ERROR_OBTENIENDO + "Precio Unitario";
                        throw new RuntimeException();
                    }

                    // Proveedor
                    try {
                        String proveedor = Util.trim((String) linea.get(7));
                        if (Check.isNotEmpty(proveedor)) {
                            articulo.setProveedor(Repositories.Empresa().saveIfTransientOrLoad(
                                    new Empresa(proveedor)));
                        } else {
                            articulo.setProveedor(Repositories.Parametro().load().getProveedorPorDefecto());
                        }

                    } catch (Exception e) {
                        causaError = ERROR_OBTENIENDO + "Proveedor";
                        throw new RuntimeException();
                    }

                    // Categoria
                    try {
                        String categoria = Util.trim((String) linea.get(8));
                        if (Check.isNotEmpty(categoria)) {
                            articulo.setCategoria(Repositories.Categoria().saveIfTransientOrLoad(
                                    new Categoria(categoria)));
                        } else {
                            articulo.setCategoria(Repositories.Parametro().load().getCategoriaPorDefecto());
                        }

                    } catch (Exception e) {
                        causaError = ERROR_OBTENIENDO + "Categoria";
                        throw new RuntimeException();
                    }

                    // Talle
                    try {
                        String talle;
                        if (linea.get(9) instanceof Double) {
                            talle = Util.doubleToInteger((Double) linea.get(9)).toString();
                        } else {
                            talle = (linea.get(9) != null) ? Util.trim(linea.get(9).toString()) : null;
                        }

                        if (Check.isNotEmpty(talle)) {
                            articulo.setTalle(Repositories.Talle().saveIfTransientOrLoad(
                                    new Talle(talle)));
                        } else {
                            articulo.setTalle(Repositories.Parametro().load().getTallePorDefecto());
                        }

                    } catch (Exception e) {
                        causaError = ERROR_OBTENIENDO + "Talle";
                        throw new RuntimeException();
                    }

                    // C�digo
                    try {
                        String codigo = Util.trim((String) linea.get(0));
                        if (Check.isNotEmpty(codigo)) {
                            if (Repositories.Articulo().loadByCodigo(codigo) != null) {
                                throw new RuntimeException();
                            }
                            articulo.setCodigo(codigo);
                        } else {
                            Repositories.Articulo().flush();
                            Repositories.Articulo().generarCodigo(articulo);
                        }

                    } catch (Exception e) {
                        causaError = ERROR_OBTENIENDO + "Codigo";
                        throw new RuntimeException();
                    }

                    Repositories.Articulo().saveOrUpdate(articulo);
                    bien++;

                } catch (Exception e) {
                    log.append("Linea " + (i + 1) + " : " + causaError + "<br/>\n");
                    conError++;

                    if (abortarImportacionError) {
                        HibernateUtil.abortTransaction();
                        break;
                    }
                }
            }
        }

        log.append("<br/>\nTerminado<br/>\n");
        if (conError > 0 && abortarImportacionError) {
            log.append("Proceso interrumpido debido a un error<br/>\n");
        } else {
            log.append((lineasExcel.size() - 1) + " linea/s procesada/s<br/>\n");
            log.append(bien + " linea/s correcta/s<br/>\n");
            log.append(conError + " linea/s erronea/s<br/>\n");
        }

        this.log = log.toString();
    }

    // GETTERS & SETTERS

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

    public Empresa getFiltroProveedor() {
        return filtroProveedor;
    }

    public void setFiltroProveedor(Empresa filtroProveedor) {
        this.filtroProveedor = filtroProveedor;
    }

    public Categoria getFiltroCategoria() {
        return filtroCategoria;
    }

    public void setFiltroCategoria(Categoria filtroCategoria) {
        this.filtroCategoria = filtroCategoria;
    }

    public Talle getFiltroTalle() {
        return filtroTalle;
    }

    public void setFiltroTalle(Talle filtroTalle) {
        this.filtroTalle = filtroTalle;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Boolean getFiltroStockBajo() {
        return filtroStockBajo;
    }

    public void setFiltroStockBajo(Boolean filtroStockBajo) {
        this.filtroStockBajo = filtroStockBajo;
    }

    public Boolean getAbortarImportacionError() {
        return abortarImportacionError;
    }

    public void setAbortarImportacionError(Boolean abortarImportacionError) {
        this.abortarImportacionError = abortarImportacionError;
    }
}
