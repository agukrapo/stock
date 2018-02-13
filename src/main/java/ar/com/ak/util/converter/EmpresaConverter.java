package ar.com.ak.util.converter;

import ar.com.ak.model.Empresa;
import ar.com.ak.repo.Repositories;
import ar.com.ak.util.Check;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class EmpresaConverter implements Converter {

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Empresa result = null;

        if (Check.isNotEmpty(value)) {
            result = Repositories.Empresa().loadByNaturalId(new Empresa(value));
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
