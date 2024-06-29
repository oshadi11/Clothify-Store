package edu.icet.demo.dao.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.demo.dao.CrudDao;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.entity.OrderHasItemEntity;
import edu.icet.demo.model.OrderHasItem;
import edu.icet.demo.util.DaoType;
import edu.icet.demo.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PlaceOrderDaoImpl implements CrudDao<OrderHasItemEntity, String> {

    ProductDaoImpl productDaoImpl = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
    @Override
    public OrderHasItemEntity search(String s) {
        return null;
    }

    @Override
    public ObservableList<OrderHasItemEntity> searchAll() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<OrderHasItemEntity> list = session.createQuery("FROM order_has_items").list();
        session.close();

        ObservableList<OrderHasItemEntity> observableList = FXCollections.observableArrayList();
        list.forEach(orderHasItemEntity -> {
            observableList.add(orderHasItemEntity);
        });
        return observableList;
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


    public boolean saveAll(ObservableList<OrderHasItem> orderHasItemObservableList) {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        orderHasItemObservableList.forEach(orderHasItem -> {//line 63
            OrderHasItemEntity orderHasItemEntity = new ObjectMapper().convertValue(orderHasItem, OrderHasItemEntity.class);
            productDaoImpl.updateQty(orderHasItemEntity.getProductId(),orderHasItemEntity.getQty());
            session.persist(orderHasItemEntity); //line 66
        });
        session.getTransaction().commit();
        session.close();
        return true;
    }
    public int getLatestId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM order_has_items ORDER BY id DESC LIMIT 1");
        try {
            int id = (int) query.uniqueResult();

            return id;
        }catch (NullPointerException e){
            return 0;
        }finally {
            session.getTransaction().commit();
            session.close();
        }

    }

    public boolean deleteByOrderId(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM order_has_items WHERE orderId=:id");
        query.setParameter("id",id);
        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return i>0;
    }

    public ObservableList<OrderHasItem> getProductsByOrderId(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("FROM order_has_items WHERE orderId=:id");
        query.setParameter("id",id);
        List<OrderHasItemEntity> list = query.list();
        session.close();

        ObservableList<OrderHasItem> observableList=FXCollections.observableArrayList();

        list.forEach(s -> {
            observableList.add(new ObjectMapper().convertValue(s, OrderHasItem.class));
        });
        return observableList;

    }

}
