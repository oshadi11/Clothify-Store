package edu.icet.demo.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.demo.bo.SuperBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.PlaceOrderDao;
import edu.icet.demo.dao.custom.impl.OrderDaoImpl;
import edu.icet.demo.dao.custom.impl.PlaceOrderDaoImpl;
import edu.icet.demo.dao.custom.impl.ProductDaoImpl;
import edu.icet.demo.entity.OrderHasItemEntity;
import edu.icet.demo.entity.ProductEntity;
import edu.icet.demo.model.OrderHasItem;
import edu.icet.demo.model.Product;
import edu.icet.demo.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlaceOrderBoImpl implements PlaceOrderDao {
    PlaceOrderDaoImpl placeOrderDao = DaoFactory.getInstance().getDao(DaoType.CART);
    ProductDaoImpl productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);


    public ObservableList<String> getProductIds() {

        return productDao.searchAllIds();
    }

    public Product getProductById(String newValue) {
        ProductEntity productEntity = productDao.search(newValue);
        return new ObjectMapper().convertValue(productEntity, Product.class);
    }

    public ObservableList<Product> getAllProducts() {
        ObservableList<ProductEntity> list = productDao.searchAll();
        ObservableList<Product> products = FXCollections.observableArrayList();

        list.forEach(productEntity -> {
            products.add(new ObjectMapper().convertValue(productEntity, Product.class));
        });
        return products;
    }

    public String generateOrderId() {
        String id = new OrderDaoImpl().getLatestOrderId();

        if (id==null){
            return "X0001";
        }
        int number = Integer.parseInt(id.split("X")[1]);
        number++;
        return String.format("X%04d", number);
    }

    public boolean saveOrderDetails(ObservableList<OrderHasItem> orderHasItemObservableList) {
        return placeOrderDao.saveAll(orderHasItemObservableList); //line 53
    }

    public int getLatestCartId() {
        return placeOrderDao.getLatestId() + 1;
    }


    public boolean deleteById(String id) {
        return placeOrderDao.deleteByOrderId(id);
    }

    public ObservableList<OrderHasItem> getProductsByOrderId(String id) {

        return placeOrderDao.getProductsByOrderId(id);
    }

    public boolean updateNewQty(String id, int qty) {
        productDao.updateQty(id, qty);
        return true;
    }
    public boolean increaseQtyOfProduct(String id, int qty) {

        return productDao.updateQtyOfProduct(id, qty);
    }


    @Override
    public OrderHasItemEntity search(String s) {
        return null;
    }

    @Override
    public ObservableList<OrderHasItemEntity> searchAll() {
        return null;
    }

    @Override
    public boolean insert(OrderHasItemEntity orderHasItemEntity) {
        return false;
    }

    @Override
    public boolean update(OrderHasItemEntity orderHasItemEntity) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }
}
