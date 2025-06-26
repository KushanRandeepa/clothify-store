package controller;
import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Setter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;




import java.io.IOException;

import java.net.URL;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AdminFormController implements Initializable {


    public JFXButton btnDashboard;
    public AnchorPane root;
    public JFXButton btnLogOut;
    public ImageView imgChart;
    @Setter
    private String adminId;


    @FXML
        void btnOnActionEmplyees(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("../view/employee_form.fxml");
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
            URL resource = this.getClass().getResource("../view/login_form.fxml");
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::loadDailySalesChart
        );
    }

    private void loadDailySalesChart() {
        try {

            JasperDesign design = JRXmlLoader.load("src/main/resources/report/sales_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), DBConnection.getInstance().getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint,"bill.pdf");
            JasperViewer.viewReport(jasperPrint,false);
        } catch (SQLException | JRException e) {
            throw new RuntimeException(e);
        }

    }

}


