package service.custom;

import dto.OrderDetails;
import dto.Orders;
import javafx.collections.ObservableList;
import service.SuperService;


public interface OrderService extends SuperService {
    ObservableList<OrderDetails> searchByOrderId(String id);
    ObservableList<Orders>getAllOrders();

}
