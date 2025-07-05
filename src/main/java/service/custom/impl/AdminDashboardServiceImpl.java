package service.custom.impl;

import dto.AdminDashboardTables;
import dto.Customer;
import entity.CustomerEntity;
import entity.OrdersDetailsEntity;
import entity.OrdersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.DaoFactory;
import repository.custom.CustomerRepository;
import repository.custom.OrderDetailRepository;
import repository.custom.OrdersRepository;
import repository.custom.ProductRepository;
import service.BoFactory;
import service.custom.AdminDashboardService;
import service.custom.CustomerService;
import service.custom.UserManagerService;
import util.Repositorytype;
import util.ServiceType;

import java.time.LocalDate;
import java.util.*;

public class AdminDashboardServiceImpl implements AdminDashboardService {
    OrdersRepository ordersRepository = DaoFactory.getInstance().getRepositoryType(Repositorytype.ORDER);
    OrderDetailRepository orderDetailRepository = DaoFactory.getInstance().getRepositoryType(Repositorytype.ORDER_DETAIL);
    ProductRepository productRepository = DaoFactory.getInstance().getRepositoryType(Repositorytype.PRODUCT);
    UserManagerService userManagerService= BoFactory.getInstance().getServiceType(ServiceType.USER);
    CustomerService customerService=BoFactory.getInstance().getServiceType(ServiceType.CUSTOMER);

    @Override
    public List<String> lowStockAlert() {
        return productRepository.checkStockIsLow();
    }

    @Override
    public Double getTodayTotalSales(LocalDate date) {
        double totalSales = 0.0;
        List<OrdersEntity> todayOrders = ordersRepository.getTodayOrders(date);
        if (todayOrders.isEmpty()) {
            return totalSales;
        }
        for (OrdersEntity entity : todayOrders) {
            totalSales += entity.getNetTotalPrice();
        }
        return totalSales;
    }

    @Override
    public Integer getNumberOfProducts(LocalDate date) {
        int count = 0;
        List<OrdersEntity> todayOrders = ordersRepository.getTodayOrders(date);
        if (todayOrders.isEmpty()) {
            return count;
        }
        for (OrdersEntity entity : todayOrders) {
            List<OrdersDetailsEntity> ordersDetailsEntityList = orderDetailRepository.searchOrderDetailsById(entity.getOrderId());
            for (OrdersDetailsEntity ordersDetails : ordersDetailsEntityList) {
                count += ordersDetails.getQty();
            }
        }
        return count;
    }

    @Override
    public ObservableList<AdminDashboardTables> loadCashierTable(LocalDate date) {
        ObservableList<AdminDashboardTables> list = FXCollections.observableArrayList();
        List<OrdersEntity> todayOrders = ordersRepository.getTodayOrders(date);
        todayOrders.forEach(ordersEntity -> {
                    Optional<AdminDashboardTables> existing = list.stream().filter(item -> item.getId().equals(ordersEntity.getCashierId())).findFirst();
                    if (existing.isPresent()){
                        AdminDashboardTables tableRow = existing.get();
                        tableRow.setAmount(ordersEntity.getNetTotalPrice()+tableRow.getAmount());
                        tableRow.setOrders(tableRow.getOrders()+1);
                    }else{
                        String id=ordersEntity.getCashierId();
                        String name=userManagerService.searchUser(id).getUsername();
                        list.add(new AdminDashboardTables(id, name, null, null, ordersEntity.getNetTotalPrice(), 1));
                    }
        }
        );
        return list;
    }

    @Override
    public ObservableList<AdminDashboardTables> loadCustomerTable() {
        ObservableList<AdminDashboardTables> list = FXCollections.observableArrayList();
        ObservableList<String> allCustomerList = customerService.getAllCustomerList();
        allCustomerList.forEach(s -> {
            Customer customer = customerService.searchCustomerById(s);
            list.add(new AdminDashboardTables(s,customer.getName(),customer.getPhoneNumber(),customer.getEmail(),null,1));
        });
        return list;
    }

    @Override
    public Double getTotalSalesOfTimePeriod(LocalDate date1, LocalDate date2) {
        double totalSales = 0.0;
        List<OrdersEntity> ordersBetweenTime = ordersRepository.getOrdersBetweenTime(date1, date2);
        if (ordersBetweenTime.isEmpty()) {
            return totalSales;
        }
        for (OrdersEntity entity : ordersBetweenTime) {
            totalSales += entity.getNetTotalPrice();
        }
        return totalSales;
    }

    @Override
    public List<Map.Entry<String, Long>> getTopProductsOfTimePeriod(LocalDate date1, LocalDate date2) {
        List<OrdersEntity> ordersBetweenTime = ordersRepository.getOrdersBetweenTime(date1, date2);
        TreeMap<String, Long> productCount = new TreeMap<>();

        if (ordersBetweenTime.isEmpty()) {
            return null;
        }

        for (OrdersEntity ordersEntity : ordersBetweenTime) {
            List<OrdersDetailsEntity> ordersDetailsEntityList = orderDetailRepository.searchOrderDetailsById(ordersEntity.getOrderId());
            for (OrdersDetailsEntity ordersDetails : ordersDetailsEntityList) {
                String productId = ordersDetails.getProductId();
                long qty = ordersDetails.getQty();
                productCount.put(productId, productCount.getOrDefault(productId, 0L) + qty);
            }
        }
        List<Map.Entry<String, Long>> sortedList = new ArrayList<>(productCount.entrySet());
        sortedList.sort((o1, o2) -> Long.compare(o2.getValue(), o1.getValue()));
        System.out.println(sortedList);
        return sortedList;
    }
}
