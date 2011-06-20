package com.lucazamador.drools.monitoring.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucazamador.drools.monitoring.persistence.api.MetricsPersistence;

/**
 * Hibernate persistence
 * 
 * @author Lucas Amador
 * 
 */
public class HibernateMetricsPersistence implements MetricsPersistence {

    private static final Logger logger = LoggerFactory.getLogger(HibernateMetricsPersistence.class);

//    private SessionFactory sessionFactory;

    public HibernateMetricsPersistence() {
//        try {
//            Configuration configuration = new Configuration();
//            sessionFactory = configuration.buildSessionFactory();
//        } catch (Throwable e) {
//            logger.error("Initial SessionFactory creation failed." + e);
//            throw new ExceptionInInitializerError(e);
//        }
    }

    public void save(Object object) {
        logger.info("save : " + object);
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        session.persist(object);
//        session.getTransaction().commit();
    }

    public void stop() {
//        sessionFactory.close();
    }

}
