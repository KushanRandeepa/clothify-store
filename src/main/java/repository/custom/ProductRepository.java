package repository.custom;

import entity.OrdersDetailsEntity;
import entity.OrdersEntity;
import entity.ProductsEntity;
import repository.CrudRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends CrudRepository<ProductsEntity,String> {
    List<String> getAllIds();
    String findLastProductId();
    boolean updateStock(List<OrdersDetailsEntity> list) throws SQLException;
    List<String> checkStockIsLow();
}
