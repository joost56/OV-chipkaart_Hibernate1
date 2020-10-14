package ovchip.dao;
import ovchip.domein.OVChipkaart;
import ovchip.domein.Product;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface ProductDAO <T, Id extends Serializable> {
    boolean save(Product product) throws SQLException;
    boolean update(Product product) throws SQLException;
    boolean delete(Product product) throws SQLException;
    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException;
    List<Product> findAll() throws SQLException;

}
