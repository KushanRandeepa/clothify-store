package repository.custom.impl;

import entity.CustomerEntity;
import entity.ProductsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import repository.custom.CustomerRepository;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public boolean add(CustomerEntity entity) throws SQLException {
        try {
            CrudUtil.execute("INSERT INTO customer VALUES(?,?,?,?)",
                    entity.getId(),
                    entity.getName(),
                    entity.getPhoneNumber(),
                    entity.getEmail()
            );
            return true;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"repository layer error").show();
            return false;
        }
    }

    @Override
    public boolean update(CustomerEntity entity) {
        return false;
    }

    @Override
    public boolean deleteById(String s) {
        return false;
    }

    @Override
    public CustomerEntity searchById(String id) {
        try {
            ResultSet resultSet= CrudUtil.execute("SELECT * FROM customer WHERE id='"+id+"'");
            while (resultSet.next()){
                return new CustomerEntity(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public ObservableList<CustomerEntity> getAll() {
        ObservableList<CustomerEntity> allCustomersList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer");
            while (resultSet.next()){
                allCustomersList.add(new CustomerEntity(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"database error").show();
        }
        return allCustomersList;
    }

    @Override
    public CustomerEntity searchByPhoneNumber(String number) {
        try {
            ResultSet resultSet= CrudUtil.execute("SELECT * FROM customer WHERE phone_number='"+number+"'");
            while (resultSet.next()){
                return new CustomerEntity(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public String findLastProductId() {
        try {
            ResultSet resultSet= CrudUtil.execute("SELECT id FROM customer ORDER BY id DESC LIMIT 1");
            if(resultSet.next()){
                return resultSet.getString("id");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,String.valueOf(e)).show();
        }
        return null;
    }
}
