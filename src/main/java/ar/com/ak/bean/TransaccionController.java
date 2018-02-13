package ar.com.ak.bean;

import ar.com.ak.model.Transaccion;
import ar.com.ak.repo.Repositories;
import ar.com.ak.util.MessageFactory;
import ar.com.ak.util.Util;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransaccionController extends GenericCRUDController<Transaccion> {

    public static final String PERIODO_DIARIO = "diario";
    public static final String PERIODO_MENSUAL = "mensual";
    public static final String PERIODO_ANUAL = "anual";
    public static final String MES_ENERO = "enero";
    public static final String MES_FREBERO = "frebero";
    public static final String MES_MARZO = "marzo";
    public static final String MES_ABRIL = "abril";
    public static final String MES_MAYO = "mayo";
    public static final String MES_JUNIO = "junio";
    public static final String MES_JULIO = "julio";
    public static final String MES_AGOSTO = "agosto";
    public static final String MES_SEPTIEMBRE = "septiembre";
    public static final String MES_OCTUBRE = "octubre";
    public static final String MES_NOVIEMBRE = "noviembre";
    public static final String MES_DICIEMBRE = "diciembre";
    private static final long serialVersionUID = -1375008387414250890L;
    private String periodo = PERIODO_DIARIO;
    private List<SelectItem> periodos;

    private Date dia = new Date();
    private Integer mes = Util.getMonth(dia);
    private Integer anio = Util.getYear(dia);

    private List<SelectItem> meses;
    private List<SelectItem> anios;

    private Object[] totales;

    public TransaccionController() {
        setCrudClass(Transaccion.class);
        setCrudDAO(Repositories.Transaccion());

        cargarPeriodos();
        cargarMeses();
        cargarAnios();

        buscarTransacciones();
    }

    public void buscarTransacciones() {
        if (getPeriodoDiario()) {
            setItemList(Repositories.Transaccion().listByCradoDate(dia));
            setTotales(Repositories.Transaccion().findTotalsByCradoDate(dia));

        } else if (getPeriodoMensual()) {
            setItemList(Repositories.Transaccion().listByCradoMonth(mes, anio));
            setTotales(Repositories.Transaccion().findTotalsByCradoMonth(mes, anio));

        } else if (getPeriodoAnual()) {
            setItemList(Repositories.Transaccion().listByCradoYear(anio));
            setTotales(Repositories.Transaccion().findTotalsByCradoYear(anio));
        }
    }

    private void cargarPeriodos() {
        periodos = new ArrayList<SelectItem>();

        periodos.add(new SelectItem(PERIODO_DIARIO,
                MessageFactory.getMessage(getCrudClassName(), PERIODO_DIARIO)));
        periodos.add(new SelectItem(PERIODO_MENSUAL,
                MessageFactory.getMessage(getCrudClassName(), PERIODO_MENSUAL)));
        periodos.add(new SelectItem(PERIODO_ANUAL,
                MessageFactory.getMessage(getCrudClassName(), PERIODO_ANUAL)));
    }

    private void cargarMeses() {
        meses = new ArrayList<SelectItem>();

        meses.add(new SelectItem(1, MessageFactory.getMessage(getCrudClassName(), MES_ENERO)));
        meses.add(new SelectItem(2, MessageFactory.getMessage(getCrudClassName(), MES_FREBERO)));
        meses.add(new SelectItem(3, MessageFactory.getMessage(getCrudClassName(), MES_MARZO)));
        meses.add(new SelectItem(4, MessageFactory.getMessage(getCrudClassName(), MES_ABRIL)));
        meses.add(new SelectItem(5, MessageFactory.getMessage(getCrudClassName(), MES_MAYO)));
        meses.add(new SelectItem(6, MessageFactory.getMessage(getCrudClassName(), MES_JUNIO)));
        meses.add(new SelectItem(7, MessageFactory.getMessage(getCrudClassName(), MES_JULIO)));
        meses.add(new SelectItem(8, MessageFactory.getMessage(getCrudClassName(), MES_AGOSTO)));
        meses.add(new SelectItem(9, MessageFactory.getMessage(getCrudClassName(), MES_SEPTIEMBRE)));
        meses.add(new SelectItem(10, MessageFactory.getMessage(getCrudClassName(), MES_OCTUBRE)));
        meses.add(new SelectItem(11, MessageFactory.getMessage(getCrudClassName(), MES_NOVIEMBRE)));
        meses.add(new SelectItem(12, MessageFactory.getMessage(getCrudClassName(), MES_DICIEMBRE)));
    }

    private void cargarAnios() {
        anios = new ArrayList<SelectItem>();

        for (Integer i : Repositories.Transaccion().listAnios()) {
            anios.add(new SelectItem(i));
        }
    }

    public Boolean getPeriodoDiario() {
        return PERIODO_DIARIO.equals(periodo);
    }

    public Boolean getPeriodoMensual() {
        return PERIODO_MENSUAL.equals(periodo);
    }

    public Boolean getPeriodoAnual() {
        return PERIODO_ANUAL.equals(periodo);
    }

    //	GETTERS & SETTERS
    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public List<SelectItem> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<SelectItem> periodos) {
        this.periodos = periodos;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public List<SelectItem> getMeses() {
        return meses;
    }

    public void setMeses(List<SelectItem> meses) {
        this.meses = meses;
    }

    public List<SelectItem> getAnios() {
        return anios;
    }

    public void setAnios(List<SelectItem> anios) {
        this.anios = anios;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Object[] getTotales() {
        return totales;
    }

    public void setTotales(Object[] totales) {
        this.totales = totales;
    }
}
