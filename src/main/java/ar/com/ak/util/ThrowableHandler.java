package ar.com.ak.util;

import ar.com.ak.bean.LoginController;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.postgresql.util.PSQLException;

import javax.faces.context.FacesContext;

public class ThrowableHandler {

    public static final String ERROR_PAGE = "/stock/pages/error.faces";
    public static final String AA_ERROR_PAGE = "/stock/pages/aAError.faces";

    public static final String ERROR_GENERICO_KEY = "error_generico";
    public static final String ERROR_CLAVE_FORANEA_KEY = "error_clave_foranea";
    public static final String ERROR_CLAVE_UNICA_KEY = "error_clave_unica";

    private static transient Logger log = Logger.getLogger(ThrowableHandler.class);

    public static void handleThrowable(FacesContext context, Throwable throwable) {

        Boolean fatal = Boolean.FALSE;
        Throwable cause = ExceptionUtils.getRootCause(throwable);

        if (cause instanceof PSQLException) {
            PSQLException e = (PSQLException) cause;

            if (e.getSQLState().equals("23503")) {
                log.error(cause);
                FacesUtil.addMessage(MessageFactory.error(MessageFactory.getMessage(ERROR_CLAVE_FORANEA_KEY)));

            } else if (e.getSQLState().equals("23505")) {
                log.error(cause);
                FacesUtil.addMessage(MessageFactory.error(MessageFactory.getMessage(ERROR_CLAVE_UNICA_KEY)));

            } else {
                fatal = Boolean.TRUE;
            }

        } else if (cause instanceof AuthenticationException) {
            log.error(cause);
            FacesUtil.redirect(LoginController.LOGIN_PAGE);

        } else {
            fatal = Boolean.TRUE;
        }

        if (fatal) {
            Util.logFatalThrowable(throwable, log);
            FacesUtil.redirect(ERROR_PAGE);
        }
    }


}
