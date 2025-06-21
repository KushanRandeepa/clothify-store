package repository;

import javafx.collections.ObservableList;

import java.sql.SQLException;


public interface CrudRepository<T,ID> extends SuperRepository{
    boolean add(T entity) throws SQLException;
    boolean update (T entity);
    boolean deleteById(ID id);
    T searchById(ID id);
    ObservableList<T> getAll();

}
