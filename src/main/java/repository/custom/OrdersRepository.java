package repository.custom;

import entity.OrdersEntity;
import repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;


public interface OrdersRepository extends CrudRepository<OrdersEntity,String> {
    String findLastOrderId();
    List<OrdersEntity>searchOrdersById(String id);
    List<OrdersEntity>getTodayOrders(LocalDate date);
    List<OrdersEntity>getOrdersBetweenTime(LocalDate start, LocalDate end);

}
