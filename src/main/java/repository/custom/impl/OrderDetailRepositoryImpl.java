package repository.custom.impl;

import entity.OrdersDetailsEntity;
import entity.OrdersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import repository.custom.OrderDetailRepository;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailRepositoryImpl implements OrderDetailRepository {

    @Override
    public boolean addOrderDetails(List<OrdersDetailsEntity> list) {
       for (OrdersDetailsEntity entity:list){
           boolean isAdd = addOrderDetails(entity);
           if(!isAdd){
               return false;
           }
       }
        return true;
    }

    public boolean addOrderDetails(OrdersDetailsEntity ordersDetails) {
        try {
            CrudUtil.execute("INSERT INTO order_details VALUES(?,?,?,?,?,?,?)",
                    ordersDetails.getOrderId(),
                    ordersDetails.getProductId(),
                    ordersDetails.getProductName(),
                    ordersDetails.getQty(),
                    ordersDetails.getPrice(),
                    ordersDetails.getTotalPrice(),
                    ordersDetails.getDiscount()
                    );
            return true;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,String.valueOf(e)).show();
            return false;
        }
    }

    @Override
    public List<OrdersDetailsEntity>  searchOrderDetailsById(String id) {
        List<OrdersDetailsEntity>ordersList=new ArrayList<>();
        try {
            ResultSet resultSet=CrudUtil.execute("SELECT * FROM order_details WHERE order_id='"+id+"'");
            while (resultSet.next()){
                ordersList.add(new OrdersDetailsEntity(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getLong(4),
                        resultSet.getDouble(5),
                        resultSet.getDouble(6),
                        resultSet.getDouble(7)
                ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"db error").show();
        }
        return ordersList;
    }


    @Override
    public boolean add(OrdersDetailsEntity entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(OrdersDetailsEntity entity) {
        return false;
    }

    @Override
    public boolean deleteById(String s) {
        return false;
    }

    @Override
    public OrdersDetailsEntity searchById(String s) {
        return null;
    }

    @Override
    public ObservableList<OrdersDetailsEntity> getAll() {
        return null;
    }
}
