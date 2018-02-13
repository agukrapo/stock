package ar.com.ak.util.validator;

import ar.com.ak.util.MessageFactory;
import org.apache.commons.lang.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class SamePasswordValidator implements Validator {

    public static final String PASSWORD_ID_ATRIBUTE_NAME = "passwordId";
    public static final String CONNTRASEÑA_DISTINTA_ERROR_KEY = "contraseña_distinta";

    @Override
    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {

        String passwordId = (String) component.getAttributes().get(PASSWORD_ID_ATRIBUTE_NAME);

        UIInput passwordInput = (UIInput) component.findComponent(passwordId);

        String password = (String) passwordInput.getValue();

        if (!StringUtils.equals(password, value.toString())) {
            throw new ValidatorException(
                    MessageFactory.error(MessageFactory.getMessage(CONNTRASEÑA_DISTINTA_ERROR_KEY)));
        }
    }
}
