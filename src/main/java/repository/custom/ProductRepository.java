package repository.custom;

import entity.ProductsEntity;
import repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<ProductsEntity,String> {
    List<String> getAllIds();
    String findLastProductId();
}
