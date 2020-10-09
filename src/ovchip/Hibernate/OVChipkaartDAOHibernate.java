package ovchip.Hibernate;

import ovchip.dao.OVChipkaartDAO;
import ovchip.domein.OVChipkaart;
import ovchip.domein.Product;
import ovchip.domein.Reiziger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    private Connection conn;
    private ProductDAOHibernate productDAOHibernate;
    private ReizigerDAOHibernate reizigerDAOPsql;

    public OVChipkaartDAOHibernate(Connection conn) {
        this.conn = conn;
    }

    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        int nummer = ovChipkaart.getKaartnummer();
        Date geldig_tot = ovChipkaart.getGeldig_tot();
        int klasse = ovChipkaart.getKlasse();
        int saldo = ovChipkaart.getSaldo();
        int id = ovChipkaart.getReiziger().getReiziger_id();

        Statement statement = conn.createStatement();
        statement.executeUpdate("INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (" + nummer + ", '" + geldig_tot + "', " + klasse + ", " + saldo + ", " + id + ")");

        if (!ovChipkaart.products.isEmpty()){
            for (Product product : ovChipkaart.products){
                LocalDate datum = java.time.LocalDate.now();
                statement.executeUpdate("INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES(" + nummer + ", " + product.getProduct_nummer() + ", 'up to date', '" + datum +"')");
            }
        }
        return true;
    }

    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        int nummer = ovChipkaart.getKaartnummer();
        Date geldig_tot = ovChipkaart.getGeldig_tot();
        int klasse = ovChipkaart.getKlasse();
        int saldo = ovChipkaart.getSaldo();
        int id = ovChipkaart.getReiziger().getReiziger_id();

        Statement statement = conn.createStatement();
        statement.executeUpdate("UPDATE ov_chipkaart SET geldig_tot = '" + geldig_tot + "', klasse = '" + klasse + "', saldo = '" + saldo + "', reiziger_id = '" + id + "'where kaart_nummer = " + nummer);

        if (!ovChipkaart.getProducts().isEmpty()) {
            List<Integer> DBList = new ArrayList<>();       //nummers uit de database
            List<Integer> nummersList = new ArrayList<>();  //nummers uit de lijst
            String sql = "SELECT product_nummer FROM ov_chipkaart_product WHERE kaart_nummer = " + nummer;

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            for (Product product : ovChipkaart.getProducts()) {  //alle relaties worden toegevoegd in de lijst op dit niveau
                nummersList.add(product.getProduct_nummer());
            }
            while (resultSet.next()) {   //zolang er relaties uit de database komen worden ze toegevoegd aan de lijst
                int nummerr = resultSet.getInt(1);
                nummersList.add(nummerr);

                for (Product p : ovChipkaart.getProducts()) {
                    if (nummerr == p.getProduct_nummer()) {
                        DBList.remove(nummerr);
                        nummersList.remove(nummerr);
                    }
                }
            }
            for (int num : DBList){
                statement.executeUpdate("DELETE FROM ov_chipkaart_product WHERE product_nummer = " + num + "AND kaart_nummer =" + nummer);
            }
            for (int num : nummersList) {
                LocalDate datum = java.time.LocalDate.now();
                statement.executeUpdate("INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (" + nummer + ", " + num + ", 'actief', '" + datum + "')");
            }
        }
        else {
            statement.executeUpdate("DELETE FROM ov_chipkaart_product WHERE kaart_nummer = " + nummer);
        }
        return true;
    }

    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        int ov_nummer = ovChipkaart.getKaartnummer();
        Statement statement = conn.createStatement();

        if(!ovChipkaart.getProducts().isEmpty()){
            for (Product product : ovChipkaart.getProducts()){
                statement.executeUpdate("DELETE FROM ov_chipkaart_product where kaart_nummer = " + ov_nummer + "AND product_nummer = " + product.getProduct_nummer());
            }
        }
        statement.executeUpdate("DELETE FROM ov_chipkaart where kaart_nummer =" + ov_nummer);
        return true;
    }

    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = " + reiziger.getReiziger_id();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<OVChipkaart> ovChipkaartList = new ArrayList<>();

        while (rs.next()) {
            int kaartnummer = rs.getInt(1);
            Date geldig_tot = rs.getDate(2);
            int klasse = rs.getInt(3);
            int saldo = rs.getInt(4);

            OVChipkaart ovChipkaart = new OVChipkaart(kaartnummer, geldig_tot, klasse, saldo, reiziger);
            List<Product> products = productDAOHibernate.findByOVChipkaart(ovChipkaart);
            if (!products.isEmpty()) {
                for (Product product : products) {
                    ovChipkaart.addProduct(product);
                }
            }
            ovChipkaartList.add(ovChipkaart);
        }
        return ovChipkaartList;
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        String sql = "SELECT * FROM ov_chipkaart";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<OVChipkaart> chipList = new ArrayList<>();

        while (resultSet.next()) {
            int nummer = resultSet.getInt(1);
            Date geldig_tot = resultSet.getDate(2);
            int klasse = resultSet.getInt(3);
            int saldo = resultSet.getInt(4);
            int reiziger_id = resultSet.getInt(5);

            List<Reiziger> findAllList = reizigerDAOPsql.findAll();
            for (var reiziger : findAllList) {
                if (reiziger.getReiziger_id() == reiziger_id) {
                    OVChipkaart ov = new OVChipkaart(nummer, geldig_tot, klasse, saldo, reiziger);//maakt een ov aan alleen als de reiziger bestaat
                    List<Product> productList = productDAOHibernate.findByOVChipkaart(ov);
                    if (!productList.isEmpty()) {
                        for (Product product : productList) {
                            ov.addProduct(product);
                        }
                    }
                    chipList.add(ov);
                }
            }

        }
        return chipList;

    }
}
