package edu.icet.demo.dao.custom;

import edu.icet.demo.dao.SuperDao;
import edu.icet.demo.entity.SupplierEntity;
import javafx.collections.ObservableList;

public interface SupplierDao extends SuperDao {
    ObservableList<SupplierEntity> searchAll();

    boolean insert(SupplierEntity supplierEntity);

    boolean update(SupplierEntity supplierEntity);

    boolean delete(String id);
}
