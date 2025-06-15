package controller;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminFormController implements Initializable {

    public JFXButton btnDashboard;



    @FXML
        void btnOnActionEmplyees(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/employee_form.fxml"))));
            stage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.hide();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }

        @FXML
        void btnOnActionOrders(ActionEvent event) {
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/order_form.fxml"))));
                stage.show();

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.hide();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @FXML
        void btnOnActionReports(ActionEvent event) {
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/reports_form.fxml"))));
                stage.show();

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.hide();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @FXML
        void btnOnActionSuppliers(ActionEvent event) {
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/supplier_form.fxml"))));
                stage.show();

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.hide();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @FXML
        void btnOnActionUserManager(ActionEvent event) {
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/user_manager_form.fxml"))));
                stage.show();

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.hide();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @FXML
        void btnOnActionproducts(ActionEvent event) {
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/product_form.fxml"))));
                stage.show();

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.hide();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnDashboard.setVisible(false);

    }
}


