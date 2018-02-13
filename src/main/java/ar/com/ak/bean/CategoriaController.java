package ar.com.ak.bean;

import ar.com.ak.model.Categoria;
import ar.com.ak.repo.Repositories;
import ar.com.ak.util.ColumnDefinition;

public class CategoriaController extends GenericCRUDController<Categoria> {

    private static final long serialVersionUID = -3256424635481716662L;

    public CategoriaController() {
        setCrudClass(Categoria.class);
        setCrudDAO(Repositories.Categoria());
        reiniciar();

        addColumnDefinition(Categoria.NOMBRE_PROPERTY_NAME, ColumnDefinition.STRING_TYPE, 600);
        addFieldDefinition(Categoria.NOMBRE_PROPERTY_NAME, ColumnDefinition.STRING_TYPE);
    }
}
