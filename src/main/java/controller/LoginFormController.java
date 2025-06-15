
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.LoginResponse;
import dto.UserLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import service.BoFactory;
import service.custom.AuthService;
import util.ServiceType;

import java.io.IOException;

public class LoginFormController {
AuthService service= BoFactory.getInstance().getServiceType(ServiceType.AUTH);
    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnSignup;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        LoginResponse loginResponse = service.login(new UserLogin(txtUsername.getText(), txtPassword.getText()));
        if (loginResponse == null) {
            new Alert(Alert.AlertType.ERROR, "Username or Password is invalid").show();
            return;
        }

        try {
            Stage stage = new Stage();
            switch (loginResponse.getRole()){
                case ADMIN:
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/admin_dashboard_form.fxml"))));
                    stage.show();break;
                    default:  stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/product_form.fxml"))));
                        stage.show();break;

            }
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.hide();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"LoginFailed").show();
        }

    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/signup_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
