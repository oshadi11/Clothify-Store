package edu.icet.demo.bo;

import edu.icet.demo.bo.custom.impl.*;
import edu.icet.demo.util.BoType;

public class BoFactory {
    private static BoFactory instance;

    private BoFactory(){}

    public static BoFactory getInstance(){
        return instance!=null?instance:(instance=new BoFactory());
    }
    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case USER:
                return (T)new UserBoImpl();
            case SUPPLIER:
                return (T)new SupplierBoImpl();
            case CUSTOMER:
                return (T)new CustomerBoImpl();
            case PRODUCT:
                return (T)new ProductBoImpl();
            case CART:
                return (T)new PlaceOderBoImpl();
            case ORDER:
                return (T) new OrderBoImpl();
        }
        return null;
    }
}
