package ar.com.ak.util;

import com.sun.faces.application.ActionListenerImpl;

import javax.faces.event.ActionEvent;

public class CustomActionListener extends ActionListenerImpl {

    @Override
    public void processAction(ActionEvent event) {
        try {
            super.processAction(event);
        } catch (Throwable throwable) {
            ThrowableHandler.handleThrowable(FacesUtil.getContext(), throwable);
        }
    }
}
