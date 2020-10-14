package ovchip.Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ovchip.dao.OVChipkaartDAO;
import ovchip.domein.Adres;
import ovchip.domein.OVChipkaart;
import ovchip.domein.Product;
import ovchip.domein.Reiziger;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO<OVChipkaart, String> {

    private Session currentSession;

    private Transaction currentTransaction;

    public OVChipkaartDAOHibernate() {
    }

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
    public boolean save(OVChipkaart ovChipkaart) {
        getCurrentSession().save(ovChipkaart);
        return true;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        getCurrentSession().update(ovChipkaart);
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        getCurrentSession().delete(ovChipkaart);
        return true;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger){
        List<OVChipkaart> ovChipkaart = (List<OVChipkaart>) getCurrentSession().get(Reiziger.class, (Serializable) reiziger);
        return ovChipkaart;
    }

    @Override
    public List<OVChipkaart> findAll() {
        List<OVChipkaart> ovChipkaarts = (List<OVChipkaart>) getCurrentSession().createQuery("from ov_chipkaart ").list();
        return ovChipkaarts;
    }

}
