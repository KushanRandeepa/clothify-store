package controller;

import com.jfoenix.controls.JFXListView;
import dto.OrderDetails;
import dto.Orders;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    OrderService orderService= BoFactory.getInstance().getServiceType(ServiceType.ORDER);

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
        if(searchText!=null){
            tableOrderDetails.setItems(orderService.searchByOrderId(searchText));
        }
        listView.getItems().addAll("abhbvhjbj","uyuyguyu");
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

        loadTable();
    }
    void loadTable(){
        tableOrders.setItems(orderService.getAllOrders());
    }

}