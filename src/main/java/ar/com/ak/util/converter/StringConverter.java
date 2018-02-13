package ar.com.ak.util.converter;

import ar.com.ak.util.Util;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class StringConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        String result = null;

        if (value != null && value.trim().length() == 0) {
            if (component instanceof EditableValueHolder) {
                ((EditableValueHolder) component).setSubmittedValue(null);
            }
        } else {
            result = Util.trim(value);
        }

        return result;
    }

    public String getAsString(FacesContext facesContext, UIComponent component,
                              Object value) {
        return value == null ? null : value.toString();
    }

}