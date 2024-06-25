package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.dao.custom.CustomerDao;
import edu.icet.demo.entity.CustomerEntity;
import edu.icet.demo.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public ObservableList<CustomerEntity> searchAll(){
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<CustomerEntity> list = session.createQuery("FROM customer").list();
        session.close();

        ObservableList<CustomerEntity> customerEntityList = FXCollections.observableArrayList();

        list.forEach(customerEntity -> {
            customerEntityList.add(customerEntity);
        });
        return customerEntityList;
    }

    @Override
    public boolean insert(CustomerEntity customerEntity){
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(customerEntity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(CustomerEntity customerEntity){
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("UPDATE customer " +
                "SET name =:name,city =:city,email =:email WHERE id =:id");
        query.setParameter("name",customerEntity.getName());
        query.setParameter("city",customerEntity.getCity());
        query.setParameter("email",customerEntity.getEmail());
        query.setParameter("id",customerEntity.getId());

        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM customer WHERE id=:id");
        query.setParameter("id",id);
        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;
    }

    public String getLatestId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM customer ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();
        System.out.println("dao cust");
        session.close();
        return id;
    }

    public ObservableList<CustomerEntity> getCustomersByEmpId(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("FROM customer WHERE empId=:id");
        query.setParameter("id",id);
        List<CustomerEntity> list = query.list();
        session.close();

        ObservableList<CustomerEntity> customerEntityList =FXCollections.observableArrayList();

        list.forEach(customerEntity -> {
            customerEntityList.add(customerEntity);
        });
        return customerEntityList;
    }

    public ObservableList<CustomerEntity> searchAllByEmpId(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("FROM customer WHERE empId=:id");
        query.setParameter("id",id);
        List<CustomerEntity> list = query.list();
        session.close();

        ObservableList<CustomerEntity> customerEntityList =FXCollections.observableArrayList();

        list.forEach(customerEntity -> {
            customerEntityList.add(customerEntity);
        });
        return customerEntityList;
    }
}
