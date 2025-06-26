package service.custom.impl;

import dto.OrderDetails;
import dto.Orders;
import entity.OrdersDetailsEntity;
import entity.OrdersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.OrderDetailRepository;
import repository.custom.OrdersRepository;
import service.custom.OrderService;
import util.Repositorytype;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    OrdersRepository ordersRepository= DaoFactory.getInstance().getRepositoryType(Repositorytype.ORDER);
    OrderDetailRepository  orderDetailRepository=DaoFactory.getInstance().getRepositoryType(Repositorytype.ORDER_DETAIL);

    @Override
    public ObservableList<OrderDetails> searchByOrderId(String id) {
        ObservableList<OrderDetails>orderDetailsList= FXCollections.observableArrayList();
        List<OrdersDetailsEntity> ordersDetailsEntityList = orderDetailRepository.searchOrderDetailsById(id);
        ordersDetailsEntityList.forEach(entity -> orderDetailsList.add(new ModelMapper().map(entity,OrderDetails.class)));
        return orderDetailsList;
    }

    @Override
    public ObservableList<Orders> getAllOrders() {
        ObservableList<Orders>ordersList=FXCollections.observableArrayList();
        ObservableList<OrdersEntity> allOrders = ordersRepository.getAll();
        allOrders.forEach(ordersEntity -> ordersList.add(new ModelMapper().map(ordersEntity, Orders.class)));
        return ordersList;
    }
}
