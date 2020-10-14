package ovchip.Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ovchip.dao.AdresDAO;
import ovchip.dao.ReizigerDAO;
import ovchip.domein.Adres;
import ovchip.domein.OVChipkaart;
import ovchip.domein.Reiziger;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO<Reiziger, String> {
    private Session currentSession;
    private Transaction currentTransaction;

    public ReizigerDAOHibernate(){};

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
    public boolean save(Reiziger reiziger) {
        getCurrentSession().save(reiziger);
        return true;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        getCurrentSession().update(reiziger);
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        getCurrentSession().delete(reiziger);
        return true;
    }

    @Override
    public Reiziger findById(int id){
        Reiziger reiziger = (Reiziger) getCurrentSession().get(Reiziger.class, (Serializable) id);
        return reiziger;
    }
    @Override
    public List<Reiziger> findByGbdatum(String datum){
        List<Reiziger> reizigers = (List<Reiziger>) getCurrentSession().get(Reiziger.class, (Serializable) datum);
        return reizigers;
    }

    @Override
    public List<Reiziger> findAll(){
        List<Reiziger> reizigers = (List<Reiziger>) getCurrentSession().createQuery("from reiziger").list();
        return reizigers;
    }


}
