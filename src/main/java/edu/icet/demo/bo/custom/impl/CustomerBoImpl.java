package edu.icet.demo.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.demo.bo.custom.CustomerBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.CustomerDao;
import edu.icet.demo.dao.custom.impl.CustomerDaoImpl;
import edu.icet.demo.entity.CustomerEntity;
import edu.icet.demo.model.Customer;
import edu.icet.demo.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerBoImpl implements CustomerBo {

    CustomerDaoImpl customerDaoImpl = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);

    public String generateCustomerId(){
        String lastCustomerId = customerDaoImpl.getLatestId();

        if (lastCustomerId==null){
            return "C0001";
        }
        int number = Integer.parseInt(lastCustomerId.substring(1));
        number++;
        return String.format("C%04d", number);
    }

    public boolean insertCustomer(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity(customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getCity());


        return  customerDaoImpl.insert(customerEntity);

    }

    public ObservableList<Customer> getAllCustomer() {
        ObservableList<CustomerEntity> customerEntities = customerDaoImpl.searchAll();

        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        customerEntities.forEach(customerEntity -> {
            customerList.add(new ObjectMapper().convertValue(customerEntity, Customer.class));
        });
        return customerList;
    }

    public ObservableList<String> getAllCustomerIds(String id) {
        ObservableList<CustomerEntity> customerEntities = customerDaoImpl.searchAllByEmpId(id);
        ObservableList<String> idList = FXCollections.observableArrayList();

        customerEntities.forEach(customerEntity -> {
            idList.add(customerEntity.getId());
        });
        return idList;
    }

    public boolean updateCustomer(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity(customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getCity());
        return customerDaoImpl.update(customerEntity);
    }

    public boolean deleteCustomerById(String id) {
        return customerDaoImpl.delete(id);
    }
}
