package ovchip.dao;

import ovchip.domein.Adres;
import ovchip.domein.Reiziger;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface AdresDAO <T, Id extends Serializable> {
    boolean save(Adres adres) throws SQLException;
    boolean update(Adres adres) throws SQLException;
    boolean delete(Adres adres) throws SQLException;
    Adres findByReiziger(Reiziger reiziger) throws SQLException;
    List<Adres> findAll() throws SQLException;
}
