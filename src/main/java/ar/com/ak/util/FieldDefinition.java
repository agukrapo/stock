package ar.com.ak.util;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class FieldDefinition implements Serializable {

    public static final String STRING_TYPE = "string";
    public static final String INTEGER_TYPE = "integer";
    public static final String CURRENCY_TYPE = "currency";
    public static final String BOOLEAN_TYPE = "boolean";
    public static final String PERCENTAGE_TYPE = "percentage";
    public static final String SELECTONE_TYPE = "selectOne";
    public static final String TEXTAREA_TYPE = "textArea";
    private static final long serialVersionUID = 6081751284850513975L;
    private String id;
    private String property;
    private String type;

    public FieldDefinition() {
    }

    public FieldDefinition(String id, String property, String type) {
        this.id = id;
        this.property = property;
        this.type = type;
    }

    private String getDefaultId() {
        return property + StringUtils.capitalize(type);
    }

    // GETTERS & SETTERS

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id != null ? id : getDefaultId();
    }

    public void setId(String id) {
        this.id = id;
    }
}
