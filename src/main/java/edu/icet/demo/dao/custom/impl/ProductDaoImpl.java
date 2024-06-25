package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.dao.custom.ProductDao;
import edu.icet.demo.entity.ProductEntity;
import edu.icet.demo.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDaoImpl implements ProductDao {


    @Override
    public ObservableList<ProductEntity> searchAll() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<ProductEntity> list = session.createQuery("FROM product").list();
        session.close();

        ObservableList<ProductEntity> productEntities = FXCollections.observableArrayList();
        list.forEach(productEntity -> {
            productEntities.add(productEntity);
        });

        return productEntities;
    }

    @Override
    public boolean insert(ProductEntity productEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(productEntity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(ProductEntity productEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("UPDATE product " +
                "SET name =:name,qty =:qty,category= :category," +
                "price= :price WHERE id =:id");
        query.setParameter("id",productEntity.getId());
        query.setParameter("name",productEntity.getName());
        query.setParameter("qty",productEntity.getQty());
        query.setParameter("category",productEntity.getCategory());
        query.setParameter("price",productEntity.getPrice());

        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return i>0;
    }

    @Override
    public boolean delete(String s) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM product WHERE id= :id");
        query.setParameter("id",s);
        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;
    }

    public String getLatestId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM product ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();

        session.close();

        return id;
    }

    public ObservableList<String> searchAllIds() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<String> list = session.createQuery("SELECT id FROM product").list();
        session.close();
        ObservableList<String> idList = FXCollections.observableArrayList();

        list.forEach(s -> {
            idList.add(s);
        });
        return idList;
    }

    public boolean updateQty(String productId, int qty) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("UPDATE product SET qty=qty-:qty WHERE id=:id");
        query.setParameter("qty",qty);
        query.setParameter("id",productId);

        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return i>0;

    }

    public ObservableList<ProductEntity> getProductByEmpId(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("FROM product WHERE empId=:id");
        query.setParameter("id",id);
        List<ProductEntity> list = query.list();
        session.close();

        ObservableList<ProductEntity> productEntities = FXCollections.observableArrayList();
        list.forEach(productEntity -> {
            productEntities.add(productEntity);
        });

        return productEntities;
    }
}

