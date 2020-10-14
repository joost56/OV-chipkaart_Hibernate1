package ovchip.dao;

import ovchip.domein.OVChipkaart;
import ovchip.domein.Reiziger;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO <T, Id extends Serializable> {
    boolean save(OVChipkaart ovChipkaart) throws SQLException;
    boolean update(OVChipkaart ovChipkaart) throws SQLException;
    boolean delete(OVChipkaart ovChipkaart) throws SQLException;
    List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
    List<OVChipkaart> findAll() throws SQLException;
}
