package edu.icet.demo.dao.custom;

import edu.icet.demo.dao.CrudDao;
import edu.icet.demo.dao.SuperDao;
import edu.icet.demo.entity.CustomerEntity;
import javafx.collections.ObservableList;

public interface CustomerDao extends CrudDao<CustomerEntity,String> {

}
