package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import service.BoFactory;
import service.custom.AuthService;
import util.ServiceType;
import util.UserRoles;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignupFormController implements Initializable {
    AuthService service=BoFactory.getInstance().getServiceType(ServiceType.AUTH);

    @FXML
    private JFXButton btnSignup;

    @FXML
    private JFXComboBox<UserRoles> comboRole;

    @FXML
    private JFXPasswordField txtConfpassword;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtPhoneNumber;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    void btnSignupOnAction(ActionEvent event) {
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String phoneNumber = txtPhoneNumber.getText();
        String password = txtPassword.getText();
        String confPassword = txtConfpassword.getText();
        UserRoles role=comboRole.getValue();

        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || confPassword.isEmpty() || role==null){
            new Alert(Alert.AlertType.ERROR,"Fill all Fields").show();
            return;
        }
        if(!password.equals(confPassword)){
            new Alert(Alert.AlertType.ERROR,"Password is wrong").show();
            return;
        }

        boolean isSignup = false;
        try {
            isSignup = service.signup(new User(username, password, email, phoneNumber, role));
            
            if(isSignup){
                new Alert(Alert.AlertType.CONFIRMATION,"You are Signup as a "+role+" Successfully").show();
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.hide();
            }else {
                new Alert(Alert.AlertType.ERROR,"Signup Fail!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<UserRoles> roles = FXCollections.observableArrayList();
        roles.add(UserRoles.ADMIN);
        roles.add(UserRoles.CASHIER);
        comboRole.setItems(roles);
    }
}
