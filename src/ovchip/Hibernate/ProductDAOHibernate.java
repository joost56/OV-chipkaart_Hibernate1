package ovchip.Hibernate;

import ovchip.dao.ProductDAO;
import ovchip.domein.OVChipkaart;
import ovchip.domein.Product;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    private Connection conn;
    private OVChipkaartDAOHibernate ovdao;


    public ProductDAOHibernate (Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Product product)throws SQLException {
        int p_nummer = product.getProduct_nummer();
        String naam = product.getNaam();
        String beschrijving = product.getBeschrijving();
        int prijs = product.getPrijs();

        Statement statement = conn.createStatement();
        statement.executeUpdate("INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (" + p_nummer + ", '" + naam + "', '" + beschrijving + "', " + prijs + ")");

        if (!product.getOvkaarten().isEmpty()) {
            for (OVChipkaart ovChipkaart : product.getOvkaarten()) {
                LocalDate datum = java.time.LocalDate.now();
                statement.executeUpdate("INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (" + ovChipkaart.getKaartnummer() + ", " + p_nummer + ", 'updated', '" + datum + "')");
            }
        }
        return true;
    }

    @Override
    public boolean update(Product product) throws SQLException{
        int p_nummer = product.getProduct_nummer();
        String naam = product.getNaam();
        String beschrijving = product.getBeschrijving();
        int prijs = product.getPrijs();

        Statement statement = conn.createStatement();
        statement.executeUpdate("UPDATE product SET naam = '" + naam + "',beschrijving = '" + beschrijving + "',prijs = "  + prijs + " WHERE product_nummer = " + p_nummer);

        if (!product.getOvkaarten().isEmpty()){//haal alle relaties uit de db
            List<Integer> DBList = new ArrayList<>();       //nummers uit de database
            List<Integer> nummersList = new ArrayList<>();  //nummers uit de lijst
            String sql = "SELECT kaart_nummer from ov_chipkaart_product where product_nummer = " + p_nummer;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            for (OVChipkaart ovChipkaart : product.getOvkaarten()){
                nummersList.add(ovChipkaart.getKaartnummer());
            }
            while (resultSet.next()){
                int nummerr = resultSet.getInt(1);
                DBList.add(nummerr);

                for (OVChipkaart ovChipkaart : product.getOvkaarten()) {
                    if (nummerr == ovChipkaart.getKaartnummer()) {
                        DBList.remove(nummerr);
                        nummersList.remove(nummerr);
                    }
                }
            }
            for (int num : DBList) {
                statement.executeUpdate("DELETE FROM ov_chipkaart_product WHERE kaart_nummer = " + num + " AND product_nummer = " + p_nummer);
            }
            for (int num : nummersList) {
                LocalDate datum = java.time.LocalDate.now();
                statement.executeUpdate("INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (" + num + ", " + p_nummer + ", 'up to date', '" + datum + "')");
            }
        }
        else {
            statement.executeUpdate("DELETE FROM ov_chipkaart_product WHERE product_nummer = " + p_nummer);
        }
        return true;
    }

    @Override
    public boolean delete(Product product) throws SQLException{
        int p_nummer = product.getProduct_nummer();

        Statement statement = conn.createStatement();
        if (!product.getOvkaarten().isEmpty()) {
            for (OVChipkaart ov : product.getOvkaarten()) {
                statement.executeUpdate("DELETE FROM ov_chipkaart_product WHERE kaart_nummer = " + ov.getKaartnummer() + " AND product_nummer = " + p_nummer);
            }
        }
        statement.executeUpdate("DELETE FROM product WHERE product_nummer = " + p_nummer);


        return true;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException{
        String sql = "SELECT *" +"FROM product " +"INNER JOIN ov_chipkaart_product ON ov_chipkaart_product.product_nummer = product.product_nummer " +"WHERE ov_chipkaart_product.kaart_nummer =" + ovChipkaart.getKaartnummer();

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Product> productList = new ArrayList<>();

        while(resultSet.next()) {
            int p_nummer = resultSet.getInt(1);
            String p_naam = resultSet.getString(2);
            String p_beschrijving = resultSet.getString(3);
            int p_prijs = resultSet.getInt(4);

            Product product = new Product(p_nummer, p_naam, p_beschrijving, p_prijs);

            product.addOVChipkaarten(ovChipkaart);
            productList.add(product);
        }
        return productList;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        String sql = "SELECT * FROM product";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Product> productList = new ArrayList<>();

        while (rs.next()) {
            int p_nummer = rs.getInt(1);
            String p_naam = rs.getString(2);
            String p_beschrijving = rs.getString(3);
            int p_prijs = rs.getInt(4);

            Product product = new Product(p_nummer, p_naam, p_beschrijving, p_prijs);

            productList.add(product);
        }
        return productList;
    }
}
