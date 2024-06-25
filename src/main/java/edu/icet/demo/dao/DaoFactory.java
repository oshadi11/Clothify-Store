package edu.icet.demo.dao;

import edu.icet.demo.bo.custom.impl.CustomerBoImpl;
import edu.icet.demo.bo.custom.impl.SupplierBoImpl;
import edu.icet.demo.dao.custom.impl.CustomerDaoImpl;
import edu.icet.demo.dao.custom.impl.ProductDaoImpl;
import edu.icet.demo.dao.custom.impl.SupplierDaoImpl;
import edu.icet.demo.dao.custom.impl.UserDaoImpl;
import edu.icet.demo.util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return instance != null ? instance : (instance = new DaoFactory());
    }

    public <T extends SuperDao> T getDao(DaoType type) {
        switch (type) {
            case USER:
                return (T) new UserDaoImpl();
            case SUPPLIER:
                return (T)new SupplierDaoImpl();
            case CUSTOMER:
                return (T)new CustomerDaoImpl();
            case PRODUCT:
                return (T)new ProductDaoImpl();
            case ORDER:
                return (T)new ProductDaoImpl();/////////////////////////////

        }
        return null;
    }
}