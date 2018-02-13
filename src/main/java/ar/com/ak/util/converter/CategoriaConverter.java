package ar.com.ak.util.converter;

import ar.com.ak.model.Categoria;
import ar.com.ak.repo.Repositories;
import ar.com.ak.util.Check;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class CategoriaConverter implements Converter {

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Categoria result = null;

        if (Check.isNotEmpty(value)) {
            result = Repositories.Categoria().loadByNaturalId(new Categoria(value));
        }

        return result;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String result = null;

        if (value != null) {
            result = value.toString();
        }

        return result;
    }
}
