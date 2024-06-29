package edu.icet.demo.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.demo.bo.custom.UserBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.impl.UserDaoImpl;
import edu.icet.demo.entity.UserEntity;
import edu.icet.demo.model.User;
import edu.icet.demo.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserBoImpl implements UserBo {
    UserDaoImpl userDaoImpl = DaoFactory.getInstance().getDao(DaoType.USER);
    public String generateEmployeeId() {

        String lastEmployeeId = userDaoImpl.getLatestId();
        if (lastEmployeeId==null){
            return "U0001";
        }

        int number = Integer.parseInt(lastEmployeeId.split("U")[1]);
        number++;
        return String.format("U%04d", number);
    }
    public UserEntity getUserByEmail(String email) {
        return userDaoImpl.search(email);
    }

    public boolean deleteUserById(String id){
        return userDaoImpl.delete(id);
    }

    public boolean insertUser(User user){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setCity(user.getCity());
        userEntity.setAge(user.getAge());
        userEntity.setRole(user.getRole());
        return userDaoImpl.insert(userEntity);
    }

    public ObservableList<User> getAllUsers(){
        ObservableList<UserEntity> list = userDaoImpl.searchAll();
        ObservableList<User> userList =FXCollections.observableArrayList();

        list.forEach(userEntity -> {
            userList.add(new ObjectMapper().convertValue(userEntity,User.class));
        });
        return userList;
    }
    public ObservableList<String> getAllUserIds(){
        ObservableList<UserEntity> list = userDaoImpl.searchAll();
        ObservableList<String> userList =FXCollections.observableArrayList();

        list.forEach(userEntity -> {
            userList.add(userEntity.getId());
        });
        return userList;
    }
    public boolean updateUser(User user){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setCity(user.getCity());
        userEntity.setAge(user.getAge());
        userEntity.setRole(user.getRole());

        return userDaoImpl.update(userEntity);
    }

    public User getUserById(String id){
        UserEntity userEntity = userDaoImpl.searchById(id);
        return new ObjectMapper().convertValue(userEntity,User.class);
    }
}
