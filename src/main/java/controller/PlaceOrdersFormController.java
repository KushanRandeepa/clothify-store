package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.BillDetails;
import dto.Orders;
import dto.OrderDetails;
import dto.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;
import service.BoFactory;
import service.custom.PlaceOrderService;
import service.custom.ProductService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PlaceOrdersFormController implements Initializable {
    public Label lblCashierId;
    @Setter
    private String cashierId;
    @FXML
    private JFXButton btnAddToCart;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnPlaceOrder;
    @FXML
    private JFXButton btnPrntBill;
    @FXML
    private JFXButton btnSendEmail;
    @FXML
    private TableColumn<?, ?> colCTDiscount;
    @FXML
    private TableColumn<?, ?> colCTId;
    @FXML
    private TableColumn<?, ?> colCTName;
    @FXML
    private TableColumn<?, ?> colCTPrice;
    @FXML
    private TableColumn<?, ?> colCTQty;
    @FXML
    private TableColumn<?, ?> colCTTotalPrice;
    @FXML
    private TableColumn<?, ?> colPTId;
    @FXML
    private TableColumn<?, ?> colPTName;
    @FXML
    private TableColumn<?, ?> colPTStock;
    @FXML
    private Label lblBalance;
    @FXML
    private Label lblNetTotal;
    @FXML
    private Label lblProductId;
    @FXML
    private Label lblProductName;
    @FXML
    private Label lblTotalAmount;
    @FXML
    private Label lblTotalDiscount;
    @FXML
    private TableView<OrderDetails> tableCart;
    @FXML
    private TableView<Product> tableProduct;
    @FXML
    private JFXTextField txtPaymentAmount;
    @FXML
    private TextField txtQty;
    @FXML
    private TextField txtTotal;
    @FXML
    private Label lblOrderId;


    ProductService productService= BoFactory.getInstance().getServiceType(ServiceType.PRODUCT);
    PlaceOrderService orderService =BoFactory.getInstance().getServiceType(ServiceType.PLACE_ORDER);
    ObservableList<OrderDetails> cartTableList;
    Double totalView=0.0;



    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String qtyStr = txtQty.getText().trim(); // Trim to remove whitespace
        String id = lblProductId.getText();
        long qty;

        if (qtyStr.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Enter the Quantity").show();
            return;
        }
        try {
            qty = Long.parseLong(qtyStr);
            if (qty <= 0) {
                new Alert(Alert.AlertType.ERROR, "Quantity must be greater than 0").show();
                return;
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid numeric Quantity").show();
            return;
        }

        try {
            if (orderService.checkQtyIsAvailable(id, qty)) {
                    OrderDetails orderDetail = orderService.addToCart(id, qty);
                    orderDetail.setOrderId(lblOrderId.getText());

                    cartTableList.add(orderDetail);
                    tableCart.setItems(cartTableList);

                    totalView += orderDetail.getTotalPrice();
                    txtTotal.setText(String.format("%.2f", totalView));
                    txtQty.clear();
                    lblProductName.setText("");
                    lblProductId.setText("");
                    btnPlaceOrder.setDisable(false);
            } else {
                new Alert(Alert.AlertType.ERROR, "Requested quantity is not available in stock.").show();
            }
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred: " + ex.getMessage()).show();
        }
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnEditOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        BillDetails cartTable = orderService.placeOrder(cartTableList);
            lblNetTotal.setText(String.valueOf(cartTable.getNetTotalAmount()));
            lblTotalAmount.setText(String.valueOf(cartTable.getTotalAmount()));
            lblTotalDiscount.setText(String.valueOf(cartTable.getTotalDiscount()));
            loadProductTableDetails();
            btnPlaceOrder.setDisable(true);
            btnAddToCart.setDisable(true);

    }

    @FXML
    void btnPrintBillOnAction(ActionEvent event) {
        cartTableList= FXCollections.observableArrayList();
        tableCart.setItems(null);
        resetAllAndNewOrder();
    }

    private void resetAllAndNewOrder() {
        btnAddToCart.setDisable(false);
        btnPlaceOrder.setDisable(true);
        btnPrntBill.setDisable(true);
        btnSendEmail.setDisable(true);
        loadProductTableDetails();
        lblOrderId.setText(orderService.generateOrderId());
        lblBalance.setText("");
        lblNetTotal.setText("");
        lblTotalAmount.setText("");
        lblTotalDiscount.setText("");
        txtPaymentAmount.setText("");
        txtTotal.setText("");
    }

    @FXML
    void btnSendEmailOnAction(ActionEvent event) {

    }

    @FXML
    void calculateBalanceOnAction(ActionEvent event) {
        Double balance = orderService.calculateBalance(txtPaymentAmount.getText(), lblNetTotal.getText());
        lblBalance.setText(String.valueOf(balance));

            addOrder();

        btnSendEmail.setDisable(false);
        btnPrntBill.setDisable(false);
    }

    private void addOrder() {
        try {
            boolean isOrderAdd = orderService.addOrder(new Orders(
                    lblOrderId.getText(),
                    lblCashierId.getText(),
                    "001",
                    null,
                    null,
                    Double.valueOf(lblTotalAmount.getText()),
                    Double.valueOf(lblTotalDiscount.getText()),
                    Double.valueOf(lblNetTotal.getText()),
                    Double.valueOf(txtPaymentAmount.getText()),
                    Double.valueOf(lblBalance.getText()),
                    cartTableList
            ));
            if(isOrderAdd){
                new Alert(Alert.AlertType.CONFIRMATION,"Order is Success!").show();

            }else {
                new Alert(Alert.AlertType.ERROR,"Order Not Save").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,String.valueOf(e)).show();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()-> {
            lblCashierId.setText(cashierId);
            colCTId.setCellValueFactory(new PropertyValueFactory<>("productId"));
            colCTName.setCellValueFactory(new PropertyValueFactory<>("productName"));
            colCTQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
            colCTPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colCTDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
            colCTTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
            colPTId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colPTName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colPTStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

            cartTableList= FXCollections.observableArrayList();

            loadProductTableDetails();


            lblOrderId.setText(orderService.generateOrderId());
            btnPlaceOrder.setDisable(true);
            btnPrntBill.setDisable(true);
            btnSendEmail.setDisable(true);
        });

    }

    void loadProductTableDetails(){
        tableProduct.setItems(productService.getAll());
        tableProduct.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue!=null){
                lblProductId.setText(newValue.getId());
                lblProductName.setText(newValue.getName());
            }
        });
    }



}
