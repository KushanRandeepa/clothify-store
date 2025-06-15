package repository;

import javafx.collections.ObservableList;


public interface CrudRepository<T,ID> extends SuperRepository{
    boolean add(T entity);
    boolean update (T entity);
    boolean deleteById(ID id);
    T searchById(ID id);
    ObservableList<T> getAll();
}
