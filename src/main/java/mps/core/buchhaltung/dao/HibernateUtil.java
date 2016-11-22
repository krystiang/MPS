package mps.core.buchhaltung.dao;

import org.hibernate.Session;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.SessionFactory;
 
public class HibernateUtil {
 
private static final SessionFactory sessionFactory;
 
static {
    try {
        // Create the SessionFactory from hibernate.cfg.xml
    	Configuration configuration = new Configuration().configure();
    	ServiceRegistry ssr = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(ssr);
    }
    catch (Throwable ex) {
        // Make sure you log the exception, as it might be swallowed
        System.err.println("Initial SessionFactory creation failed." + ex);
        throw new ExceptionInInitializerError(ex);
    }
}

 
public static SessionFactory getSessionFactory() {
return sessionFactory;
}
 
public static Session beginTransaction() {
Session hibernateSession = HibernateUtil.getSession();
if(!hibernateSession.getTransaction().isActive()){
hibernateSession.beginTransaction();
}
return hibernateSession;
}
 
public static void commitTransaction() {
HibernateUtil.getSession().getTransaction().commit();
}
 
public static void rollbackTransaction() {
HibernateUtil.getSession().getTransaction().rollback();
}
 
public static void closeSession() {
HibernateUtil.getSession().close();
}
 
public static Session getSession() {
Session hibernateSession = sessionFactory.getCurrentSession();
return hibernateSession;
}
}