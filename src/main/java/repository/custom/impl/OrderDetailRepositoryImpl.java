package repository.custom.impl;

import entity.OrdersDetailsEntity;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import repository.custom.OrderDetailRepository;
import util.CrudUtil;

import java.sql.SQLException;
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
