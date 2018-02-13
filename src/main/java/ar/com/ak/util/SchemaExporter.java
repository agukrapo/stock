package ar.com.ak.util;

import ar.com.ak.model.Usuario;
import ar.com.ak.repo.Repositories;
import org.hibernate.HibernateException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.hbm2ddl.SchemaValidator;

public class SchemaExporter {

    public static final String HBM2DLL_OUTPUT_FILE = ".\\hibernate.hbm2ddl.sql";
    public static final String HBM2DLL_OUTPUT_FILE_DELIMITER = ";";

    private static AnnotationConfiguration configuration = HibernateUtil.getConfiguration();

    public static void main(String[] args) {
        create(false, true);
        System.out.println("Done!");
    }

    public static void create(boolean script, boolean export) {
        SchemaExport schemaExport = new SchemaExport(configuration)
                .setFormat(true)
                .setDelimiter(HBM2DLL_OUTPUT_FILE_DELIMITER)
                .setOutputFile(HBM2DLL_OUTPUT_FILE);

        schemaExport.create(script, export);
    }

    public static void update(boolean script, boolean update) {
        SchemaUpdate schemaUpdate = new SchemaUpdate(configuration);

        schemaUpdate.setFormat(true);
        schemaUpdate.setDelimiter(HBM2DLL_OUTPUT_FILE_DELIMITER);
        schemaUpdate.setOutputFile(HBM2DLL_OUTPUT_FILE);

        schemaUpdate.execute(script, update);
    }

    public static Boolean isValid() {
        try {
            new SchemaValidator(configuration).validate();
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    public static void createDevelopmentSuperuser() {
        HibernateUtil.beginTransaction();
        Repositories.Usuario().saveOrUpdate(new Usuario("admin", "1234qwer", true));
        HibernateUtil.endTransaction();
    }
}
