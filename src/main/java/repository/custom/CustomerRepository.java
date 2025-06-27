package repository.custom;

import entity.CustomerEntity;
import repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity,String> {
    CustomerEntity searchByPhoneNumber(String number);
    String findLastProductId();

}
