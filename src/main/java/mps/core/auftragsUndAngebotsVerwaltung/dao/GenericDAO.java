package mps.core.auftragsUndAngebotsVerwaltung.dao;

import mps.core.fertigung.dao.HibernateUtil;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
 

public abstract class GenericDAO<T, ID extends Serializable> implements GenericDAOInterface<T, ID> {
 
    protected Session getSession() {
        return HibernateUtil.getSession();
    }
 
    public long save(T entity) {
        Session hibernateSession = this.getSession();
      return (long) hibernateSession.save(entity);
    }
 
    public void merge(T entity) {
        Session hibernateSession = this.getSession();
        hibernateSession.merge(entity);
    }
 
    public void delete(T entity) {
        Session hibernateSession = this.getSession();
        hibernateSession.delete(entity);
    }
 
    @SuppressWarnings("unchecked")
	public List<T> findMany(Query query) {
        List<T> result;
        result = (List<T>) query.list();
        return result;
    }
 
    @SuppressWarnings("unchecked")
	public T findOne(Query query) {
        T foundUnique;
        foundUnique = (T) query.uniqueResult();
        return foundUnique;
    }
 
   
	@SuppressWarnings("unchecked")
	public T findByID(Class<T> clazz, Long id) {
        Session hibernateSession = this.getSession();
        T found = null;
        found = (T) hibernateSession.get(clazz, id);
        return found;
    }
 
    @SuppressWarnings("unchecked")
	public List<T> findAll(Class<T> clazz) {
        Session hibernateSession = this.getSession();
        List<T> all = null;
        Query query = hibernateSession.createQuery("from " + clazz.getName());
        all = query.list();
        return all;
    }
}