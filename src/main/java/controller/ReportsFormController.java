package controller;

import db.DBConnection;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ReportsFormController implements Initializable{


    @FXML
    private ImageView imgblue;

    @FXML
    private ImageView imggreen;

    @FXML
    private ImageView imgred;

    @FXML
    private ImageView imgyellow;

    private void loadDailySalesChart() {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/sales_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), DBConnection.getInstance().getConnection());
            BufferedImage bufferedImage = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, 0, 2.0f);
            WritableImage fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
            imgblue.setImage(fxImage);
        } catch (SQLException | JRException e) {
            new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDailySalesChart();
    }
}
