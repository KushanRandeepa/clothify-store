package controller;

import com.jfoenix.controls.JFXListView;
import dto.OrderDetails;
import dto.Orders;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.BoFactory;
import service.custom.OrderService;
import util.ServiceType;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class OrdersFormController implements Initializable {

    OrderService orderService = BoFactory.getInstance().getServiceType(ServiceType.ORDER);

    @FXML
    private TableColumn<?, ?> colCashierId;
    @FXML
    private TableColumn<?, ?> colBalance;
    @FXML
    private TableColumn<?, ?> colCustId;
    @FXML
    private TableColumn<?, ?> colDiscount;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colOrderId;
    @FXML
    private TableColumn<?, ?> colPaymentAmount;
    @FXML
    private TableColumn<?, ?> colPrice;
    @FXML
    private TableColumn<?, ?> colProductId;
    @FXML
    private TableColumn<?, ?> colQty;
    @FXML
    private TableColumn<?, ?> colTotalAmount;
    @FXML
    private TableColumn<?, ?> colTotalDiscount;
    @FXML
    private TableColumn<?, ?> coldate;
    @FXML
    private TableColumn<?, ?> colnetTotalAmount;
    @FXML
    private TableColumn<?, ?> coltime;
    @FXML
    private TableColumn<?, ?> coltotalPrice;
    @FXML
    private JFXListView<String> listView;
    @FXML
    private TableView<OrderDetails> tableOrderDetails;
    @FXML
    private TableView<Orders> tableOrders;
    @FXML
    private TextField txtSearch;

    @FXML
    void btnSearchByIDOnAction(ActionEvent event) {
        String searchText = txtSearch.getText();
        if (searchText == null) {
            new Alert(Alert.AlertType.ERROR, "enter ID").show();
        }
        tableOrderDetails.setItems(orderService.searchByOrderId(searchText));
        Orders order = orderService.getOrder(searchText);
        if (order != null) {
            loadList(order);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCashierId.setCellValueFactory(new PropertyValueFactory<>("cashierId"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colTotalDiscount.setCellValueFactory(new PropertyValueFactory<>("totalDiscountAmount"));
        colnetTotalAmount.setCellValueFactory(new PropertyValueFactory<>("netTotalPrice"));
        colPaymentAmount.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        coltotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        tableOrders.getSelectionModel().selectedItemProperty().addListener((observableValue, oldvalue, newValue) -> {
            tableOrderDetails.setItems(orderService.searchByOrderId(newValue.getOrderId()));
            loadList(newValue);
        });
        loadTable();
    }

    void loadTable() {
        tableOrders.setItems(orderService.getAllOrders());
    }

    void loadList(Orders order) {
        listView.getItems().clear();
        listView.getItems().addAll(
                "Order ID        =" + order.getOrderId(),
                    "Cashier Id      =" + order.getCashierId(),
                    "Customer Id     =" + order.getCustomerId(),
                    "OrderDate       =" + order.getOrderDate(),
                    "OrderTime       =" + order.getOrderTime(),
                    "Total Price     =" + order.getTotalPrice(),
                    "Total Discount  =" + order.getTotalDiscountAmount(),
                    "Net Total Price =" + order.getNetTotalPrice(),
                    "Payment Amount  =" + order.getPaymentAmount(),
                    "Balance         =" + order.getBalance()
        );
    }

}