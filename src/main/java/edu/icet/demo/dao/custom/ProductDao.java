package edu.icet.demo.dao.custom;

import edu.icet.demo.dao.SuperDao;
import edu.icet.demo.entity.ProductEntity;
import javafx.collections.ObservableList;

public interface ProductDao extends SuperDao {
    ObservableList<ProductEntity> searchAll();

    boolean insert(ProductEntity productEntity);

    boolean update(ProductEntity productEntity);

    boolean delete(String s);
}
