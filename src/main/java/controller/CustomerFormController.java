package controller;

import com.jfoenix.controls.JFXTextField;
import dto.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.checkerframework.checker.units.qual.A;
import service.BoFactory;
import service.custom.CustomerService;
import util.ServiceType;

import java.sql.SQLException;

public class CustomerFormController {
CustomerService customerService= BoFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtNumber;

    @FXML
    private JFXTextField txtName;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        try {
            boolean b = customerService.addCustomer(new Customer(null, txtName.getText(), txtNumber.getText(), txtEmail.getText()));
            if(b){
                new Alert(Alert.AlertType.CONFIRMATION,"customer Added").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"error").show();
        }
    }



}
