package service.custom.impl;

import db.DBConnection;
import dto.BillDetails;
import dto.Orders;
import dto.OrderDetails;
import dto.Product;
import entity.OrdersDetailsEntity;
import entity.OrdersEntity;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.OrderDetailRepository;
import repository.custom.OrdersRepository;
import repository.custom.ProductRepository;
import service.BoFactory;
import service.custom.OrderService;
import service.custom.ProductService;
import util.Repositorytype;
import util.ServiceType;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    ProductService productService= BoFactory.getInstance().getServiceType(ServiceType.PRODUCT);
    OrdersRepository ordersRepository=DaoFactory.getInstance().getRepositoryType(Repositorytype.ORDER);

    @Override
    public OrderDetails addToCart(String productId  ,Long qty) {
        Product product = productService.searchById(productId);
        double totalAmount=product.getPrice()*qty;
        double discount= totalAmount * (product.getDiscount()/100);
        return new OrderDetails(productId, product.getName(), qty, product.getPrice(),  (totalAmount-discount),product.getDiscount());
    }

    @Override
    public BillDetails placeOrder(ObservableList<OrderDetails> list) {
        double netTotalAmountofBill = 0.0;
        double totalAmountOfBill = 0.0;
        double totalDiscount=0.0;
        for (OrderDetails details : list) {
            double totalAmount =Math.floor((details.getPrice()* details.getQty())*100)/100;
            double discount = Math.floor((totalAmount * (details.getDiscount() / 100))*100)/100;
            netTotalAmountofBill += totalAmount - discount;
            totalAmountOfBill += totalAmount;
            totalDiscount += discount;
        }
        return new BillDetails(totalAmountOfBill, totalDiscount, netTotalAmountofBill);

    }

    @Override
    public boolean checkQtyIsAvailable(String id,Long qty) {
        Product product = productService.searchById(id);
        return product.getStock() >= qty;
    }

    @Override
    public Double calculateBalance(String amount,String netTotalAmount) {
        double balance = (Double.parseDouble(amount)) - (Double.parseDouble(netTotalAmount));
        return Math.floor(balance*100)/100.0;

    }

    @Override
    public String generateOrderId() {
        String lastOrderId = ordersRepository.findLastOrderId();
        if(lastOrderId!=null){
            int number = Integer.parseInt(lastOrderId.replaceAll("[^0-9]", ""));
            number++;
            return String.format("OR%03d",number);
        }else {
            return "OR001";
        }
    }

    @Override
    public boolean addOrder(Orders order) throws SQLException {
        OrdersEntity ordersEntity = new ModelMapper().map(order, OrdersEntity.class);
        ordersEntity.setOrderDate(LocalDate.now());
        ordersEntity.setOrderTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
        return ordersRepository.add(ordersEntity);
    }

    @Override
    public boolean addOrderDetails(OrderDetails ordersDetails) {
return false;
    }

    @Override
    public boolean printOrder(String orderId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("orderId", orderId);
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/ClothfyStoreBill.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConnection.getInstance().getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint,"bill.pdf");
            JasperViewer.viewReport(jasperPrint,false);
            return true;
        } catch (JRException | SQLException e) {
            return false;
        }

    }

    @Override
    public boolean sendEmailOrder() {
        return false;
    }


}
