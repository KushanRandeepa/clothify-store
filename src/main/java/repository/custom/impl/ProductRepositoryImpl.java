package repository.custom.impl;

import entity.OrdersDetailsEntity;
import entity.ProductsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import repository.custom.ProductRepository;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class  ProductRepositoryImpl implements ProductRepository {
    @Override
    public boolean add(ProductsEntity entity) {
        try {
            CrudUtil.execute("INSERT INTO product_entity VALUES(?,?,?,?,?,?,?,?)",
                    entity.getId(),
                    entity.getName(),
                    entity.getCategory(),
                    entity.getSize(),
                    entity.getStock(),
                    entity.getPrice(),
                    entity.getCreatedAt(),
                    entity.getUpdatedAt()
                    );
            return true;
        } catch (SQLException e) {
          new Alert(Alert.AlertType.ERROR,"repository layer error").show();
          return false;
        }
    }

    @Override
    public boolean update(ProductsEntity entity) {
        try {
            CrudUtil.execute("UPDATE product_entity SET name=? , category=? , size=? , stock=? , price=? , discount=? ,updated_at=? WHERE id=?",
                    entity.getName(),
                    entity.getCategory(),
                    entity.getSize(),
                    entity.getStock(),
                    entity.getPrice(),
                    entity.getDiscount(),
                    entity.getUpdatedAt(),
                    entity.getId()
            );
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    @Override
    public boolean deleteById(String id) {
        try {
            CrudUtil.execute("DELETE FROM product_entity WHERE id='"+id+"'" );
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    @Override
    public ProductsEntity searchById(String id) {
        try {
            ResultSet resultSet=CrudUtil.execute("SELECT * FROM product_entity WHERE id='"+id+"'");
            while (resultSet.next()){
                return new ProductsEntity(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getLong(5),
                        resultSet.getDouble(6),
                        resultSet.getDouble(9)
                );
            }

        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public ObservableList<ProductsEntity> getAll() {
        ObservableList<ProductsEntity> allProductsEntityList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM product_entity");
            while (resultSet.next()){
                allProductsEntityList.add(new ProductsEntity(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getString("size"),
                        resultSet.getLong("stock"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("discount")
                ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"database error").show();
        }
        return allProductsEntityList;
    }

    @Override
    public List<String> getAllIds() {
        return List.of();
    }

    @Override
    public String findLastProductId() {
        try {
           ResultSet resultSet= CrudUtil.execute("SELECT id FROM product_entity ORDER BY created_at DESC LIMIT 1");

           if(resultSet.next()){
               return resultSet.getString("id");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,String.valueOf(e)).show();
        }
        return null;
    }

    @Override
    public boolean updateStock(List<OrdersDetailsEntity> list)   {
        for (OrdersDetailsEntity entity:list){
            boolean isUpdate = updateStock(entity);
            if(!isUpdate){
                return false;
            }
        }
        return true;
    }

    @Override
    public List<String> checkStockIsLow() {
        List<String> idList= new ArrayList<>();
        try {
            ResultSet resultSet=CrudUtil.execute("SELECT id  FROM product_entity  WHERE stock < 10");
            while (resultSet.next()){
                idList.add(resultSet.getString("id"));
            }
            return idList;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"db error").show();
            return Collections.emptyList();
        }
    }


    public boolean updateStock(OrdersDetailsEntity ordersDetails)   {
        try {
            CrudUtil.execute("UPDATE product_entity SET stock=stock-? WHERE id=?",
                    ordersDetails.getQty(),
                    ordersDetails.getProductId()
            );
            return true;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,String.valueOf(e)).show();
            return false;
        }
    }
}

