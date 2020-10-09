package ovchip.Hibernate;

import ovchip.dao.AdresDAO;
import ovchip.dao.ReizigerDAO;
import ovchip.domein.Adres;
import ovchip.domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private Connection conn;
    private ReizigerDAO rdao;

    public AdresDAOHibernate(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save (Adres adres) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO adres VALUES (?,?,?,?,?,?)");
        preparedStatement.setInt(1, adres.getAdres_id());
        preparedStatement.setString(2, adres.getPostcode());
        preparedStatement.setString(3, adres.getHuisnummer());
        preparedStatement.setString(4, adres.getStraat());
        preparedStatement.setString(5, adres.getWoonplaats());
        preparedStatement.setInt(6, adres.getReiziger_id());
        return preparedStatement.execute();


    }

    @Override
    public boolean update (Adres adres) throws SQLException{
        int id = adres.getAdres_id();
        String postcode = adres.getPostcode();
        String huisnummer = adres.getHuisnummer();
        String straat = adres.getStraat();
        String woonplaats = adres.getWoonplaats();

        Statement stmt = conn.createStatement();

        stmt.executeUpdate("UPDATE adres SET adres_id = " + id + ", postcode = '" + postcode + "', huisnummer = '" + huisnummer + "', straat = '" + straat + "', woonplaats = '" + woonplaats + "' WHERE reiziger_id = " + id);

        return true;
    }

    @Override
    public boolean delete (Adres adres) throws SQLException{
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM adres WHERE adres_id = ?");
        preparedStatement.setInt(1, adres.getAdres_id());

        return preparedStatement.execute();
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        String query = "SELECT * FROM adres WHERE reiziger_id = " + reiziger.getReiziger_id();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt(1);
            String postcode = rs.getString(2);
            String huisnummer = rs.getString(3);
            String straat = rs.getString(4);
            String woonplaats = rs.getString(5);

            Adres adres = new Adres(id, postcode, huisnummer, straat, woonplaats, reiziger.getReiziger_id());

            return adres;
        }else {
            return null;
        }
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        String query = "SELECT * FROM adres";

        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Adres> adresList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(1);
            String postcode = rs.getString(2);
            String huisnummer = rs.getString(3);
            String straat = rs.getString(4);
            String woonplaats = rs.getString(5);
            int reiziger_id = rs.getInt(6);

            ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(conn);
            List<Reiziger> findAllList = rdao.findAll();
            for (var r : findAllList) {
                if (r.getReiziger_id() == reiziger_id) {
                    Adres a = new Adres(id, postcode, huisnummer, straat, woonplaats, r.getReiziger_id());
                    adresList.add(a);
                }
            }
        }
        ps.close();
        rs.close();
        return adresList;
    }
}
