package ar.com.ak.util;

import org.apache.commons.lang.builder.*;
import org.hibernate.annotations.NaturalId;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class EntityUtil {

    private static Map<String, List<Field>> cache =
            new Hashtable<String, List<Field>>();

    private EntityUtil() {
    }

    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }

        if (obj2 == null || obj2.getClass() != obj1.getClass()) {
            return false;
        }

        EqualsBuilder builder = new EqualsBuilder();

        for (Field field : getNaturalIdFields(obj1.getClass())) {
            try {
                builder.append(field.get(obj1), field.get(obj2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return builder.isEquals();
    }

    public static int compareTo(Object obj1, Object obj2) {
        CompareToBuilder builder = new CompareToBuilder();

        for (Field field : getNaturalIdFields(obj1.getClass())) {
            try {
                builder.append(field.get(obj1), field.get(obj2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return builder.toComparison();
    }

    public static int hashCode(Object obj) {
        HashCodeBuilder builder = new HashCodeBuilder();

        for (Field field : getNaturalIdFields(obj.getClass())) {
            try {
                builder.append(field.get(obj));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return builder.toHashCode();
    }

    public static String toString(Object obj) {
        ToStringBuilder builder = new ToStringBuilder(obj, ToStringStyle.SIMPLE_STYLE);

        for (Field field : getNaturalIdFields(obj.getClass())) {
            try {
                builder.append(field.getName(), field.get(obj));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return builder.toString();
    }

    public static List<Field> getNaturalIdFields(Class<?> clazz) {
        String name = clazz.getName();

        if (!cache.containsKey(name)) {
            List<Field> fields = new ArrayList<Field>();

            for (Field field : clazz.getDeclaredFields()) {
                NaturalId naturalId = field.getAnnotation(NaturalId.class);
                if (naturalId != null) {
                    field.setAccessible(true);
                    fields.add(field);
                }
            }

            cache.put(name, fields);
        }

        return cache.get(name);
    }

    public static List<Field> getSortByFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();

        for (Field field : clazz.getDeclaredFields()) {
            SortBy sortBy = field.getAnnotation(SortBy.class);
            if (sortBy != null) {
                field.setAccessible(true);
                fields.add(field);
            }
        }

        return fields;
    }

    @Target(FIELD)
    @Retention(RUNTIME)
    public @interface SortBy {
        String order() default "asc";
    }

}
