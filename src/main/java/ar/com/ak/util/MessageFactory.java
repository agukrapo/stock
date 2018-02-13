package ar.com.ak.util;

import javax.faces.application.FacesMessage;
import java.util.ResourceBundle;

public class MessageFactory {

    public static final String RESOURCE_BUNDLE = "i18n.";
    public static final String RESOURCE_BUNDLE_SISTEMA = RESOURCE_BUNDLE + "Sistema";
    public static final String OPERACION_EXITO_KEY = "operacion_exito";
    public static final String ERROR_FATAL = "error_fatal";

    private MessageFactory() {
    }

    public static FacesMessage error(String summary, String detail) {
        return new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
    }

    public static FacesMessage error(String detail) {
        return new FacesMessage(FacesMessage.SEVERITY_ERROR, detail, detail);
    }

    public static FacesMessage info(String summary, String detail) {
        return new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
    }

    public static FacesMessage info(String detail) {
        return new FacesMessage(FacesMessage.SEVERITY_INFO, "", detail);
    }

    public static FacesMessage fatal(String summary, String detail) {
        return new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail);
    }

    public static FacesMessage fatal(String detail) {
        return new FacesMessage(FacesMessage.SEVERITY_FATAL, "", detail);
    }

    public static FacesMessage operacionExitoMessage() {
        return new FacesMessage(FacesMessage.SEVERITY_INFO, getMessage(OPERACION_EXITO_KEY), getMessage(OPERACION_EXITO_KEY));
    }

    public static FacesMessage errorFatalMessage() {
        return new FacesMessage(FacesMessage.SEVERITY_INFO, getMessage(ERROR_FATAL), getMessage(ERROR_FATAL));
    }

    public static String getMessage(String msgKey) {
        ResourceBundle msg = ResourceBundle.getBundle(RESOURCE_BUNDLE_SISTEMA);
        return msg.getString(msgKey);
    }

    public static String getMessage(String resourceBundle, String msgKey) {
        ResourceBundle msg = ResourceBundle.getBundle(RESOURCE_BUNDLE + resourceBundle);
        return msg.getString(msgKey);
    }
}
