package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.User;
import entity.UserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.BoFactory;
import service.custom.UserManagerService;
import util.ServiceType;
import util.UserRoles;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.AbstractList;
import java.util.ResourceBundle;

public class UserManagerFormController implements Initializable {


    public TextField txtSearch;
    public JFXComboBox<UserRoles> combRole;
    UserManagerService userManagerService= BoFactory.getInstance().getServiceType(ServiceType.USER);
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
    private TableView<User> tableUsers;
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
        String id=txtId.getText();
        if(id!=null) {
            boolean b = userManagerService.deleteUser(id);
            if (b) {
                new Alert(Alert.AlertType.CONFIRMATION, "Delete Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Not Delete").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Please Select User ID ").show();
        }
    }



    @FXML
    void btnSearchOnaction(ActionEvent event) {
        User user = userManagerService.searchUser(txtSearch.getText());
        if(user!=null) {
            txtId.setText(user.getId());
            txtEmail.setText(user.getEmail());
            txtNumber.setText(user.getPhoneNumber());
            txtUsername.setText(user.getUsername());
            txtCreatedAt.setText(String.valueOf(user.getCreatedAt()));
            txtLastLoginTime.setText(String.valueOf(user.getLastLoginAt()));
            combRole.setValue(user.getRole());
        }else {
            new Alert(Alert.AlertType.ERROR,"User not available").show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String username = txtUsername.getText();
        UserRoles roles =  combRole.getValue();
        String email = txtEmail.getText();
        String phoneNumber = txtNumber.getText();
        if(id.isEmpty() || username.isEmpty()|| email.isEmpty()|| phoneNumber.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Fill the Text Feilds").show();
        }else {
            boolean b = userManagerService.updateUser(new User(id, username, email, phoneNumber, roles,null,null));
            if (b){
                new Alert(Alert.AlertType.CONFIRMATION,"Update SuccessFully").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Not Updated").show();
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        coluserRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colLastLoginTime.setCellValueFactory(new PropertyValueFactory<>("lastLoginAt"));
        loadTable();
        txtId.setDisable(true);
        txtCreatedAt.setDisable(true);
        txtLastLoginTime.setDisable(true);

        ObservableList<UserRoles> roles = FXCollections.observableArrayList();
        roles.add(UserRoles.ADMIN);
        roles.add(UserRoles.CASHIER);
        combRole.setItems(roles);

        tableUsers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            if (newValue != null) {
                addValueToText(newValue);
            }
        });
    }

    private void addValueToText(User newValue) {
        txtId.setText(newValue.getId());
        txtUsername.setText(newValue.getUsername());
        txtEmail.setText(newValue.getEmail());
        txtNumber.setText(newValue.getPhoneNumber());
        txtLastLoginTime.setText(String.valueOf(newValue.getLastLoginAt()));
        txtCreatedAt.setText(String.valueOf(newValue.getCreatedAt()));
        combRole.setValue(newValue.getRole());
    }

    private void loadTable() {
        tableUsers.setItems(userManagerService.getAllUsers());

    }
}
