package repository;

import repository.custom.impl.ProductRepositoryImpl;
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

        }
        return null;
    }





}

