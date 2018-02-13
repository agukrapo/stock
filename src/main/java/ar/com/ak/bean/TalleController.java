package ar.com.ak.bean;

import ar.com.ak.model.Talle;
import ar.com.ak.repo.Repositories;

public class TalleController extends GenericCRUDController<Talle> {

    private static final long serialVersionUID = -6342541421412864211L;

    public TalleController() {
        super.setCrudClass(Talle.class);
        super.setCrudDAO(Repositories.Talle());
        super.reiniciar();
    }
}
