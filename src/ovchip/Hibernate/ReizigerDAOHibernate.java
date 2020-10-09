package ovchip.Hibernate;

import ovchip.dao.ReizigerDAO;
import ovchip.domein.Adres;
import ovchip.domein.OVChipkaart;
import ovchip.domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private Connection conn;
    private Reiziger reiziger;
    private ReizigerDAO reizigerDAO;
    private List<Reiziger> reizigers;

    public ReizigerDAOHibernate(Connection conn) {
        this.conn = conn;
    }


    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO reiziger values (?,?,?,?,?) ");
        preparedStatement.setInt(1, reiziger.getReiziger_id());
        preparedStatement.setString(2, reiziger.getVoorletters());
        preparedStatement.setString(3, reiziger.getTussenvoegsel());
        preparedStatement.setString(4, reiziger.getAchternaam());
        preparedStatement.setDate(5, reiziger.getGeboortedatum());

        AdresDAOHibernate adresDAOPsql = new AdresDAOHibernate(conn);
        Adres adres = adresDAOPsql.findByReiziger(reiziger);
        reiziger.setAdres(adres);

        OVChipkaartDAOHibernate ovChipkaartDAOsql = new OVChipkaartDAOHibernate(conn);
        List<OVChipkaart> OVChipkaarten = ovChipkaartDAOsql.findByReiziger(reiziger);
        for (OVChipkaart ovChipkaart : OVChipkaarten){
            reiziger.setOvChipkaarten(ovChipkaart);
        }

        return preparedStatement.execute();
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        int id = reiziger.getReiziger_id();
        String voorletters = reiziger.getVoorletters();
        String tussenvoegsel = reiziger.getTussenvoegsel();
        String achternaam = reiziger.getAchternaam();
        Date geboortedatum = reiziger.getGeboortedatum();

        Statement stmt = conn.createStatement();

        stmt.executeUpdate("UPDATE reiziger SET reiziger_id = " + id + ", voorletters = '" + voorletters + "', tussenvoegsel = '" + tussenvoegsel + "', achternaam = '" + achternaam + "', geboortedatum = '" + geboortedatum + "' WHERE reiziger_id = " + id);

        AdresDAOHibernate adao = new AdresDAOHibernate(conn);
        Adres adres = adao.findByReiziger(reiziger);
        reiziger.setAdres(adres);

        OVChipkaartDAOHibernate ovChipkaartDAOsql = new OVChipkaartDAOHibernate(conn);
        List<OVChipkaart> OVChipkaarten = ovChipkaartDAOsql.findByReiziger(reiziger);
        for (OVChipkaart ovChipkaart : OVChipkaarten){
            reiziger.setOvChipkaarten(ovChipkaart);
        }

        return true;
    }

    public boolean delete(Reiziger reiziger) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM reiziger WHERE reiziger_id = ?");
        preparedStatement.setInt(1, reiziger.getReiziger_id());

        return preparedStatement.execute();
    }


    public Reiziger findById(int id) throws SQLException {
        String query = "SELECT * FROM reiziger WHERE reiziger_id = " + id;
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int r_id = rs.getInt(1);
            String vl = rs.getString(2);
            String tv = rs.getString(3);
            String an = rs.getString(4);
            Date gd = rs.getDate(5);

            Reiziger reiziger = new Reiziger(r_id, vl, tv, an, gd);
            AdresDAOHibernate adao = new AdresDAOHibernate(conn);
            Adres adres = adao.findByReiziger(reiziger);
            reiziger.setAdres(adres);

            OVChipkaartDAOHibernate ovChipkaartDAOsql = new OVChipkaartDAOHibernate(conn);
            List<OVChipkaart> OVChipkaarten = ovChipkaartDAOsql.findByReiziger(reiziger);
            for (OVChipkaart ovChipkaart : OVChipkaarten){
                reiziger.setOvChipkaarten(ovChipkaart);
            }

            return reiziger;
        }else {
            return null;
        }
    }



    public List<Reiziger> findByGbdatum(String datum)throws SQLException{
        String query = "SELECT * FROM reiziger WHERE geboortedatum = '" + datum + "'";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Reiziger> gblijst = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(1);
            String vl = rs.getString(2);
            String tv = rs.getString(3);
            String an = rs.getString(4);
            Date gd = rs.getDate(5);
            Reiziger reiziger = new Reiziger(id, vl, tv, an, gd);
            AdresDAOHibernate adao = new AdresDAOHibernate(conn);
            Adres adres = adao.findByReiziger(reiziger);
            reiziger.setAdres(adres);

            OVChipkaartDAOHibernate ovChipkaartDAOsql = new OVChipkaartDAOHibernate(conn);
            List<OVChipkaart> OVChipkaarten = ovChipkaartDAOsql.findByReiziger(reiziger);
            for (OVChipkaart ovChipkaart : OVChipkaarten){
                reiziger.setOvChipkaarten(ovChipkaart);
            }

            gblijst.add(reiziger);
        }
        return gblijst;
    }
    public List<Reiziger> findAll() throws SQLException {
        String query = "SELECT * FROM reiziger";

        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<Reiziger> findAllLijst = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(1);
            String voorletter = rs.getString(2);
            String tussenvoegsel = rs.getString(3);
            String achternaam = rs.getString(4);
            Date geboortedatum = rs.getDate(5);

            Reiziger reiziger = new Reiziger(id, voorletter, tussenvoegsel, achternaam, geboortedatum);
            AdresDAOHibernate adao = new AdresDAOHibernate(conn);
            Adres adres = adao.findByReiziger(reiziger);
            reiziger.setAdres(adres);

            OVChipkaartDAOHibernate ovChipkaartDAOsql = new OVChipkaartDAOHibernate(conn);
            List<OVChipkaart> OVChipkaarten = ovChipkaartDAOsql.findByReiziger(reiziger);
            for (OVChipkaart ovChipkaart : OVChipkaarten){
                reiziger.setOvChipkaarten(ovChipkaart);
            }

            findAllLijst.add(reiziger);
        }
        return findAllLijst;
    }
}
