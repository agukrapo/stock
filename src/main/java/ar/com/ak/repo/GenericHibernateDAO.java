package ar.com.ak.repo;

import ar.com.ak.model.IdentifiableEntity;
import ar.com.ak.util.EntityUtil;
import ar.com.ak.util.EntityUtil.SortBy;
import ar.com.ak.util.HibernateUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class GenericHibernateDAO<T extends IdentifiableEntity> {

    private Class<T> persistentClass;

    private List<Order> sortingOrder;

    private Logger logger;

    @SuppressWarnings("unchecked")
    public GenericHibernateDAO() {
        persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];

        logger = Logger.getLogger(persistentClass);

        sortingOrder = getSortingOrder();
    }

    protected Session getSession() {
        return HibernateUtil.getSession();
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    @SuppressWarnings("unchecked")
    public T loadById(Long id) {
        logger.debug("loadById(" + id + ")");

        return (T) getSession().get(getPersistentClass(), id);
    }

    @SuppressWarnings("unchecked")
    public T loadByNaturalId(T instance) {
        logger.debug("loadByNaturalId(" + instance + ")");

        List<Field> fields = EntityUtil.getNaturalIdFields(persistentClass);

        Criteria criteria = getSession().createCriteria(getPersistentClass());

        for (Field field : fields) {
            try {
                criteria.add(Restrictions.eq(field.getName(), field.get(instance)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return (T) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public T loadByCriteria(Criterion... criterion) {
        logger.debug("loadByCriteria(" + criterion + ")");

        Criteria criteria = getSession().createCriteria(getPersistentClass());

        for (Criterion c : criterion) {
            criteria.add(c);
        }

        T result = (T) criteria.uniqueResult();

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<T> listByCriteria(Criterion... criterion) {
        logger.debug("listByCriteria(" + criterion + ")");

        Criteria criteria = getSession().createCriteria(getPersistentClass());

        for (Criterion c : criterion) {
            criteria.add(c);
        }

        for (Order order : sortingOrder) {
            criteria.addOrder(order);
        }

        List<T> result = criteria.list();

        return result != null ? result : new ArrayList<T>();
    }

    public List<?> listByQuery(String queryString) {
        logger.debug("listByQuery(" + queryString + ")");

        Query query = getSession().createQuery(queryString);

        List<T> result = query.list();

        return result != null ? result : new ArrayList<T>();
    }

    @SuppressWarnings("unchecked")
    public List<T> listByCriteriaCollection(Collection<Criterion> criteriaCollection) {
        logger.debug("listByCriteriaCollection(" + criteriaCollection + ")");

        Criteria criteria = getSession().createCriteria(getPersistentClass());

        for (Criterion c : criteriaCollection) {
            criteria.add(c);
        }

        for (Order order : sortingOrder) {
            criteria.addOrder(order);
        }

        List<T> result = criteria.list();

        return result != null ? result : new ArrayList<T>();
    }

    public List<T> listAll() {
        logger.debug("listAll()");

        return listByCriteria();
    }

    public List<T> listByEqParameters(Map<String, ?> parameters) {
        logger.debug("listByEqParameters(" + parameters + ")");

        return listByCriteria(Restrictions.allEq(parameters));
    }

    @SuppressWarnings("unchecked")
    public List<T> listByExample(T exampleInstance, String... excludeProperty) {
        logger.debug("listByEqParameters(" + exampleInstance + ", " + excludeProperty + ")");

        Criteria criteria = getSession().createCriteria(getPersistentClass());
        Example example = Example.create(exampleInstance);

        for (String exclude : excludeProperty) {
            example.excludeProperty(exclude);
        }

        criteria.add(example);

        for (Order order : sortingOrder) {
            criteria.addOrder(order);
        }

        List<T> result = criteria.list();

        return result != null ? result : new ArrayList<T>();
    }

    public T saveOrUpdate(T entity) {
        logger.debug("saveOrUpdate(" + entity + ")");

        getSession().saveOrUpdate(entity);
        getSession().flush();

        return entity;
    }

    public T saveIfTransientOrLoad(T instance) {
        logger.debug("saveIfTransientOrLoad(" + instance + ")");

        T tmp = this.loadByNaturalId(instance);

        if (tmp == null) {
            this.saveOrUpdate(instance);
        } else {
            try {
                PropertyUtils.copyProperties(instance, tmp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return instance;
    }

    public void delete(T entity) {
        logger.debug("delete(" + entity + ")");

        getSession().delete(entity);
    }

    // TODO
    public void deleteUnused(Class<? extends IdentifiableEntity> entity) {
//		Query query = getSession().createQuery(
//				"delete " + persistentClass.getSimpleName() + " where name = :oldName"
//			);

//		query.setInteger("dia", Util.getDayOfMonth(creado));
//		query.setInteger("mes", Util.getMonth(creado));
//		query.setInteger("anio", Util.getYear(creado));

//		query.executeUpdate();

    }

    public void flush() {
        logger.debug("flush()");

        getSession().flush();
    }

    public void clear() {
        logger.debug("clear()");

        getSession().clear();
    }

    protected List<Order> getSortingOrder() {
        List<Order> result = new ArrayList<Order>();

        for (Field field : EntityUtil.getSortByFields(persistentClass)) {
            SortBy sortBy = field.getAnnotation(SortBy.class);

            if (sortBy.order().equals("asc")) {
                result.add(Order.asc(field.getName()));
            } else if (sortBy.order().equals("desc")) {
                result.add(Order.asc(field.getName()));
            }
        }

        return result;
    }

    protected Criteria getCriteria() {
        return getSession().createCriteria(getPersistentClass());
    }

}
