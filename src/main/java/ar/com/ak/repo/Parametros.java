package ar.com.ak.repo;

import ar.com.ak.model.Parametro;

import java.util.List;

public class Parametros extends GenericHibernateDAO<Parametro> {

    public Parametro load() {
        List<Parametro> tmp = super.listAll();

        return tmp.isEmpty() ? new Parametro() : tmp.get(0);
    }

    public Integer getStockMinimo() {
        return this.load().getStockMinimo();
    }

}