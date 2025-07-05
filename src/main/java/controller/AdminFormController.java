package controller;

import com.jfoenix.controls.JFXButton;

import dto.AdminDashboardTables;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import com.jfoenix.controls.JFXListView;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Setter;
import service.BoFactory;
import service.custom.AdminDashboardService;
import service.custom.ProductService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminFormController implements Initializable {

    AdminDashboardService adminDashboardService = BoFactory.getInstance().getServiceType(ServiceType.ADMIN);
    ProductService productService = BoFactory.getInstance().getServiceType(ServiceType.PRODUCT);

    private ObservableList<Node> originalChildren;
    @Setter
    private String adminId;
    @FXML
    private JFXButton btnDashboard;
    @FXML
    private JFXButton btnLogOut;
    @FXML
    private JFXButton btnSearch;
    @FXML
    private TableColumn<?, ?> colAmount;
    @FXML
    private TableColumn<?, ?> colCashierId;
    @FXML
    private TableColumn<?, ?> colCusNumber;
    @FXML
    private TableColumn<?, ?> colCustEmail;
    @FXML
    private TableColumn<?, ?> colCustID;
    @FXML
    private TableColumn<?, ?> colCustName;
    @FXML
    private TableColumn<?, ?> colCustOrders;
    @FXML
    private TableColumn<?, ?> colNoOfOrders;
    @FXML
    private TableColumn<?, ?> colCashierName;
    @FXML
    private DatePicker datePicker1;
    @FXML
    private DatePicker datePicker2;
    @FXML
    private Label lblTodayProducts;
    @FXML
    private Label lblTodaySales;
    @FXML
    private Label lblTotalSales;
    @FXML
    private JFXListView<String> listTopProducts;
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<AdminDashboardTables> tableCashierSales;
    @FXML
    private TableView<AdminDashboardTables> tableCustomer;

    @FXML
    void btnOnActionOrders(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("../view/orders_form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);

    }

    @FXML
    void btnOnActionReports(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("../view/reports_form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }

    @FXML
    void btnOnActionUserManager(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("../view/user_manager_form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }

    @FXML
    void btnOnActionProducts(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("../view/product_form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }

    @FXML
    void btnDashboardOnAction(ActionEvent actionEvent) {
        this.root.getChildren().clear();
        this.root.getChildren().addAll(originalChildren);

    }

    @FXML
    void btnLogOutOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/login_form.fxml"))));
            stage.setResizable(true);
            stage.show();

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.hide();

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        int size = 5;
        listTopProducts.getItems().clear();
        LocalDate start = datePicker2.getValue();
        LocalDate end = datePicker1.getValue();
        if (start == null || end == null) {
            new Alert(Alert.AlertType.INFORMATION, "Please Enter the Date").show();
        }
        Double totalSales = adminDashboardService.getTotalSalesOfTimePeriod(start, end);
        totalSales = Math.round(totalSales * 100) / 100.0;
        lblTotalSales.setText(String.valueOf(totalSales));

        List<Map.Entry<String, Long>> topProductsList = adminDashboardService.getTopProductsOfTimePeriod(start, end);
        if (topProductsList == null) {
            listTopProducts.getItems().addAll("There are no sales on that time period");
            return;
        }
        if (topProductsList.size() < 5) {
            size = topProductsList.size();
        }
        for (int i = 0; i < size; i++) {
            String productId = topProductsList.get(i).getKey();
            listTopProducts.getItems().addAll(i+1+") "+productId+" : "+productService.searchById(productId).getName());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCashierId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCashierName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colNoOfOrders.setCellValueFactory(new PropertyValueFactory<>("orders"));
        colCustID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCusNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        colCustName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCustOrders.setCellValueFactory(new PropertyValueFactory<>("orders"));

        Platform.runLater(() -> {
                    originalChildren = FXCollections.observableArrayList(root.getChildren());
                    lblTodaySales.setText(String.valueOf(adminDashboardService.getTodayTotalSales(LocalDate.now())));
                    lblTodayProducts.setText(String.valueOf(adminDashboardService.getNumberOfProducts(LocalDate.now())));
                    alertLowStock();
                    loadCashierTable();
                    loadCustomerTable();
                }
        );
    }

    private void loadCashierTable() {
        ObservableList<AdminDashboardTables> cashierTable = adminDashboardService.loadCashierTable(LocalDate.now());
        if(!cashierTable.isEmpty()){
            tableCashierSales.setItems(cashierTable);
        }
    }

    private void loadCustomerTable() {
        ObservableList<AdminDashboardTables> customerTable = adminDashboardService.loadCustomerTable();
        if(!customerTable.isEmpty()){
            tableCustomer.setItems(customerTable);
        }
    }

    private void alertLowStock() {
        List<String> productId = adminDashboardService.lowStockAlert();
        if (!productId.isEmpty()) {
            for (String str : productId) {
                System.out.println(str);
            }
            new Alert(Alert.AlertType.WARNING, productId.toString() + " are Low Stock").show();
        }
    }


}





