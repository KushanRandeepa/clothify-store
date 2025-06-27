package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Setter;
import service.BoFactory;
import service.custom.CustomerService;
import service.custom.PlaceOrderService;
import service.custom.ProductService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PlaceOrdersFormController implements Initializable {
    @FXML
    private Label lblx;
    @FXML
    private Label lble;
    @FXML
    private Label lblCashierId;
    @FXML
    private JFXButton btnContinue;
    @FXML
    private Label lblDiscount;
    @FXML
    private JFXButton btnRegister;
    @FXML
    private JFXComboBox<String> cmbCustId;
    @FXML
    private JFXButton searchCustomer;
    @FXML
    private TextField txtsearchCustomer;
    @FXML
    private Label lblCustId;
    @FXML
    private Label lblCustName;
    @FXML
    private Label lblCustEmail;
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


    ProductService productService = BoFactory.getInstance().getServiceType(ServiceType.PRODUCT);
    PlaceOrderService orderService = BoFactory.getInstance().getServiceType(ServiceType.PLACE_ORDER);
    CustomerService customerService=BoFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
    ObservableList<OrderDetails> cartTableList;
    Double totalView = 0.0;


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
        cartTableList = FXCollections.observableArrayList();
        tableCart.setItems(null);
        resetAllAndNewOrder();
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

    @FXML
    void btnContinue(ActionEvent actionEvent) {
        lblCustId.setText("GUEST");
        lblCustName.setVisible(false);
        lblCustEmail.setVisible(false);
        lblx.setVisible(false);
        lble.setVisible(false);
        btnPlaceOrder.setDisable(false);
    }

    @FXML
    void btnRegister(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/add_customer_form.fxml"))));
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }

    @FXML
    void comBoxReloadOnAction(ActionEvent actionEvent) {
        loadCustomers();
    }

    @FXML
    void btnSearchCustomer(ActionEvent event) {
        String text = txtsearchCustomer.getText();
        if(text==null){
            new Alert(Alert.AlertType.ERROR,"enter the phone Number").show();
        }
        Customer customer = customerService.searchCustomerByNumber(text);
        lblCustEmail.setText(customer.getEmail());
        lblCustId.setText(customer.getId());
        lblCustName.setText(customer.getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
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
            cartTableList = FXCollections.observableArrayList();
            lblOrderId.setText(orderService.generateOrderId());
            btnPlaceOrder.setDisable(true);
            btnPrntBill.setDisable(true);
            btnSendEmail.setDisable(true);
            loadProductTableDetails();
            loadCustomers();

            cmbCustId.getSelectionModel().selectedItemProperty().addListener((observableValue, oldvalue, newValue) ->{
                getCustomerDetails(newValue);
            } );
        });

    }

    private void getCustomerDetails(String newValue) {
        Customer customer = customerService.searchCustomerById(newValue);
        lblCustId.setText(newValue);
        lblCustName.setText(customer.getName());
        lblCustEmail.setText(customer.getEmail());
    }

    private void loadCustomers() {
        cmbCustId.setItems(customerService.getAllCustomerList());
    }

    void loadProductTableDetails() {
        tableProduct.setItems(productService.getAll());
        tableProduct.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                lblProductId.setText(newValue.getId());
                lblProductName.setText(newValue.getName());
                lblDiscount.setText(String.valueOf(newValue.getDiscount()));
            }
        });
    }

    private void addOrder() {
        try {
            boolean isOrderAdd = orderService.addOrder(new Orders(
                    lblOrderId.getText(),
                    lblCashierId.getText(),
                    lblCustId.getText(),
                    null,
                    null,
                    Double.valueOf(lblTotalAmount.getText()),
                    Double.valueOf(lblTotalDiscount.getText()),
                    Double.valueOf(lblNetTotal.getText()),
                    Double.valueOf(txtPaymentAmount.getText()),
                    Double.valueOf(lblBalance.getText()),
                    cartTableList
            ));
            if (isOrderAdd) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order is Success!").show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Save").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();

        }
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
}
