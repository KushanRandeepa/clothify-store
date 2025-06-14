package service;

import service.custom.impl.ProductServiceImpl;
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

        }
        return null;
    }

}
