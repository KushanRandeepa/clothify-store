package service.custom;

import dto.AdminDashboardTables;
import javafx.collections.ObservableList;
import service.SuperService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AdminDashboardService extends SuperService {
    List<String> lowStockAlert();
    Double getTodayTotalSales(LocalDate date);
    Integer getNumberOfProducts(LocalDate date);
    ObservableList<AdminDashboardTables> loadCashierTable(LocalDate date);
    ObservableList<AdminDashboardTables> loadCustomerTable();
    Double getTotalSalesOfTimePeriod(LocalDate date1,LocalDate date2);
    List<Map.Entry<String, Long>> getTopProductsOfTimePeriod(LocalDate date1, LocalDate date2);
}
