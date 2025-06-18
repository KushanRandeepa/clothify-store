package service;

import service.custom.impl.*;
import util.ServiceType;

public class BoFactory {
    private static BoFactory instance;
    private BoFactory(){}

    public static BoFactory getInstance(){
        return instance==null?instance=new BoFactory():instance;
    }

    public <T extends SuperService>T getServiceType(ServiceType type){
        switch (type){
            case PRODUCT:return (T) new ProductServiceImpl();
            case AUTH:return (T) new AuthServiceImpl();
            case USER:return (T) new UserManagerServiceImpl();
            case SUPPLIER:return (T) new SupplierServiceImpl();
            case ORDER:return (T) new OrderServiceImpl();
            case EMPLOYEE:return (T) new EmployeeServiceImpl();
            case REPORTS:return (T) new ReportsServiceImpl();
            case PLACE_ORDER:return (T) new PlaceOrderServiceImpl();
        }
        return null;
    }

}
