package ar.com.ak.repo;

import ar.com.ak.model.Transaccion;
import ar.com.ak.util.Util;
import org.hibernate.Query;

import java.sql.Timestamp;
import java.util.*;

public class Transacciones extends GenericHibernateDAO<Transaccion> {


    public List<Integer> listAnios() {
        @SuppressWarnings("unchecked")
        List<Timestamp> tiempos = (List<Timestamp>) listByQuery("select distinct creado from Transaccion");

        SortedSet<Integer> tmp = new TreeSet<Integer>();

        for (Timestamp time : tiempos) {
            tmp.add(Util.getYear(time));
        }

        return Util.collectionToList(tmp);
    }

    @SuppressWarnings("unchecked")
    public List<Transaccion> listByCradoDate(Date creado) {
        List<Transaccion> result = null;

        Query query = getSession().createQuery(
                "from Transaccion " +
                        "where day(creado) = :dia " +
                        "and month(creado) = :mes " +
                        "and year(creado) = :anio"
        );

        query.setInteger("dia", Util.getDayOfMonth(creado));
        query.setInteger("mes", Util.getMonth(creado));
        query.setInteger("anio", Util.getYear(creado));

        result = query.list();

        return result != null ? result : new ArrayList<Transaccion>();
    }

    @SuppressWarnings("unchecked")
    public List<Transaccion> listByCradoMonth(Integer month, Integer year) {
        List<Transaccion> result = null;

        Query query = getSession().createQuery(
                "from Transaccion " +
                        "where month(creado) = :mes " +
                        "and year(creado) = :anio"
        );

        query.setInteger("mes", month);
        query.setInteger("anio", year);

        result = query.list();

        return result != null ? result : new ArrayList<Transaccion>();
    }

    @SuppressWarnings("unchecked")
    public List<Transaccion> listByCradoYear(Integer year) {
        List<Transaccion> result = null;

        Query query = getSession().createQuery(
                "from Transaccion " +
                        "where year(creado) = :anio"
        );

        query.setInteger("anio", year);

        result = query.list();

        return result != null ? result : new ArrayList<Transaccion>();
    }

    public Object[] findTotalsByCradoDate(Date creado) {

        Query query = getSession().createQuery(
                "select sum(costoArticulo), sum(precioUnitarioArticulo), sum(cantidad) from Transaccion " +
                        "where day(creado) = :dia " +
                        "and month(creado) = :mes " +
                        "and year(creado) = :anio"
        );

        query.setInteger("dia", Util.getDayOfMonth(creado));
        query.setInteger("mes", Util.getMonth(creado));
        query.setInteger("anio", Util.getYear(creado));

        return (Object[]) query.uniqueResult();
    }

    public Object[] findTotalsByCradoMonth(Integer month, Integer year) {

        Query query = getSession().createQuery(
                "select sum(costoArticulo), sum(precioUnitarioArticulo), sum(cantidad) from Transaccion " +
                        "where month(creado) = :mes " +
                        "and year(creado) = :anio"
        );

        query.setInteger("mes", month);
        query.setInteger("anio", year);

        return (Object[]) query.uniqueResult();
    }

    public Object[] findTotalsByCradoYear(Integer year) {

        Query query = getSession().createQuery(
                "select sum(costoArticulo), sum(precioUnitarioArticulo), sum(cantidad) from Transaccion " +
                        "where year(creado) = :anio"
        );

        query.setInteger("anio", year);

        return (Object[]) query.uniqueResult();
    }
}