package edu.icet.demo.dao.custom;

import edu.icet.demo.dao.CrudDao;
import edu.icet.demo.dao.SuperDao;
import edu.icet.demo.entity.ProductEntity;
import javafx.collections.ObservableList;

public interface ProductDao extends CrudDao<ProductEntity,String> {

}
