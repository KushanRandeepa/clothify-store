package repository.custom;


import entity.OrdersDetailsEntity;
import repository.CrudRepository;

import java.util.List;

public interface OrderDetailRepository extends CrudRepository<OrdersDetailsEntity,String> {
    boolean addOrderDetails(List<OrdersDetailsEntity> list );
}
