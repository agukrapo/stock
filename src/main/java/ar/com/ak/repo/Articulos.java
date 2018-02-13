package ar.com.ak.repo;

import ar.com.ak.model.Articulo;
import ar.com.ak.util.Util;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.Collections;
import java.util.List;

public class Articulos extends GenericHibernateDAO<Articulo> {

    public void calcularPrecioUnitario(Articulo articulo) {

        if (articulo.getCosto() != null && articulo.getCoeficientePrecio() != null) {
            articulo.setPrecioUnitario(
                    Double.valueOf(articulo.getCosto().doubleValue() *
                            articulo.getCoeficientePrecio().doubleValue()));
        }
    }

    public void generarCodigo(Articulo articulo) {

        String prefijo = String.format("%s-%s-%s-",
                Util.abbreviateString(articulo.getProveedor().getNombre()),
                Util.abbreviateString(articulo.getCategoria().getNombre()),
                Util.abbreviateString(articulo.getTalle().getNombre()));

        List<Articulo> tmp = listByCriteria(
                Restrictions.ilike(Articulo.CODIGO_PROPERTY_NAME, prefijo, MatchMode.START));

        Collections.sort(tmp);

        Long count = Long.valueOf(0);

        if (!tmp.isEmpty()) {
            Articulo ultimo = tmp.get(tmp.size() - 1);

            count = Long.parseLong(
                    ultimo.getCodigo().substring(ultimo.getCodigo().lastIndexOf("-") + 1)) + 1;
        }

        articulo.setCodigo(prefijo + count);
    }

    public Articulo loadByCodigoBarra(String codigoBarra) {
        return super.loadByCriteria(Restrictions.eq(Articulo.CODIGO_BARRA_PROPERTY_NAME, codigoBarra));
    }

    public Articulo loadByCodigo(String codigo) {
        return super.loadByCriteria(Restrictions.eq(Articulo.CODIGO_PROPERTY_NAME, codigo));
    }

    public List<Articulo> listEmptyCodigoBarra() {
        return super.listByCriteria(Restrictions.isNull(Articulo.CODIGO_BARRA_PROPERTY_NAME));
    }
}