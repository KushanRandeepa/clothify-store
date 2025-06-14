package service.custom.impl;

import dto.Product;
import entity.ProductsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.impl.ProductRepositoryImpl;
import service.custom.ProductService;
import util.Repositorytype;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    ProductRepositoryImpl repository=DaoFactory.getInstance().getRepositoryType(Repositorytype.PRODUCT);


    @Override
    public boolean addProduct(Product product) {
        ProductsEntity entity = new ModelMapper().map(product, ProductsEntity.class);
        entity.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        entity.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return repository.add(entity);
    }

    @Override
    public boolean deleteProduct(String id) {
        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        return false;
    }

    @Override
    public Product searchById(String id) {
        return null;
    }

    @Override
    public ObservableList<Product> getAll() {
        ObservableList<Product> allProducts = FXCollections.observableArrayList();

        List<ProductsEntity> entityList = repository.getAll();
        entityList.forEach(productsEntity -> allProducts.add(new ModelMapper().map(productsEntity, Product.class)));
        return allProducts;
    }
}
