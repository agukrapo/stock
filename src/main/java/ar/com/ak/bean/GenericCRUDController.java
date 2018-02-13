package ar.com.ak.bean;

import ar.com.ak.model.IdentifiableEntity;
import ar.com.ak.repo.GenericHibernateDAO;
import ar.com.ak.util.ColumnDefinition;
import ar.com.ak.util.FacesUtil;
import ar.com.ak.util.FieldDefinition;
import ar.com.ak.util.MessageFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GenericCRUDController<T extends IdentifiableEntity> implements Serializable {

    private static final long serialVersionUID = 4066129173554515340L;

    private Class<T> crudClass;

    private transient Logger logger;

    private transient GenericHibernateDAO<T> crudDAO;

    private List<T> itemList;
    private T currentItem;

    private List<ColumnDefinition> columnsDefinition = new ArrayList<ColumnDefinition>();
    private Integer rows = 16;

    private List<FieldDefinition> fieldsDefinition = new ArrayList<FieldDefinition>();

    private Map<String, Criterion> listParameters = new HashMap<String, Criterion>();

    public void eliminar() {
        crudDAO.delete(currentItem);
        reiniciar();
        FacesUtil.addMessage(MessageFactory.operacionExitoMessage());
    }

    public void agregar() {
        try {
            currentItem = crudClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void editar() {
    }

    public void aceptar() {
        T tmp = crudClass.cast(currentItem.clone());
        try {
            crudDAO.saveOrUpdate(currentItem);
        } catch (RuntimeException re) {
            currentItem = tmp;
            throw re;
        }

        reiniciar();
        FacesUtil.addMessage(MessageFactory.operacionExitoMessage());
    }

    public void cancelar() {
        reiniciar();
    }

    protected void reiniciar() {
        itemList = crudDAO.listByCriteriaCollection(listParameters.values());
        currentItem = null;
    }

    // PARAMETROS DEL LISTADO

    public void addListEqParameter(String propertyName, Object value) {
        listParameters.put(propertyName, Restrictions.eq(propertyName, value));
    }

    public void removeListEqParameter(String propertyName) {
        listParameters.remove(propertyName);
    }

    public void addListBetweenParameter(String propertyName, Object lo, Object hi) {
        listParameters.put(propertyName, Restrictions.between(propertyName, lo, hi));
    }

    public void removeBetweenParameter(String propertyName) {
        listParameters.remove(propertyName);
    }

    //MISC

    public String getCrudClassName() {
        return crudClass.getSimpleName();
    }

    public void addColumnDefinition(String property, String type, Integer width) {
        columnsDefinition.add(new ColumnDefinition(property, type, width));
    }

    public void addFieldDefinition(String property, String type) {
        fieldsDefinition.add(new FieldDefinition(
                StringUtils.uncapitalize(crudClass.getSimpleName()) + StringUtils.capitalize(property),
                property, type));
    }

    // GETTERS & SETTERS

    public List<T> getItemList() {
        return itemList;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
    }

    public T getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(T currentItem) {
        this.currentItem = currentItem;
    }


    public Class<T> getCrudClass() {
        return crudClass;
    }


    public void setCrudClass(Class<T> crudClass) {
        this.crudClass = crudClass;
        logger = Logger.getLogger(crudClass);
    }


    public GenericHibernateDAO<T> getCrudDAO() {
        return crudDAO;
    }


    public void setCrudDAO(GenericHibernateDAO<T> crudDAO) {
        this.crudDAO = crudDAO;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public List<ColumnDefinition> getColumnsDefinition() {
        return columnsDefinition;
    }

    public List<FieldDefinition> getFieldsDefinition() {
        return fieldsDefinition;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
