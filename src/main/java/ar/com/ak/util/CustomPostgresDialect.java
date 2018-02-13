package ar.com.ak.util;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.type.Type;

import java.util.Properties;

public class CustomPostgresDialect extends PostgreSQLDialect {

    public static final String ID_SEQUENCE_POSTFIJO = "_id_seq";
    public static final String FOREIGN_KEY_CONSTRAINT_POSTFIJO = "_fkey";

    @Override
    public String getAddForeignKeyConstraintString(String constraintName,
                                                   String[] foreignKey, String referencedTable, String[] primaryKey,
                                                   boolean referencesPrimaryKey) {
        return super.getAddForeignKeyConstraintString(
                getForeignKeyConstraintName(foreignKey, referencedTable),
                foreignKey, referencedTable, primaryKey, referencesPrimaryKey);
    }

    private String getForeignKeyConstraintName(String[] foreignKey, String referencedTable) {
        StringBuffer result = new StringBuffer();

        if (referencedTable.indexOf(".") != -1) {
            result.append(referencedTable.substring(referencedTable.lastIndexOf(".") + 1));
        } else {
            result.append(referencedTable);
        }

        for (String s : foreignKey) {
            result.append("_" + s);
        }

        result.append(FOREIGN_KEY_CONSTRAINT_POSTFIJO);

        return result.toString();
    }

    @Override
    public Class<?> getNativeIdentifierGeneratorClass() {
        return TableNameSequenceGenerator.class;
    }

    public static class TableNameSequenceGenerator extends SequenceGenerator {

        @Override
        public void configure(final Type type, final Properties params, final Dialect dialect) {

            if (params.getProperty(SEQUENCE) == null || params.getProperty(SEQUENCE).length() == 0) {
                String tableName = params.getProperty(PersistentIdentifierGenerator.TABLE);

                if (tableName != null) {
                    params.setProperty(SEQUENCE, tableName + ID_SEQUENCE_POSTFIJO);
                }
            }
            super.configure(type, params, dialect);
        }
    }
}
