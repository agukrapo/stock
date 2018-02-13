package ar.com.ak.bean;

import ar.com.ak.model.Empresa;
import ar.com.ak.repo.Repositories;

public class EmpresaController extends GenericCRUDController<Empresa> {

    private static final long serialVersionUID = -1401533941958370934L;

    public EmpresaController() {
        super.setCrudClass(Empresa.class);
        super.setCrudDAO(Repositories.Empresa());
        super.reiniciar();
    }
}
