package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.AbstractList;

public class UserManagerFormController {

    @FXML
    private JFXButton btnAddUser;

    @FXML
    private JFXButton btnDeleteUser;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdateUser;

    @FXML
    private JFXButton btnUserManager;

    @FXML
    private TableColumn<?, ?> colCreatedAt;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLastLoginTime;

    @FXML
    private TableColumn<?, ?> colPhoneNumber;

    @FXML
    private TableColumn<?, ?> colUsername;

    @FXML
    private TableColumn<?, ?> coluserRole;

    @FXML
    private JFXTextField txtCreatedAt;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtLastLoginTime;

    @FXML
    private JFXTextField txtNumber;

    @FXML
    private JFXTextField txtRole;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    void btnAddUserOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/signup_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Signup Fail").show();
        }
    }


    @FXML
    void btnDeleteUserOnAction(ActionEvent event) {

    }

    @FXML
    void btnOnActionEmplyees(ActionEvent event) {

    }

    @FXML
    void btnOnActionOrders(ActionEvent event) {

    }

    @FXML
    void btnOnActionProducts(ActionEvent event) {

    }

    @FXML
    void btnOnActionReports(ActionEvent event) {

    }

    @FXML
    void btnOnActionSuppliers(ActionEvent event) {

    }

    @FXML
    void btnOnActionUserManager(ActionEvent event) {

    }

    @FXML
    void btnOnActionproducts(ActionEvent event) {

    }

    @FXML
    void btnSearchOnaction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}
