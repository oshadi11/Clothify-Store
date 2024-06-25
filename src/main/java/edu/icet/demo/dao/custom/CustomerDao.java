package edu.icet.demo.dao.custom;

import edu.icet.demo.dao.SuperDao;
import edu.icet.demo.entity.CustomerEntity;
import javafx.collections.ObservableList;

public interface CustomerDao extends SuperDao {

    ObservableList<CustomerEntity> searchAll();

    boolean insert(CustomerEntity customerEntity);

    boolean update(CustomerEntity customerEntity);

    boolean delete(String id);
}
