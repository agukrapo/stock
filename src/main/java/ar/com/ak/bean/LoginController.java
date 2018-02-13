package ar.com.ak.bean;

import ar.com.ak.model.Usuario;
import ar.com.ak.repo.Repositories;
import ar.com.ak.util.FacesUtil;
import ar.com.ak.util.MessageFactory;
import ar.com.ak.util.SchemaExporter;
import org.apache.log4j.Logger;

import java.io.Serializable;

public class LoginController implements Serializable {

    public static final String VALUE_EXPRESION = "#{loginController}";
    public static final String LOGIN_PAGE = "/stock/pages/login.faces";
    public static final String LOGIN_MESSAGE = "Inicio de sesión";
    public static final String START_FROM_OUTCOME = "start";
    public static final String MSG_ERROR_LOGIN_KEY = "error_loginController";
    private static final long serialVersionUID = -7473785840796597034L;
    private String nombre;
    private String contraseña;

    private transient Logger log = Logger.getLogger(LoginController.class);

    public LoginController() {
        FacesUtil.removeSession();
        autoSetupForDevelopment();
    }

    public static LoginController getCurrentInstance() {
        return FacesUtil.getEvaluatedExpression(VALUE_EXPRESION, LoginController.class);
    }

    public String login() {
        String result = null;

        Usuario usuario = Repositories.Usuario().checkLogin(nombre, contraseña);

        if (usuario == null) {
            FacesUtil.addMessage(MessageFactory.error(MessageFactory.getMessage(MSG_ERROR_LOGIN_KEY)));
        } else {
            SessionController.setUsuarioEnSession(usuario);
            ApplicationController.getCurrentInstance().getUsuarios().put(usuario.getNombre(), usuario);
            result = START_FROM_OUTCOME;
            log.info(LOGIN_MESSAGE + ": " + usuario.getNombre());
        }

        return result;
    }

    private void autoSetupForDevelopment() {
        if (ApplicationController.getCurrentInstance().getDevelopment()) {
            if (!SchemaExporter.isValid()) {
                SchemaExporter.create(false, true);
                SchemaExporter.createDevelopmentSuperuser();
            }
        }
    }

    // GETTERS & SETTERS
    public String getNombre() {
        return null;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return null;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
