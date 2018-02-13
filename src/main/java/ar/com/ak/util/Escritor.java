package ar.com.ak.util;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class Escritor {

    private static final String JAVA = ".java";
    private static final String DAO = "DAO";
    private static final String CLASS_TOKEN = "$%&CLASS&%$";
    private static final String LOWER_CASE_CLASS_TOKEN = "$%&LOWER_CASE_CLASS&%$";
    private static final String DAO_PACKAGE_TOKEN = "$%&PACKAGE&%$";
    private static final String MODEL_PACKAGE_TOKEN = "$%&MODEL_PACKAGE&%$";
    private static final String DAO_FILE_CONTENT = "package " + DAO_PACKAGE_TOKEN + ";\n\nimport " + MODEL_PACKAGE_TOKEN + "." + CLASS_TOKEN + ";\n\npublic class " + CLASS_TOKEN + "DAO extends GenericHibernateDAO<" + CLASS_TOKEN + "> {\n}";
    private static final String DAO_FACTORY_CONTENT = "\t\tprivate static " + CLASS_TOKEN + "DAO " + LOWER_CASE_CLASS_TOKEN + "Dao;\n\t\tpublic static " + CLASS_TOKEN + "DAO " + CLASS_TOKEN + "() {\n\t\t\t\treturn (" + LOWER_CASE_CLASS_TOKEN + "Dao == null)? " + LOWER_CASE_CLASS_TOKEN + "Dao = new " + CLASS_TOKEN + "DAO(): " + LOWER_CASE_CLASS_TOKEN + "Dao;\n\t\t}";

    private static final String DAO_PACKAGE = "ar.com.ak.persistence.dao";

    public static void main(String[] args) {

        String daoFolderName = "F:\\desarrollo\\workspace\\stock\\src\\main\\java\\ar\\com\\ak\\persistence\\dao\\";

        String modelPackage = "ar.com.ak.persistence.model";

        makeDAOFiles(modelPackage, daoFolderName);

        System.out.println("Hecho!");
    }

    public static void makeDAOFiles(String modelPackage, String daoFolderName) {
        List<Class<?>> clases = Util.getClasses(modelPackage);

        Integer i = 0;

        for (Class<?> clazz : clases) {
            try {
                String clazzName = clazz.getSimpleName();

                String daoClazzFileName = daoFolderName + clazzName + DAO + JAVA;

                File daoFile = new File(daoClazzFileName);

                if (!daoFile.exists()) {
                    daoFile.createNewFile();

                    FileWriter fileWriter = new FileWriter(daoFile);
                    fileWriter.write(DAO_FILE_CONTENT
                            .replace(CLASS_TOKEN, clazzName)
                            .replace(DAO_PACKAGE_TOKEN, DAO_PACKAGE)
                            .replace(MODEL_PACKAGE_TOKEN, modelPackage));
                    fileWriter.flush();
                    fileWriter.close();
                }

                System.out.println(DAO_FACTORY_CONTENT
                        .replace(CLASS_TOKEN, clazzName)
                        .replace(LOWER_CASE_CLASS_TOKEN, lowerFirstChar(clazzName)));
                System.out.println();

                i++;

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        System.out.println("i=" + i);
    }

    private static String lowerFirstChar(String string) {
        Character firstChar = Character.toLowerCase(string.charAt(0));
        return firstChar.toString() + string.subSequence(1, string.length());
    }

}
