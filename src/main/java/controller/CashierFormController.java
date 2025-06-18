package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CartTable;
import dto.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.BoFactory;
import service.custom.PlaceOrderService;
import service.custom.ProductService;
import util.ServiceType;

import java.net.URL;
import java.util.ResourceBundle;

public class CashierFormController implements Initializable {

    ProductService productService= BoFactory.getInstance().getServiceType(ServiceType.PRODUCT);
    PlaceOrderService placeOrderService=BoFactory.getInstance().getServiceType(ServiceType.PLACE_ORDER);
    ObservableList<CartTable> cartTableList;
    Double totalView=0.0;
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
    private TableView<CartTable> tableCart;
    @FXML
    private TableView<Product> tableProduct;
    @FXML
    private JFXTextField txtCashierID;
    @FXML
    private JFXTextField txtPaymentAmount;
    @FXML
    public TextField txtQty;
    @FXML
    public TextField txtTotal;

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
            if (placeOrderService.checkQtyIsAvailable(id, qty)) {
                CartTable cartTable = placeOrderService.addToCart(id, qty);
                cartTableList.add(cartTable);
                totalView += cartTable.getNetTotalAmount();
                tableCart.setItems(cartTableList);
                txtTotal.setText(String.format("%.2f", totalView)); // format to 2 decimal places
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
        CartTable cartTable = placeOrderService.placeOrder(cartTableList);
            lblNetTotal.setText(String.valueOf(cartTable.getNetTotalAmount()));
            lblTotalAmount.setText(String.valueOf(cartTable.getTotalAmount()));
            lblTotalDiscount.setText(String.valueOf(cartTable.getTotalDiscount()));
            loadProductTableDetails();
            btnPlaceOrder.setDisable(true);
            btnAddToCart.setDisable(true);

    }

    @FXML
    void btnPrintBillOnAction(ActionEvent event) {

    }

    @FXML
    void btnSendEmailOnAction(ActionEvent event) {

    }

    @FXML
    void calculateBalanceOnAction(ActionEvent event) {
        Double balance = placeOrderService.calculateBalance(txtPaymentAmount.getText(), lblNetTotal.getText());
        lblBalance.setText(String.valueOf(balance));

        btnSendEmail.setDisable(false);
        btnPrntBill.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCTId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCTName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCTQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colCTPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCTDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colCTTotalPrice.setCellValueFactory(new PropertyValueFactory<>("netTotalAmount"));

        colPTId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPTName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPTStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        loadProductTableDetails();
        loadCartTable();

        btnPlaceOrder.setDisable(true);
        btnPrntBill.setDisable(true);
        btnSendEmail.setDisable(true);

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

    public  void loadCartTable(){
        cartTableList= FXCollections.observableArrayList();
    }

    public void restDashboard(){
        txtQty.clear();
        lblProductId.setText("");
        lblProductName.setText("");

        btnPlaceOrder.setDisable(true);
        btnPrntBill.setDisable(true);
        btnSendEmail.setDisable(true);
    }
}
