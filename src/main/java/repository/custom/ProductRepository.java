package repository.custom;

import entity.ProductsEntity;
import javafx.collections.ObservableList;
import repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductsEntity,String> {
}
