package ovchip.Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ovchip.dao.AdresDAO;
import ovchip.dao.ReizigerDAO;
import ovchip.domein.Adres;
import ovchip.domein.Reiziger;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO<Adres, String> {
    private Session currentSession;
    private Transaction currentTransaction;

    public AdresDAOHibernate(){};

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }


    @Override
    public boolean save(Adres adres) {
        getCurrentSession().save(adres);
        return true;
    }

    @Override
    public boolean update(Adres adres) {
        getCurrentSession().update(adres);
        return true;
    }

    @Override
    public boolean delete(Adres adres) {
        getCurrentSession().delete(adres);
        return true;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger){
        Adres adres = (Adres) getCurrentSession().get(Adres.class, (Serializable) reiziger);
        return adres;
    }

    @Override
    public List<Adres> findAll() {
        List<Adres> adressen = (List<Adres>) getCurrentSession().createQuery("from adres").list();
        return adressen;
    }


}
