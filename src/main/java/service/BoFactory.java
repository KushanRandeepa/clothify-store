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
            case CASHIER:return (T) new CashierServiceImpl();
            case ORDER:return (T) new OrderServiceImpl();
            case CUSTOMER:return (T) new CustomerServiceImpl();
            case REPORTS:return (T) new ReportsServiceImpl();
            case PLACE_ORDER:return (T) new PlaceOrderServiceImpl();
        }
        return null;
    }

}
