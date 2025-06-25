package repository.custom;

import entity.OrdersEntity;
import javafx.collections.ObservableList;
import repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;


public interface OrdersRepository extends CrudRepository<OrdersEntity,String> {
    String findLastOrderId();
    List<OrdersEntity>searchOrdersById(String id);
    List<OrdersEntity> searchOrdersByDate(String id, LocalDate date);

}
