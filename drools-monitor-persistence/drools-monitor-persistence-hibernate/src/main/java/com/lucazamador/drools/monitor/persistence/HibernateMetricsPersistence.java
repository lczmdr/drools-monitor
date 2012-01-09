package com.lucazamador.drools.monitor.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitor.persistence.api.MetricsPersistence;

/**
 * Hibernate persistence
 * 
 * @author Lucas Amador
 * 
 */
public class HibernateMetricsPersistence implements MetricsPersistence {

    private static final Logger logger = LoggerFactory.getLogger(HibernateMetricsPersistence.class);

    private SessionFactory sessionFactory;

    public HibernateMetricsPersistence() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            logger.error("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public void save(Object object) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(object);
        session.getTransaction().commit();
    }

    public void stop() {
        sessionFactory.close();
    }

}
