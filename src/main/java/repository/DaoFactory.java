package repository;

import repository.custom.impl.*;
import util.Repositorytype;

public class DaoFactory {

    private static DaoFactory instance;

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return instance == null ? instance = new DaoFactory() : instance;
    }

    public <T extends SuperRepository>T getRepositoryType(Repositorytype type){
        switch (type){
            case PRODUCT:return (T) new ProductRepositoryImpl();
            case USER:return (T) new UserRepositoryImpl();
            case ORDER:return (T) new OrderRepositoryImpl();
            case EMPLOYEE:return (T) new EmployeeRepositoryImpl();
            case ORDER_DETAIL:return (T) new OrderDetailRepositoryImpl();
        }
        return null;
    }





}

