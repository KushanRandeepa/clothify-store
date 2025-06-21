package repository.custom;

import entity.OrdersEntity;
import repository.CrudRepository;


public interface OrdersRepository extends CrudRepository<OrdersEntity,String> {
    String findLastOrderId();

}
