package edu.icet.demo.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.demo.bo.custom.ProductBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.impl.ProductDaoImpl;
import edu.icet.demo.entity.ProductEntity;
import edu.icet.demo.model.Product;
import edu.icet.demo.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductBoImpl implements ProductBo {
    ProductDaoImpl productDaoImpl = DaoFactory.getInstance().getDao(DaoType.PRODUCT);

    public boolean addProduct(Product product) {
        ProductEntity productEntity = new ProductEntity(product.getId(),
                product.getName(),
                product.getQty(),
                product.getCategory(),
                product.getPrice(),
                product.getSupId()
        );
        return productDaoImpl.insert(productEntity);
    }

    public String generateProductId() {
        String id = productDaoImpl.getLatestId();

        if (id == null){
            return "P0001";
        }

        int number = Integer.parseInt(id.split("P")[1]);
        number++;
        return String.format("P%04d", number);
    }

    public ObservableList<Product> getAllProducts() {
        ObservableList<ProductEntity> productEntities = productDaoImpl.searchAll();
        ObservableList<Product> productsList = FXCollections.observableArrayList();

        productEntities.forEach(productEntity -> {
            productsList.add(new ObjectMapper().convertValue(productEntity,Product.class));
        });

        return productsList;
    }


    public boolean updateProduct(Product product) {
        ProductEntity productEntity = new ProductEntity(product.getId(),
                product.getName(),
                product.getQty(),
                product.getCategory(),
                product.getPrice(),
                product.getSupId()
        );
        return productDaoImpl.update(productEntity);
    }

    public boolean deleteProduct(String id) {
        return productDaoImpl.delete(id);
    }


    public ObservableList<String> getAllProductIds() {
        return productDaoImpl.searchAllIds();
    }



}
