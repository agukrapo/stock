package ar.com.ak.util;

import java.io.Serializable;

public class ColumnDefinition implements Serializable {

    public static final String STRING_TYPE = "string";
    public static final String INTEGER_TYPE = "integer";
    public static final String CURRENCY_TYPE = "currency";
    private static final long serialVersionUID = 104894558770894562L;
    private String property;
    private String type;
    private Integer width;

    public ColumnDefinition() {
    }

    public ColumnDefinition(String property, String type, Integer width) {
        this.property = property;
        this.type = type;
        this.width = width;
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

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}
