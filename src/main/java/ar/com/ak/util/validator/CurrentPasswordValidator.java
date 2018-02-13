package ar.com.ak.util.validator;

import ar.com.ak.bean.SessionController;
import ar.com.ak.util.MessageFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class CurrentPasswordValidator implements Validator {

    public static final String CONTRASEÑA_INCORRECTA = "contraseña_incorrecta";

    @Override
    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {

        if (!SessionController.getUsuarioEnSession().getContraseña().equals(value.toString())) {
            throw new ValidatorException(
                    MessageFactory.error(MessageFactory.getMessage(CONTRASEÑA_INCORRECTA)));
        }
    }
}
