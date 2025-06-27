
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import service.BoFactory;
import service.custom.AuthService;
import service.custom.UserManagerService;
import util.ServiceType;

import java.io.IOException;

public class LoginFormController {
    AuthService service = BoFactory.getInstance().getServiceType(ServiceType.AUTH);
    UserManagerService userManagerService= BoFactory.getInstance().getServiceType(ServiceType.USER);

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
            switch (loginResponse.getRole()) {
                case ADMIN:
                    FXMLLoader adminFormLoader = new FXMLLoader(getClass().getResource("../view/admin_dashboard_form.fxml"));
                    Parent adminRoot = adminFormLoader.load();
                    AdminFormController adminFormController=adminFormLoader.getController();
                    adminFormController.setAdminId(loginResponse.getId());
                    userManagerService.updateLoginTime(loginResponse.getId());
                    stage.setScene(new Scene(adminRoot));
                    stage.show();
                    break;
                case CASHIER:
                    FXMLLoader cashierFormLoader = new FXMLLoader(getClass().getResource("../view/cashier_dashboard_form.fxml"));
                    Parent cashierRoot = cashierFormLoader.load();
                    CashierFormController cashierFormController=cashierFormLoader.getController();
                    cashierFormController.setCashierId(loginResponse.getId());
                    userManagerService.updateLoginTime(loginResponse.getId());
                    stage.setScene(new Scene(cashierRoot));
                    stage.show();


                    break;
                default:
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/product_form.fxml"))));
                    stage.show();
                    break;

            }
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.hide();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }

    }

}
