package ar.com.ak.bean;

import ar.com.ak.model.Usuario;
import ar.com.ak.util.AppConfParser;
import ar.com.ak.util.FacesUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ApplicationController implements Serializable {

    public static final String VALUE_EXPRESION = "#{applicationController}";
    public static final String FACELETS_DEVELOPMENT = "facelets.DEVELOPMENT";
    public static final String FAVICON_PATH = "/image/favicon.ico";
    public static final String TABLE_ACTIVE_ROW_COLOR = "#FFEBDA";
    public static final String TABLE_ODD_ROW_COLOR = "#F0F0F0";
    public static final String TABLE_EVEN_ROW_COLOR = "#FFFFFF";
    public static final String HIBERNATE_SQL_PACKAGE_NAME = "org.hibernate.SQL";
    public static final String MODEL_PACKAGE_NAME = "ar.com.ak.model";
    private static final long serialVersionUID = -5020633393039452234L;
    private final Map<String, Usuario> usuarios;
    private Boolean development;
    private String tableActiveRowColor = TABLE_ACTIVE_ROW_COLOR;
    private String tableOddRowColor = TABLE_ODD_ROW_COLOR;
    private String tableEvenRowColor = TABLE_EVEN_ROW_COLOR;
    private String appVersion;

    public ApplicationController() {
        appVersion = AppConfParser.parse().getVersion();

        development = Boolean.parseBoolean((String) FacesUtil.getInitParameter(FACELETS_DEVELOPMENT));

        usuarios = new HashMap<String, Usuario>();

        if (development) {
            Logger.getLogger(HIBERNATE_SQL_PACKAGE_NAME).setLevel(Level.DEBUG);
            Logger.getLogger(MODEL_PACKAGE_NAME).setLevel(Level.DEBUG);
        }
    }

    public static ApplicationController getCurrentInstance() {
        return FacesUtil.getEvaluatedExpression(VALUE_EXPRESION,
                ApplicationController.class);
    }

    public void goToLoginController() {
        FacesUtil.redirect(LoginController.LOGIN_PAGE);
    }

    public String getFaviconPath() {
        return FacesUtil.getRequest().getContextPath() + FAVICON_PATH;
    }

    // GETTERS & SETTERS
    public Boolean getDevelopment() {
        return development;
    }

    public void setDevelopment(Boolean development) {
        this.development = development;
    }

    public String getTableActiveRowColor() {
        return tableActiveRowColor;
    }

    public void setTableActiveRowColor(String tableActiveRowColor) {
        this.tableActiveRowColor = tableActiveRowColor;
    }

    public String getTableOddRowColor() {
        return tableOddRowColor;
    }

    public void setTableOddRowColor(String tableOddRowColor) {
        this.tableOddRowColor = tableOddRowColor;
    }

    public String getTableEvenRowColor() {
        return tableEvenRowColor;
    }

    public void setTableEvenRowColor(String tableEvenRowColor) {
        this.tableEvenRowColor = tableEvenRowColor;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

}
