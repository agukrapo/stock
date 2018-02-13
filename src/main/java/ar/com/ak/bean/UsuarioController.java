package ar.com.ak.bean;

import ar.com.ak.model.Usuario;
import ar.com.ak.repo.Repositories;

public class UsuarioController extends GenericCRUDController<Usuario> {

    private static final long serialVersionUID = 1877946995329439598L;

    public UsuarioController() {
        super.setCrudClass(Usuario.class);
        super.setCrudDAO(Repositories.Usuario());
        super.reiniciar();
    }
}
