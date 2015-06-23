package com.jiaoya.common.dao;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


/**
 * 一个公共的Hibernate通用dao实现类<br>
 * 数据库访问层,每一个实现类都应该来继承该类<br>
 * 不应该重写里面的方法,需要相应的方法,直接到数据访问层每个类对应的接口中添加
 */
public  class BaseDaoImpl<T extends Serializable, PK extends Serializable>
        extends HibernateDaoSupport implements BaseDao<T, PK> {

    /**
     * 为T对应的实例类型
     */
    private Class<T> entityClass;

    @Autowired
    public void setSessionFactoryOverride(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    /**
     * 获取T实例类的类型
     */
    public BaseDaoImpl() {
        Type t =  this.getClass().getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            this.entityClass = (Class<T>) ((ParameterizedType) t).getActualTypeArguments()[0];
        }
    }

    public T get(PK id) {
        return (T) this.getHibernateTemplate().get(this.entityClass, id);
    }

    public T get(PK id, LockMode lock) {
        T entity = (T) this.getHibernateTemplate().get(this.entityClass, id, lock);
        if (entity != null) {
            this.flush();// 如果实体不为null,立即刷新,否则锁不会生效
        }
        return entity;
    }


    public Integer getRowCount(DetachedCriteria criteria) {
        criteria.setProjection(Projections.rowCount());
        return (Integer) this.findByCriteria(criteria, 0, 1).get(0);
    }

    public T load(PK id) {
        return (T) this.getHibernateTemplate().load(this.entityClass, id);
    }

    public T load(PK id, LockMode lock) {
        T entity = (T) this.getHibernateTemplate().load(this.entityClass, id, lock);
        if (entity != null) {
            this.flush();// 如果实体不为null,立即刷新,否则锁不会生效
        }
        return entity;
    }

    public List<T> loadAll() {
        return this.getHibernateTemplate().loadAll(entityClass);
    }

    public List<T> find(String hql) {
        return (List<T>) this.getHibernateTemplate().find(hql);
    }

    public List<T> find(String hql, Object... values) {
        return (List<T>)this.getHibernateTemplate().find(hql, values);
    }

    public List<T> findByNamedQuery(String queryName, Object... values) {
        return (List<T>)this.getHibernateTemplate().findByNamedQuery(queryName, values);
    }

    public List<T> findByNamedQuery(String queryName) {
        return (List<T>)this.getHibernateTemplate().findByNamedQuery(queryName);
    }

    public List<T> findByNamedQueryAndNamedParam(String queryName,
                                                 Map<String, Object> params) {
        return (List<T>)this.getHibernateTemplate().findByNamedQueryAndNamedParam(
                queryName, (String[]) params.keySet().toArray(),
                params.values().toArray());
    }

    public List<T> findByNamedParam(String queryName, Map<String, Object> params) {
        return (List<T>)this.getHibernateTemplate()
                .findByNamedParam(queryName,
                        (String[]) params.keySet().toArray(),
                        params.values().toArray());
    }

    public List<T> findByCriteria(DetachedCriteria criteria) {
        return (List<T>)this.getHibernateTemplate().findByCriteria(criteria);
    }

    public List<T> findByCriteria(DetachedCriteria criteria,
                                  Integer firstResult, Integer maxResults) {
        return (List<T>)this.getHibernateTemplate().findByCriteria(criteria,
                firstResult, maxResults);
    }

    public void save(T entity) throws HibernateException {
        this.getHibernateTemplate().save(entity);
    }

    public void saveOrUpdate(T entity) throws HibernateException {
        this.getHibernateTemplate().saveOrUpdate(entity);
    }

    public void delete(T entity) throws HibernateException {
        this.getHibernateTemplate().delete(entity);
    }

    public void delete(T entity, LockMode lock) throws HibernateException {
        this.getHibernateTemplate().delete(entity, lock);
        this.flush();// 如果实体不为null,立即刷新,否则锁不会生效
    }

    public void delete(Collection<T> entitys) throws HibernateException {
        this.getHibernateTemplate().deleteAll(entitys);
    }

    public void update(T entity) throws HibernateException {
        this.getHibernateTemplate().update(entity);
    }

    public void update(T entity, LockMode lock) throws HibernateException {
        this.getHibernateTemplate().update(entity, lock);
        this.flush();// 如果实体不为null,立即刷新,否则锁不会生效
    }

    public Integer bulkUpdate(String hql) {
        return this.getHibernateTemplate().bulkUpdate(hql);
    }

    public Integer bulkUpdate(String hql, Object... values) {
        return this.getHibernateTemplate().bulkUpdate(hql, values);
    }

    public void flush() throws HibernateException {
        this.getHibernateTemplate().flush();
    }

    public void lock(T entity, LockMode lock) throws HibernateException {
        this.getHibernateTemplate().lock(entity, lock);
    }

    public DetachedCriteria createDetachedCriteria() {
        return DetachedCriteria.forClass(this.entityClass);
    }

    public DetachedCriteria createDetachedCriteria(
            Class<? extends Serializable> c) {
        return DetachedCriteria.forClass(c);
    }

    public Criteria createCriteria() {
        return this.createDetachedCriteria().getExecutableCriteria(this.currentSession());
    }

}
