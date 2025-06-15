


package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.BoFactory;
import service.custom.ProductService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductFormController implements Initializable {

    ProductService service = BoFactory.getInstance().getServiceType(ServiceType.PRODUCT);

    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnProducts;
    @FXML
    private JFXButton btnReload;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private TableColumn<?, ?> colCategory;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colPrice;
    @FXML
    private TableColumn<?, ?> colSize;
    @FXML
    private TableColumn<?, ?> colStock;
    @FXML
    private ComboBox<String> comboxCategory;
    @FXML
    private TableView<Product> tableProduct;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtPrice;
    @FXML
    private TextField txtSearch;
    @FXML
    private JFXTextField txtStock;
    public JFXComboBox<String> comBoxSize;

    @FXML
    void btnOnActionDashboard(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/admin_dashboard_form.fxml"))));
            stage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.hide();

        } catch (IOException e) {
           new Alert(Alert.AlertType.ERROR,"Load error").show();
        }
    }

    @FXML
    void btnOnActionEmplyees(ActionEvent event) {
    }
    @FXML
    void btnOnActionOrders(ActionEvent event) {
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
    void onActionReload(ActionEvent event) {
        loadTable();
        clearTextFields();
    }

    @FXML
    void btnOnActionSearch(ActionEvent event) {
        Product product = service.searchById(txtSearch.getText());
        if (product==null){
            new Alert(Alert.AlertType.ERROR,"No").show();
        }else {
            addValueToText(product);
        }
    }

    @FXML
    void btnOnActionAdd(ActionEvent event) {
        Product product = getValuesFromTexts();
        if(product==null) return;

        try {
            boolean b = service.addProduct(product);
            if (b) {
                new Alert(Alert.AlertType.CONFIRMATION, "Product Add Successfully.").show();
                loadTable();

            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add product").show();

            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "database error").show();
        }

    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {
        Product product = getValuesFromTexts();
        if(product==null) return;
        boolean isUpdate = service.updateProduct(product);
        if (isUpdate){
            new Alert(Alert.AlertType.CONFIRMATION,"Product is Updated").show();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR,"not Updated").show();
        }

    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {
        Product product = getValuesFromTexts();
        if(product!=null){
            boolean isDeleted = service.deleteProduct(product.getId());
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, product.getId()+" This Product Is Deleted!").show();
                loadTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"not delete").show();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnProducts.setDisable(true);
        txtId.setDisable(true);
        loadComboBoxValues();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

tableProduct.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
    if (newValue != null) {
        addValueToText(newValue);
    }
});
        loadTable();
    }

    private void addValueToText(Product newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtStock.setText(String.valueOf(newValue.getStock()));
        txtPrice.setText(String.valueOf(newValue.getPrice()));
        comboxCategory.setValue(newValue.getCategory());
        comBoxSize.setValue(newValue.getSize());
    }

    void loadComboBoxValues(){
        ObservableList<String> sizeList = FXCollections.observableArrayList();
        sizeList.add("XS");
        sizeList.add("S");
        sizeList.add("M");
        sizeList.add("L");
        sizeList.add("XL");
        sizeList.add("2XL");
        sizeList.add("3XL");
        comBoxSize.setItems(sizeList);

        ObservableList<String> categoryList = FXCollections.observableArrayList();
        categoryList.add("Gents");
        categoryList.add("Ladies");
        categoryList.add("Kids");
        categoryList.add("Infants ");
        categoryList.add("Sportswear");
        categoryList.add("Unisex");
        comboxCategory.setItems(categoryList);

    }
    void loadTable(){
        tableProduct.setItems(service.getAll());
    }
    Product getValuesFromTexts(){

        String id = txtId.getText();
        String name = txtName.getText();
        String priceStr = txtPrice.getText();
        String size = comBoxSize.getValue();
        String stockStr = txtStock.getText();
        String category = String.valueOf(comboxCategory.getValue());

        if (id.isEmpty() || name.isEmpty() || priceStr.isEmpty() || size.isEmpty() || category.isEmpty() || stockStr.isEmpty() ) {
            new Alert(Alert.AlertType.WARNING, "Please fill in all fields.").show();
            return null;
        }
        try {
           return  new Product(id, name, category, size, Long.parseLong(stockStr), Double.parseDouble(priceStr));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid  format. Please enter again.").show();
        }
        return null ;
    }
    void clearTextFields(){
        txtId.setText("");
        txtPrice.setText("");
        txtStock.setText("");
        txtName.setText("");
        comboxCategory.setValue("");
        comBoxSize.setValue("");
    }

}
