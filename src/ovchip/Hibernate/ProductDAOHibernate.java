package ovchip.Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ovchip.dao.AdresDAO;
import ovchip.dao.ProductDAO;
import ovchip.domein.Adres;
import ovchip.domein.OVChipkaart;
import ovchip.domein.Product;
import ovchip.domein.Reiziger;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO<Product, String> {
    private Session currentSession;
    private Transaction currentTransaction;

    public ProductDAOHibernate(){};

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
    public boolean save(Product product) {
        getCurrentSession().save(product);
        return true;
    }

    @Override
    public boolean update(Product product) {
        getCurrentSession().update(product);
        return true;
    }

    @Override
    public boolean delete(Product product) {
        getCurrentSession().delete(product);
        return true;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart){
        List<Product> products = (List<Product>) getCurrentSession().get(Reiziger.class, (Serializable) ovChipkaart);
        return products;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = (List<Product>) getCurrentSession().createQuery("from product").list();
        return products;
    }
}
