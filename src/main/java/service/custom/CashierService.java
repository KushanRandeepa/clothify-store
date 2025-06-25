package service.custom;

import dto.Orders;
import javafx.collections.ObservableList;
import service.SuperService;

import java.time.LocalDate;

public interface CashierService extends SuperService{
    ObservableList<Orders >getAllOrders(String id);
    ObservableList<Orders>getTodayOrders(String id, LocalDate date);
    Double todaySales(String id,LocalDate date);
}
