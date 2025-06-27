package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Setter;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminFormController implements Initializable {

    private ObservableList<Node> originalChildren;

    public JFXButton btnDashboard;
    public AnchorPane root;
    public JFXButton btnLogOut;
    public ImageView imgChart;
    @Setter
    private String adminId;


    @FXML
    void btnOnActionEmplyees(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("../view/employee_form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
                    originalChildren = FXCollections.observableArrayList(root.getChildren());
                }
        );
    }

    }





