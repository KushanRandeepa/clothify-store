package service.custom;

import dto.CartTable;
import javafx.collections.ObservableList;
import service.SuperService;

public interface PlaceOrderService extends SuperService {

CartTable addToCart(String id,Long qty);
CartTable placeOrder(ObservableList<CartTable> list);
boolean checkQtyIsAvailable(String id,Long qty) ;
Double calculateBalance(String amount,String totalAmount);
}
