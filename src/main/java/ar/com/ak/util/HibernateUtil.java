package ar.com.ak.util;

import ar.com.ak.model.AuditableEntity;
import ar.com.ak.model.IdentifiableEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.ImprovedNamingStrategy;

import java.util.List;

public class HibernateUtil {

    public static final String DEFAULT_DB_DRIVER_CLASS = "org.postgresql.Driver";
    public static final String DEFAULT_DB_PROVIDER_CLASS = "org.hibernate.connection.C3P0ConnectionProvider";
    public static final String DEFAULT_DB_DIALECT = "ar.com.ak.util.CustomPostgresDialect";
    public static final String DEFAULT_SCHEMA = "public";
    public static final String DEFAULT_SESSION_CONTEXT_CLASS = "thread";
    public static final String ANNOTATED_CLASSES_PACKAGE_NAME = "ar.com.ak.model";

    private static final AnnotationConfiguration configuration = configure();
    private static final SessionFactory sessionFactory = configuration.buildSessionFactory();

    private static AnnotationConfiguration configure() {
        AnnotationConfiguration configuration = new AnnotationConfiguration()
                .setNamingStrategy(ImprovedNamingStrategy.INSTANCE)
                .setProperty(Environment.DIALECT, DEFAULT_DB_DIALECT)
                .setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, DEFAULT_SESSION_CONTEXT_CLASS)
                .setProperty(Environment.CONNECTION_PROVIDER, DEFAULT_DB_PROVIDER_CLASS)
                .setProperty(Environment.DRIVER, DEFAULT_DB_DRIVER_CLASS)
                .setProperty(Environment.USE_IDENTIFIER_ROLLBACK, Boolean.TRUE.toString())
                .setInterceptor(CustomInterceptor.INSTANCE)
                .addAnnotatedClass(IdentifiableEntity.class)
                .addAnnotatedClass(AuditableEntity.class);

        List<Class<?>> annotatedClasses = Util.getClasses(ANNOTATED_CLASSES_PACKAGE_NAME);

        for (Class<?> clazz : annotatedClasses) {
            configuration.addAnnotatedClass(clazz);
        }

        return configuration.configure();
    }

    public static AnnotationConfiguration getConfiguration() {
        return configuration;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

    public static void beginTransaction() {
        getSession().beginTransaction();
    }

    public static void endTransaction() {
        Transaction transaction = getSession().getTransaction();

        try {
            if (transaction.isActive()) {
                transaction.commit();
            }

        } catch (Throwable ex) {
            try {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            } catch (Throwable rbEx) {
            }
            throw new RuntimeException(ex);
        }
    }

    public static void abortTransaction() {
        Transaction transaction = getSession().getTransaction();

        if (transaction.isActive()) {
            transaction.rollback();
        }

    }
}
