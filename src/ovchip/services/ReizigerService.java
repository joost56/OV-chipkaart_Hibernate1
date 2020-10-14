package ovchip.services;

import ovchip.Hibernate.ReizigerDAOHibernate;
import ovchip.dao.ReizigerDAO;
import ovchip.domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public class ReizigerService {
    private static ReizigerDAOHibernate reizigerDAOHibernate;

    public ReizigerService() {
        reizigerDAOHibernate = new ReizigerDAOHibernate();
    }


    public boolean save(Reiziger reiziger) {
        reizigerDAOHibernate.openCurrentSessionwithTransaction();
        reizigerDAOHibernate.save(reiziger);
        reizigerDAOHibernate.closeCurrentSessionwithTransaction();
        return true;
    }

    public boolean update(Reiziger reiziger) {
        reizigerDAOHibernate.openCurrentSessionwithTransaction();
        reizigerDAOHibernate.update(reiziger);
        reizigerDAOHibernate.closeCurrentSessionwithTransaction();
        return true;
    }

    public Reiziger findById(int id){
        reizigerDAOHibernate.openCurrentSession();
        Reiziger reiziger = reizigerDAOHibernate.findById(id);
        reizigerDAOHibernate.closeCurrentSession();
        return reiziger;
    }

    public void delete(int id) {
        reizigerDAOHibernate.openCurrentSessionwithTransaction();
        Reiziger reiziger1 = reizigerDAOHibernate.findById(id);
        reizigerDAOHibernate.delete(reiziger1);
        reizigerDAOHibernate.closeCurrentSessionwithTransaction();
    }

    public List<Reiziger> findByGbdatum(String datum) {
        reizigerDAOHibernate.openCurrentSession();
        List<Reiziger> books = reizigerDAOHibernate.findByGbdatum(datum);
        reizigerDAOHibernate.closeCurrentSession();
        return books;
    }

    public List<Reiziger> findAll() {
        reizigerDAOHibernate.openCurrentSession();
        List<Reiziger> books = reizigerDAOHibernate.findAll();
        reizigerDAOHibernate.closeCurrentSession();
        return books;
    }
}
