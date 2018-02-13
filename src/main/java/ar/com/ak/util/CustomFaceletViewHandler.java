package ar.com.ak.util;

import ar.com.ak.bean.ApplicationController;
import com.sun.facelets.FaceletViewHandler;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;
import java.io.IOException;

public class CustomFaceletViewHandler extends FaceletViewHandler {

    public CustomFaceletViewHandler(ViewHandler parent) {
        super(parent);
    }

    @Override
    protected void handleRenderException(FacesContext context, Exception exception)
            throws IOException, ELException, FacesException {

        if (ApplicationController.getCurrentInstance().getDevelopment()) {
            super.handleRenderException(context, exception);
        } else {
            ThrowableHandler.handleThrowable(context, exception);
        }
    }
}
