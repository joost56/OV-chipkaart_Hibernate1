package ovchip.Mains;

import ovchip.Hibernate.AdresDAOHibernate;
import ovchip.Hibernate.ReizigerDAOHibernate;
import ovchip.Verbinding.DBVerbinding;
import ovchip.dao.AdresDAO;
import ovchip.dao.ReizigerDAO;
import ovchip.domein.Reiziger;
import ovchip.services.ReizigerService;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Main {

    ReizigerDAOHibernate reizigerDAOHibernate = new ReizigerDAOHibernate();

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        ReizigerService reizigerService = new ReizigerService();
        System.out.println("\n---------- Test ReizigerDAO -------------");

        //findall
        List<Reiziger> reizigers = reizigerService.findAll();
        System.out.println("[Test]Uitvoer na het gebruik van de methode ReizigerDAO.findAll()\n");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }

        //save
        String gbdatum = "2002-03-19";
        Reiziger Joost = new Reiziger(6, "J", "", "Buiting", Date.valueOf(gbdatum));
        System.out.println("\n \n[Test]Uitvoer na het gebruik van de methode ReizigerDAO.save() en Reiziger.findAll() :\n");
        reizigerService.save(Joost);
        List<Reiziger> reizigers1 = reizigerService.findAll();
        for (Reiziger r : reizigers1) {
            System.out.println(r);
        }

        //update
        Joost.setTussenvoegsel("van");
        System.out.println("\n \n[Test]Voor ReizigerDAO.update() :  " + reizigerService.findById(6));
        reizigerService.update(Joost);
        System.out.println("Na ReizigerDAO.update()   :  " + reizigerService.findById(6));

        //delete
        System.out.print("\n[Test] Reizigers voor delete: " + reizigers.size() + "  , reizigers na delete: ");
        reizigerService.delete(6);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + "\n");

        //findbyid
        Reiziger reiziger = reizigerService.findById(1);
        System.out.println("[Test] findById(1) geeft de reiziger:");
        System.out.println(reiziger);

        //findbygeboortedatum
        List<Reiziger> reizigers2 = reizigerService.findByGbdatum("2002-10-22");
        System.out.println("\n[Test] findByGbdatum geeft de reizigers:");
        for (Reiziger r2 : reizigers2) {
            System.out.println(r2);
        }
        System.out.println();
    }


    public Main() throws SQLException {
        testReizigerDAO(reizigerDAOHibernate);

    }

    public static void main(String[] args) throws SQLException {
        new Main();

    }
}
