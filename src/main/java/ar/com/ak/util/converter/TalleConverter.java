package ar.com.ak.util.converter;

import ar.com.ak.model.Talle;
import ar.com.ak.repo.Repositories;
import ar.com.ak.util.Check;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class TalleConverter implements Converter {

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Talle result = null;

        if (Check.isNotEmpty(value)) {
            result = Repositories.Talle().loadByNaturalId(new Talle(value));
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
