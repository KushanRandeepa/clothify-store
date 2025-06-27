package service.custom;

import dto.Customer;
import javafx.collections.ObservableList;
import service.SuperService;

import java.sql.SQLException;

public interface CustomerService extends SuperService {
    ObservableList<String>getAllCustomerList();
    Customer searchCustomerById(String id);
    Customer searchCustomerByNumber(String phoneNumber);
    boolean addCustomer(Customer customer) throws SQLException;
    String generateId();
}
