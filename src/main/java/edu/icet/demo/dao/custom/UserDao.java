package edu.icet.demo.dao.custom;

import edu.icet.demo.dao.CrudDao;
import edu.icet.demo.dao.SuperDao;
import edu.icet.demo.entity.UserEntity;
import javafx.collections.ObservableList;

public interface UserDao extends CrudDao<UserEntity,String> {

}
