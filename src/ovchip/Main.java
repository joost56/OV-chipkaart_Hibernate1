package ovchip;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ovchip.Hibernate.ReizigerDAOHibernate;
import ovchip.Verbinding.DBVerbinding;
import ovchip.dao.ReizigerDAO;
import ovchip.domein.Reiziger;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    Connection conn = DBVerbinding.getConnection();

    ReizigerDAOHibernate reizigerDAOHibernate = new ReizigerDAOHibernate(conn);

    public Main() throws SQLException {
        testFetchAll();
        testDAOHibernate(reizigerDAOHibernate);

    }


    private static void testDAOHibernate(ReizigerDAO rdao) throws SQLException{
        System.out.println("\n---------- Test ReizigerDAO -------------");

        //findall
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test]Uitvoer na het gebruik van de methode ReizigerDAO.findAll()\n");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }

        //save
        String gbdatum = "2002-03-19";
        Reiziger Joost = new Reiziger(6, "J", "", "Buiting", Date.valueOf(gbdatum));
        System.out.println("\n \n[Test]Uitvoer na het gebruik van de methode ReizigerDAO.save() en Reiziger.findAll() :\n");
        rdao.save(Joost);
        List<Reiziger> reizigers1 = rdao.findAll();
        for (Reiziger r : reizigers1) {
            System.out.println(r);
        }

        //update
        Joost.setTussenvoegsel("van");
        System.out.println("\n \n[Test]Voor ReizigerDAO.update() :  " + rdao.findById(6));
        rdao.update(Joost);
        System.out.println("Na ReizigerDAO.update()   :  " + rdao.findById(6));

        //delete
        System.out.print("\n[Test] Reizigers voor delete: " + reizigers.size() + "  , reizigers na delete: ");
        rdao.delete(Joost);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + "\n");

        //findbyid
        Reiziger reiziger = rdao.findById(1);
        System.out.println("[Test] findById(1) geeft de reiziger:");
        System.out.println(reiziger);

        //findbygeboortedatum
        List<Reiziger> reizigers2 = rdao.findByGbdatum("2002-10-22");
        System.out.println("\n[Test] findByGbdatum geeft de reizigers:");
        for (Reiziger r2 : reizigers2) {
            System.out.println(r2);
        }
        System.out.println();
    }



    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
        testFetchAll();
        new Main();
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

}