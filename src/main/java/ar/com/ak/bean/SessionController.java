package ar.com.ak.bean;

import ar.com.ak.model.Usuario;
import ar.com.ak.repo.Repositories;
import ar.com.ak.util.AuthenticationException;
import ar.com.ak.util.FacesUtil;
import org.apache.log4j.Logger;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

import java.io.Serializable;

public class SessionController implements Serializable {

    public static final String VALUE_EXPRESION = "#{sessionController}";
    public static final String LOGOUT_MESSAGE = "Fin de sesión";
    public static final String USER_SESSION_KEY = "STOCK_USUARIO";
    private static final long serialVersionUID = 7816465207892152305L;
    private transient Logger log = Logger.getLogger(SessionController.class);

    @NotEmpty
    private String currentPass;

    @NotEmpty
    @Length(min = 4, max = 30)
    private String newPass1;

    @NotEmpty
    private String newPass2;

    public SessionController() {
    }

    public static SessionController getCurrentInstance() {
        return FacesUtil.getEvaluatedExpression(VALUE_EXPRESION, SessionController.class);
    }

    public static Usuario getUsuarioEnSession() {
        return (Usuario) FacesUtil.getSessionValue(USER_SESSION_KEY);
    }

    public static void setUsuarioEnSession(Usuario usuario) {
        FacesUtil.setSessionValue(USER_SESSION_KEY, usuario);
    }

    public void logout() {
        log.info(LOGOUT_MESSAGE + ": " + getNombreUsuario());
        ApplicationController.getCurrentInstance().getUsuarios().remove(getNombreUsuario());
        FacesUtil.redirect(LoginController.LOGIN_PAGE);
    }

    public String getNombreUsuario() {
        return getUsuarioEnSession().getNombre();
    }

    public String getContraseñaUsuario() {
        return getUsuarioEnSession().getContraseña();
    }

    public Usuario getUsuario() {
        Usuario usuario = getUsuarioEnSession();

        if (usuario == null) {
            throw new AuthenticationException();
        }

        return usuario;
    }

    public void changePasword() {
        Usuario current = getUsuario();
        current.setContraseña(newPass1);
        Repositories.Usuario().saveOrUpdate(current);

        resetChangePassword();
    }

    public void resetChangePassword() {
        currentPass = null;
        newPass1 = null;
        newPass2 = null;
    }

    //GETTERS & SETTERS
    public String getCurrentPass() {
        return currentPass;
    }

    public void setCurrentPass(String currentPass) {
        this.currentPass = currentPass;
    }

    public String getNewPass1() {
        return newPass1;
    }

    public void setNewPass1(String newPass1) {
        this.newPass1 = newPass1;
    }

    public String getNewPass2() {
        return newPass2;
    }

    public void setNewPass2(String newPass2) {
        this.newPass2 = newPass2;
    }
}
