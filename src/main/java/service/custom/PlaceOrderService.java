package service.custom;

import dto.BillDetails;
import dto.Orders;
import dto.OrderDetails;
import javafx.collections.ObservableList;
import service.SuperService;

import java.sql.SQLException;

public interface PlaceOrderService extends SuperService {

OrderDetails addToCart(String productId ,Long qty);
BillDetails placeOrder(ObservableList<OrderDetails> list);
boolean checkQtyIsAvailable(String id,Long qty) ;
Double calculateBalance(String amount,String totalAmount);
String generateOrderId();
boolean addOrder(Orders order) throws SQLException;
boolean printOrder(String orderId);
boolean sendEmailOrder();
}
