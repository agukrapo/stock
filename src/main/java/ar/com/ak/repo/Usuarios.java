package ar.com.ak.repo;

import ar.com.ak.model.Usuario;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class Usuarios extends GenericHibernateDAO<Usuario> {

    public static final String NOMBRE_FIELD_NAME = "nombre";
    public static final String CONTRASEÑA_FIELD_NAME = "contraseña";

    public Usuario checkLogin(String nombre, String contraseña) {
        Usuario result = null;

        List<Usuario> tmp = super.listByCriteria(Restrictions.eq(NOMBRE_FIELD_NAME, nombre),
                Restrictions.eq(CONTRASEÑA_FIELD_NAME, contraseña));

        if (!tmp.isEmpty()) {
            result = tmp.get(0);
        }

        return result;
    }
}
