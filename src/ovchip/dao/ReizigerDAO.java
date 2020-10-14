package ovchip.dao;

import ovchip.domein.Reiziger;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO <T, Id extends Serializable> {

    boolean save(Reiziger reiziger) throws SQLException;
    boolean update(Reiziger reiziger) throws SQLException;
    boolean delete(Reiziger reiziger) throws SQLException;
    Reiziger findById(int id) throws SQLException;
    List<Reiziger> findByGbdatum(String datum) throws SQLException;
    List<Reiziger> findAll() throws SQLException;

}
