package repository.custom;

import entity.OrdersEntity;
import repository.CrudRepository;
import java.util.List;


public interface OrdersRepository extends CrudRepository<OrdersEntity,String> {
    String findLastOrderId();
    List<OrdersEntity>searchOrdersById(String id);


}
