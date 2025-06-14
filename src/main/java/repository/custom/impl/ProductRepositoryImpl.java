package repository.custom.impl;

import entity.ProductsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import repository.custom.ProductRepository;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRepositoryImpl implements ProductRepository {
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

        return false;
    }

    @Override
    public boolean deleteById(String s) {
        return false;
    }

    @Override
    public ProductsEntity searchById(String s) {
        return null;
    }

    @Override
    public ObservableList<ProductsEntity> getAll() {
        ObservableList<ProductsEntity> allProductsEntityList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM product_entity");
            while (resultSet.next()){
                allProductsEntityList.add(new ProductsEntity(
                        resultSet.getNString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getString("size"),
                        resultSet.getLong("stock"),
                        resultSet.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"database error").show();
        }
        return allProductsEntityList;
    }
}
