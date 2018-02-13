package ar.com.ak.util;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class CustomFilter implements Filter {

    private static final String NO_PERSISTENCE_PARAM_NAME = "NO_PERSISTENCE";

    private transient Logger log = Logger.getLogger(CustomFilter.class);

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (isPersistenceRequest(request)) {
            try {
                HibernateUtil.beginTransaction();
            } catch (Throwable throwable) {
                Util.logFatalThrowable(throwable, log);
            }

            try {
                chain.doFilter(request, response);
            } catch (Throwable throwable) {
                Util.logFatalThrowable(throwable, log);
            }

            try {
                HibernateUtil.endTransaction();
            } catch (Throwable throwable) {
                Util.logFatalThrowable(throwable, log);
            }

        } else {
            try {
                chain.doFilter(request, response);
            } catch (Throwable throwable) {
                Util.logFatalThrowable(throwable, log);
            }
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    private Boolean isPersistenceRequest(ServletRequest request) {
        Boolean result = null;
        try {
            result = request.getParameter(NO_PERSISTENCE_PARAM_NAME) == null;
        } catch (Throwable throwable) {
        }
        return result;
    }
}