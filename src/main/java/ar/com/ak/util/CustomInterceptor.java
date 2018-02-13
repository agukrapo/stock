package ar.com.ak.util;

import ar.com.ak.bean.SessionController;
import ar.com.ak.model.AuditableEntity;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Date;

public class CustomInterceptor extends EmptyInterceptor {

    public static final CustomInterceptor INSTANCE = new CustomInterceptor();
    public static final String CREADOR_PROPERTY_NAME = "creador";
    public static final String CREADO_PROPERTY_NAME = "creado";
    public static final String ACTUALIZADOR_PROPERTY_NAME = "actualizador";
    public static final String ACTUALIZADO_PROPERTY_NAME = "actualizado";
    private static final long serialVersionUID = -8946593115272224162L;

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state,
                          String[] propertyNames, Type[] types) {

        Boolean updated = Boolean.FALSE;

        if (entity instanceof AuditableEntity) {
            AuditableEntity auditable = (AuditableEntity) entity;

            for (int i = 0; i < propertyNames.length; i++) {
                String propertyName = propertyNames[i];

                if (propertyName.equals(CREADOR_PROPERTY_NAME)) {
                    auditable.setCreador(SessionController.getUsuarioEnSession());
                    state[i] = SessionController.getUsuarioEnSession();
                    updated = Boolean.TRUE;

                } else if (propertyName.equals(CREADO_PROPERTY_NAME)) {
                    Date now = new Date();

                    auditable.setCreado(now);
                    state[i] = now;
                    updated = Boolean.TRUE;
                }
            }
        }

        return updated;
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id,
                                Object[] currentState, Object[] previousState,
                                String[] propertyNames, Type[] types) {

        Boolean updated = Boolean.FALSE;

        if (entity instanceof AuditableEntity) {
            AuditableEntity auditable = (AuditableEntity) entity;

            for (int i = 0; i < propertyNames.length; i++) {
                String propertyName = propertyNames[i];

                if (propertyName.equals(ACTUALIZADOR_PROPERTY_NAME)) {
                    auditable.setActualizador(SessionController.getUsuarioEnSession());
                    currentState[i] = SessionController.getUsuarioEnSession();
                    updated = Boolean.TRUE;

                } else if (propertyName.equals(ACTUALIZADO_PROPERTY_NAME)) {
                    Date now = new Date();

                    auditable.setActualizado(now);
                    currentState[i] = now;
                    updated = Boolean.TRUE;
                }
            }
        }

        return updated;
    }
}
