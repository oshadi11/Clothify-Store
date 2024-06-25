package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.dao.custom.UserDao;
import edu.icet.demo.entity.UserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;
import edu.icet.demo.util.HibernateUtil;

import java.util.List;

public class UserDaoImpl implements UserDao {

    public String getLatestId(){
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM user ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();
        session.close();
        return id;
    }
    public UserEntity searchById(String id){
        Session session = HibernateUtil.getSession();
        session.getTransaction();

        Query query = session.createQuery("FROM user WHERE id=:id");
        query.setParameter("id",id);
        UserEntity userEntity = (UserEntity) query.uniqueResult();
        session.close();
        return userEntity;
    }

    @Override
    public UserEntity search(String s){
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("FROM user WHERE email=:email");
        query.setParameter("email",s);
        UserEntity userEntity = (UserEntity) query.uniqueResult();
        session.close();
        System.out.println(userEntity);
        return userEntity;
    }

    @Override
    public ObservableList<UserEntity> searchAll(){

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<UserEntity> userList = session.createQuery("FROM user").list();
        ObservableList<UserEntity> list= FXCollections.observableArrayList();
        session.close();
        userList.forEach(userEntity -> {
            list.add(userEntity);
        });
        return list;

    }

    @Override
    public boolean insert(UserEntity userEntity){

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(userEntity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM user WHERE id=:id");
        query.setParameter("id",id);
        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return i>0;
    }
    @Override
    public boolean update(UserEntity userEntity){
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("UPDATE user SET name =:name,city =:city,email =:email, password =:password, age =:age WHERE id =:id");
        query.setParameter("name",userEntity.getName());
        query.setParameter("city",userEntity.getCity());
        query.setParameter("email",userEntity.getEmail());
        query.setParameter("id",userEntity.getId());
        query.setParameter("password",userEntity.getPassword());
        query.setParameter("age",userEntity.getAge());

        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;
    }
}
