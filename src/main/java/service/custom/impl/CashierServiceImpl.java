package service.custom.impl;

import dto.Orders;
import entity.OrdersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.OrdersRepository;
import service.custom.CashierService;
import util.Repositorytype;

import java.time.LocalDate;
import java.util.List;

public class CashierServiceImpl implements CashierService {
    OrdersRepository ordersRepository= DaoFactory.getInstance().getRepositoryType(Repositorytype.ORDER);
    @Override
    public ObservableList<Orders> getAllOrders(String id) {
        ObservableList<Orders>ordersList= FXCollections.observableArrayList();
        List<OrdersEntity> ordersEntities = ordersRepository.searchOrdersById(id);
        ordersEntities.forEach(ordersEntity -> ordersList.add(new ModelMapper().map(ordersEntity, Orders.class)));
        return ordersList;

    }

    @Override
    public ObservableList<Orders> getTodayOrders(String id, LocalDate date) {
        ObservableList<Orders>todayOrderList=FXCollections.observableArrayList();
        ObservableList<Orders> allOrdersList = getAllOrders(id);
        for(Orders order:allOrdersList){
            if(order.getOrderDate().equals(date)){
                todayOrderList.add(order);
            }
        }
        return todayOrderList;
    }

    @Override
    public Double todaySales(String id, LocalDate date) {
        Double todaySales=0.0;
        ObservableList<Orders> ordersEntities = getTodayOrders(id,date);
      for(Orders entity:ordersEntities){
          todaySales+=entity.getNetTotalPrice();
      }
      return todaySales;
    }
}
