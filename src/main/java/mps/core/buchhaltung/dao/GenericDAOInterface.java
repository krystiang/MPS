package mps.core.buchhaltung.dao;

import java.io.*;
import java.util.*;
import org.hibernate.Query;
 
public interface GenericDAOInterface<T, ID extends Serializable> {
 
    public long save(T entity);
 
    public void merge(T entity);
 
    public void delete(T entity);
 
    public List<T> findMany(Query query);
 
    public T findOne(Query query);
 
    public List<T> findAll(Class<T> clazz);
 
    public T findByID(Class<T> clazz, Long id);
}