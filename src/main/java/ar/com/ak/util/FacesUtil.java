package ar.com.ak.util;

import org.apache.commons.io.IOUtils;

import javax.faces.FactoryFinder;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class FacesUtil {

    public static final String SESSION_KEY_PREFIJO = "GD_STOCK-";
    //	public static final String RESOURCE_FILE_PATH = "WEB-INF/classes/";
    public static final String RESOURCE_FILE_PATH = "/";

    public static void removeSession() {
        try {
            ExternalContext externaContext = FacesContext
                    .getCurrentInstance().getExternalContext();

            HttpSession session = (HttpSession) externaContext.getSession(false);

            if (session != null) {
                session.invalidate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void renderNavigation(String fromOutcome) {

        FacesContext context = getContext();

        NavigationHandler navigation = context.getApplication().getNavigationHandler();
        navigation.handleNavigation(context, null, fromOutcome);

        LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder
                .getFactory(FactoryFinder.LIFECYCLE_FACTORY);

        Lifecycle lifecycle = lifecycleFactory.getLifecycle(getLifecycleId());
        lifecycle.render(context);

        context.responseComplete();
    }

    private static String getLifecycleId() {
        FacesContext context = getContext();

        String lifecycleId = context.getExternalContext().getInitParameter(
                FacesServlet.LIFECYCLE_ID_ATTR);

        return lifecycleId != null ? lifecycleId : LifecycleFactory.DEFAULT_LIFECYCLE;
    }

    public static String getActionAttribute(ActionEvent event, String name) {
        return (String) event.getComponent().getAttributes().get(name);
    }

    //REQUEST
    public static Object getRequestParameterValue(String key) {
        return getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getRequestValue(String key) {
        return getExternalContext().getRequestMap().get(key);
    }

    public static void setRequestValue(String key, Object value) {
        getExternalContext().getRequestMap().put(key, value);
    }

    public static Object removeRequestValue(String key) {
        return getExternalContext().getRequestMap().remove(key);
    }

    // SESSION
    public static Object getSessionValue(String key) {
        return getExternalContext().getSessionMap().get(SESSION_KEY_PREFIJO + key);
    }

    public static void setSessionValue(String key, Object value) {
        getExternalContext().getSessionMap().put(SESSION_KEY_PREFIJO + key, value);
    }

    public static Object removeSessionValue(String key) {
        return getExternalContext().getSessionMap().remove(SESSION_KEY_PREFIJO + key);
    }

    // APLICATION
    public static Object getApplicationValue(String key) {
        return getExternalContext().getApplicationMap().get(key);
    }

    public static void setApplicationValue(String key, Object value) {
        getExternalContext().getApplicationMap().put(key, value);
    }

    public static Object removeApplicationValue(String key) {
        return getExternalContext().getApplicationMap().remove(key);
    }

    public static Object getInitParameter(String key) {
        return getExternalContext().getInitParameterMap().get(key);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getEvaluatedExpression(String valueExpresion, Class<T> clazz) {
        FacesContext context = getContext();

        return (T) context.getApplication().evaluateExpressionGet(context,
                valueExpresion, clazz);
    }

    public static void addMessage(String clientId, FacesMessage message) {
        getContext().addMessage(clientId, message);
    }

    public static void addMessage(FacesMessage message) {
        getContext().addMessage(null, message);
    }

    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) getExternalContext().getResponse();
    }

    public static ServletContext getServletContext() {
        return (ServletContext) getExternalContext().getContext();
    }

    public static ExternalContext getExternalContext() {
        return getContext().getExternalContext();
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) getExternalContext().getRequest();
    }

    public static void redirect(String page) {
        try {
            getExternalContext().redirect(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }

    public static Iterator<String> getClientIdsWithMessages() {
        return getContext().getClientIdsWithMessages();
    }

    public static void downloadFile(Reader reader, String fileName, String contentType) {
        try {
            getContext().responseComplete();

            HttpServletResponse response = getResponse();

            response.setContentType(contentType);
            response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            IOUtils.copy(reader, response.getOutputStream());

            response.getOutputStream().close();

        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static void downloadFile(String fileName, String contentType) {
        try {
            getContext().responseComplete();

            HttpServletResponse response = getResponse();

            response.setContentType(contentType);
            response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            response.getOutputStream().close();

        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static File getResourceFile(String fileName) {
        return new File(getServletContext().getRealPath(RESOURCE_FILE_PATH + fileName));
    }

    public static String getResourceRealPath(String fileName) {
//    	return getServletContext().getRealPath(RESOURCE_FILE_PATH + fileName);
        return FacesUtil.class.getClassLoader().getResource(fileName).toString();
    }

}
