package edu.icet.demo.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.demo.bo.custom.SupplierBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.impl.SupplierDaoImpl;
import edu.icet.demo.entity.SupplierEntity;
import edu.icet.demo.entity.UserEntity;
import edu.icet.demo.model.Supplier;
import edu.icet.demo.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SupplierBoImpl implements SupplierBo {
    SupplierDaoImpl supplierDaoImpl= DaoFactory.getInstance().getDao(DaoType.SUPPLIER);

    public String generateSupplierId() {
        System.out.println("start");
        String lastSupplierId = supplierDaoImpl.getLatestId();
        System.out.println("after dao");
        if (lastSupplierId==null){
            return "S0001";
        }

        int number = Integer.parseInt(lastSupplierId.substring(1));
        number++;
        return String.format("S%04d", number);
    }
    public boolean addSupplier(Supplier supplier) {
        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setId(supplier.getId());
        supplierEntity.setName(supplier.getName());
        supplierEntity.setEmail(supplier.getEmail());
        supplierEntity.setCompany(supplier.getCompany());
        return supplierDaoImpl.insert(supplierEntity);
    }

    public ObservableList<Supplier> getAllSuppliers() {
        ObservableList<SupplierEntity> supplierEntities = supplierDaoImpl.searchAll();
        ObservableList<Supplier> list = FXCollections.observableArrayList();

        supplierEntities.forEach(supplierEntity -> {
            list.add(new ObjectMapper().convertValue(supplierEntity, Supplier.class));
        });
        return list;
    }

    public boolean updateSupplier(Supplier supplier) {
        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setId(supplier.getId());
        supplierEntity.setName(supplier.getName());
        supplierEntity.setEmail(supplier.getEmail());
        supplierEntity.setCompany(supplier.getCompany());
        return supplierDaoImpl.update(supplierEntity);
    }


    public ObservableList<String> getAllSupplierIds() {

        return supplierDaoImpl.getAllIds();
    }

    public boolean deleteSupplierById(String id) {
        return supplierDaoImpl.delete(id);
    }
}
