package service.custom.impl;

import dto.Customer;
import entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.CustomerRepository;
import service.custom.CustomerService;
import util.Repositorytype;

import java.sql.SQLException;

public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository= DaoFactory.getInstance().getRepositoryType(Repositorytype.CUSTOMER);
    @Override
    public ObservableList<String> getAllCustomerList() {
        ObservableList<String>idList= FXCollections.observableArrayList();
        ObservableList<CustomerEntity> allCustomers = customerRepository.getAll();
        allCustomers.forEach(customerEntity -> idList.add(customerEntity.getId()));
        return idList;
    }

    @Override
    public Customer searchCustomerById(String id) {
        return new ModelMapper().map(customerRepository.searchById(id), Customer.class);
    }

    @Override
    public Customer searchCustomerByNumber(String phoneNumber) {
        return new ModelMapper().map(customerRepository.searchByPhoneNumber(phoneNumber),Customer.class);
    }

    @Override
    public boolean addCustomer(Customer customer) throws SQLException {
        CustomerEntity map = new ModelMapper().map(customer, CustomerEntity.class);
        map.setId(generateId());
        return customerRepository.add(map);
    }

    @Override
    public String generateId() {
        String lastId = customerRepository.findLastProductId();
        if (lastId!=null){
            int number = Integer.parseInt(lastId.replaceAll("[^0-9]", ""));
            number++;
            return String.format("C%04d",number);
        }else{
            return "P001";
        }
    }
}
