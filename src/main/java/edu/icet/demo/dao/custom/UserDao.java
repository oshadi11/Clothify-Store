package edu.icet.demo.dao.custom;

import edu.icet.demo.dao.SuperDao;
import edu.icet.demo.entity.UserEntity;
import javafx.collections.ObservableList;

public interface UserDao extends SuperDao {
    UserEntity search(String s);

    ObservableList<UserEntity> searchAll();

    boolean insert(UserEntity userEntity);

    boolean delete(String id);

    boolean update(UserEntity userEntity);
}
