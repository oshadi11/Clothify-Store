package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.dao.CrudDao;
import edu.icet.demo.entity.OrderEntity;
import edu.icet.demo.util.HibernateUtil;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class OrderDaoImpl implements CrudDao<OrderEntity, String> {

    @Override
    public OrderEntity search(String s) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("FROM order_table WHERE id=:id");
        query.setParameter("id",s);
        return (OrderEntity)query.uniqueResult();
    }

    @Override
    public ObservableList<OrderEntity> searchAll() {
        return null;
    }

    @Override
    public boolean insert(OrderEntity orderEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist((OrderEntity)orderEntity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(OrderEntity orderEntity) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM order_table WHERE id=:id");
        query.setParameter("id",s);
        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;
    }
    public String getLatestOrderId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM order_table ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();

        session.close();

        return id;
    }
}
