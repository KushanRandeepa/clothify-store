package controller;

import com.jfoenix.controls.JFXButton;

import dto.Orders;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Setter;
import service.BoFactory;
import service.custom.CashierService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import java.util.ResourceBundle;

public class CashierFormController implements Initializable {

    CashierService service= BoFactory.getInstance().getServiceType(ServiceType.CASHIER);
    private ObservableList<Node> originalChildren;
    @Setter
    private String cashierId;
    @FXML
    private JFXButton btnDashboard;
    @FXML
    private JFXButton btnPlaceOrder;
    @FXML
    private Button btnSearch;
    @FXML
    private TableColumn<?, ?> colBalance;
    @FXML
    private TableColumn<?, ?> colBalanceTdy;
    @FXML
    private TableColumn<?, ?> colCustId;
    @FXML
    private TableColumn<?, ?> colCustIdTdy;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colDiscount;
    @FXML
    private TableColumn<?, ?> colNetTotal;
    @FXML
    private TableColumn<?, ?> colNetTotalTdy;
    @FXML
    private TableColumn<?, ?> colOrderId;
    @FXML
    private TableColumn<?, ?> colOrderIdTdy;
    @FXML
    private TableColumn<?, ?> colPay;
    @FXML
    private TableColumn<?, ?> colPaymentTdy;
    @FXML
    private TableColumn<?, ?> colTime;
    @FXML
    private TableColumn<?, ?> colTimeTdy;
    @FXML
    private TableColumn<?, ?> colTotal;
    @FXML
    private Label lblId;
    @FXML
    private Label lblSalesToday;
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<Orders> tableOrdersSoFar;
    @FXML
    private TableView<Orders> tableOrdersToday;
    @FXML
    private TextField txtSearch;
    public JFXButton btnLogOut;

    @FXML
    void btnPlaceorderOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/placeOrder_form.fxml"));
        Parent load = loader.load();
        PlaceOrdersFormController controller = loader.getController();
        controller.setCashierId(cashierId);

        this.root.getChildren().clear();
        this.root.getChildren().add(load);

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Platform.runLater(()->{
            lblId.setText(cashierId);

                    colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
                    colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
                    colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
                    colCustId.setCellValueFactory(new PropertyValueFactory<>("cashierId"));
                    colDiscount.setCellValueFactory(new PropertyValueFactory<>("totalDiscountAmount"));
                    colNetTotal.setCellValueFactory(new PropertyValueFactory<>("netTotalPrice"));
                    colPay.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
                    colTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
                    colTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

                    colOrderIdTdy.setCellValueFactory(new PropertyValueFactory<>("orderId"));
                    colBalanceTdy.setCellValueFactory(new PropertyValueFactory<>("balance"));
                    colNetTotalTdy.setCellValueFactory(new PropertyValueFactory<>("netTotalPrice"));
                    colTimeTdy.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
                    colCustIdTdy.setCellValueFactory(new PropertyValueFactory<>("cashierId"));
                    colPaymentTdy.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));

            originalChildren = FXCollections.observableArrayList(root.getChildren());
                     loadTable(cashierId);

         }
        );
    }

   void loadTable(String id){
       tableOrdersSoFar.setItems(service.getAllOrders(id));
       tableOrdersToday.setItems(service.getTodayOrders(id, LocalDate.now()));
        lblSalesToday.setText(String.valueOf(service.todaySales(id, LocalDate.now())));
    }

    public void btndashboardOnAction(ActionEvent actionEvent) throws IOException {
     this.root.getChildren().clear();
     this.root.getChildren().addAll(originalChildren);
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/login_form.fxml"))));
            stage.setResizable(true);
            stage.show();

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.hide();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    }



