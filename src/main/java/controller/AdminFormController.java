package controller;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import lombok.Setter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminFormController implements Initializable {


    public JFXButton btnDashboard;
    public AnchorPane root;
    @Setter
    private String adminId;


    @FXML
        void btnOnActionEmplyees(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("../view/user_manager_form.fxml");
        assert resource!=null;
        Parent load = FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
        }

        @FXML
        void btnOnActionOrders(ActionEvent event) throws IOException {
            URL resource = this.getClass().getResource("../view/orders_form.fxml");
            assert resource!=null;
            Parent load = FXMLLoader.load(resource);
            this.root.getChildren().clear();
            this.root.getChildren().add(load);

        }

        @FXML
        void btnOnActionReports(ActionEvent event) throws IOException {
            URL resource = this.getClass().getResource("../view/user_manager_form.fxml");
            assert resource!=null;
            Parent load = FXMLLoader.load(resource);
            this.root.getChildren().clear();
            this.root.getChildren().add(load);
        }

        @FXML
        void btnOnActionSuppliers(ActionEvent event) throws IOException {
            URL resource = this.getClass().getResource("../view/user_manager_form.fxml");
            assert resource!=null;
            Parent load = FXMLLoader.load(resource);
            this.root.getChildren().clear();
            this.root.getChildren().add(load);
        }

        @FXML
        void btnOnActionUserManager(ActionEvent event) throws IOException {
            URL resource = this.getClass().getResource("../view/user_manager_form.fxml");
            assert resource!=null;
            Parent load = FXMLLoader.load(resource);
            this.root.getChildren().clear();
            this.root.getChildren().add(load);
        }

        @FXML
        void btnOnActionproducts(ActionEvent event) throws IOException {
            URL resource = this.getClass().getResource("../view/product_form.fxml");
            assert resource!=null;
            Parent load = FXMLLoader.load(resource);
            this.root.getChildren().clear();
            this.root.getChildren().add(load);
            System.out.println(adminId);
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{

                }
        );
    }
}


