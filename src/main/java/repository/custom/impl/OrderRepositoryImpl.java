package repository.custom.impl;

import db.DBConnection;
import dto.OrderDetails;
import entity.OrdersDetailsEntity;
import entity.OrdersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.OrderDetailRepository;
import repository.custom.OrdersRepository;
import repository.custom.ProductRepository;
import util.CrudUtil;
import util.Repositorytype;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrdersRepository {

    OrderDetailRepository orderDetailRepository = DaoFactory.getInstance().getRepositoryType(Repositorytype.ORDER_DETAIL);
    ProductRepository productRepository = DaoFactory.getInstance().getRepositoryType(Repositorytype.PRODUCT);

    @Override
    public String findLastOrderId() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");
            if (resultSet.next()) {
                return resultSet.getString("order_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public boolean add(OrdersEntity ordersEntity) throws SQLException {
        List<OrdersDetailsEntity> ordersDetailsEntityList = FXCollections.observableArrayList();
        List<OrderDetails> orderDetailsList = ordersEntity.getOrdersDetails();
        orderDetailsList.forEach(orderDetail -> {
            ordersDetailsEntityList.add(new ModelMapper().map(orderDetail, OrdersDetailsEntity.class));
        });

        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
String sql="INSERT INTO orders VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preStm = connection.prepareStatement(sql);
            preStm.setObject(1, ordersEntity.getOrderId());
            preStm.setObject(2, ordersEntity.getCashierId());
            preStm.setObject(3, ordersEntity.getCustomerId());
            preStm.setObject(4, ordersEntity.getOrderDate());
            preStm.setObject(5, ordersEntity.getOrderTime());
            preStm.setObject(6, ordersEntity.getTotalPrice());
            preStm.setObject(7, ordersEntity.getTotalDiscountAmount());
            preStm.setObject(8, ordersEntity.getNetTotalPrice());
            preStm.setObject(9, ordersEntity.getPaymentAmount());
            preStm.setObject(10, ordersEntity.getBalance());
            boolean isOrderAdd = preStm.executeUpdate() > 0;
            if (isOrderAdd) {
                boolean isAddOrderDetails = orderDetailRepository.addOrderDetails(ordersDetailsEntityList);
                if (isAddOrderDetails) {
                    boolean isUpdated = productRepository.updateStock(ordersDetailsEntityList);
                    if (isUpdated) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean update(OrdersEntity entity) {
        return false;
    }

    @Override
    public boolean deleteById(String s) {
        return false;
    }

    @Override
    public OrdersEntity searchById(String s) {
        return null;
    }

    @Override
    public ObservableList<OrdersEntity> getAll() {
        ObservableList<OrdersEntity>ordersList=FXCollections.observableArrayList();
        try {
            ResultSet resultSet=CrudUtil.execute("SELECT * FROM orders");
            while (resultSet.next()){
                ordersList.add(new OrdersEntity(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getTime(5).toLocalTime(),
                        resultSet.getDouble(6),
                        resultSet.getDouble(7),
                        resultSet.getDouble(8),
                        resultSet.getDouble(9),
                        resultSet.getDouble(10)
                ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"db error").show();
        }
        return ordersList;
    }


    @Override
    public List<OrdersEntity> searchOrdersById(String id) {
        List<OrdersEntity>ordersList=new ArrayList<>();
        try {
            ResultSet resultSet=CrudUtil.execute("SELECT * FROM orders WHERE  cashier_id='"+id+"'");
            while (resultSet.next()){
                ordersList.add(new OrdersEntity(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getTime(5).toLocalTime(),
                        resultSet.getDouble(6),
                        resultSet.getDouble(7),
                        resultSet.getDouble(8),
                        resultSet.getDouble(9),
                        resultSet.getDouble(10)
                ));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"db error").show();
        }
        return ordersList;
    }

}
