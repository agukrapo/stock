package ar.com.ak.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import java.util.Iterator;

public class CustomPhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1334851959309802292L;

    private static final String FOCUS_CLIENT_ID_KEY = "focusClientId";
    private static final String NO_CLIENT_ID_VALUE = "none";

    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    public void beforePhase(PhaseEvent event) {

        String focusClientId = null;

        Iterator<String> clientIdsWithMessages = FacesUtil.getClientIdsWithMessages();
        if (clientIdsWithMessages.hasNext()) {
            focusClientId = clientIdsWithMessages.next();

            if (focusClientId == null) {
                focusClientId = NO_CLIENT_ID_VALUE;
            }

        } else {
            focusClientId = NO_CLIENT_ID_VALUE;
        }

        FacesUtil.setRequestValue(FOCUS_CLIENT_ID_KEY, focusClientId);
    }

    public void afterPhase(PhaseEvent event) {

    }
}
