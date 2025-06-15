package service.custom;

import dto.Product;
import javafx.collections.ObservableList;
import service.SuperService;


public interface ProductService extends SuperService {
     boolean addProduct(Product product);
     boolean deleteProduct(String id);
     boolean updateProduct(Product product);
     Product searchById(String id);
     ObservableList<Product> getAll();
     String generateId();
}
