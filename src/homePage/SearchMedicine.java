package homePage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SearchMedicine implements Initializable {

    Connection connection;
    PreparedStatement preparedStatement;
    public TextField searchField;
    public TableView<Medicine> table= new TableView<>();
    public TableColumn<Medicine, String> nameColumn;
    public TableColumn<Medicine, String> purchaseRate;
    public TableColumn<Medicine, String> salingPrice;
    public TableColumn<Medicine, String> batchNo;
    public TableColumn<Medicine, String> expiryDate;
    public TableColumn<Medicine, String> supplierName;
    public TableColumn<Medicine, String> quantity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allMedicineStock_Display();
    }
    public void searchButton_Click(){

        try {
            String searchMedicine=searchField.getText();
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmashop", "root", "root");

            preparedStatement = connection.prepareStatement("select * from medicine where name=?");
            preparedStatement.setString(1,searchMedicine);
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<Medicine> medicines = FXCollections.observableArrayList();

            if (resultSet.next()){
                medicines.add(
                        new Medicine(
                                resultSet.getString("name"),
                                resultSet.getDouble("purchaserate"),
                                resultSet.getDouble("salingrate"),
                                resultSet.getDouble("batchno"),
                                resultSet.getString("expiry"),
                                resultSet.getString("suppliername"),
                                resultSet.getInt("quantity")
                        )
                );
            }

            nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameMedicine"));
            purchaseRate.setCellValueFactory(new PropertyValueFactory<>("purchaseRate"));
            salingPrice.setCellValueFactory(new PropertyValueFactory<>("salingRate"));
            batchNo.setCellValueFactory(new PropertyValueFactory<>("batchNo"));
            expiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
            supplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
            quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            table.setItems(medicines);

        } catch (Exception e) {
            System.out.println("Exception : "+e);
        }
    }

    public void stockButton_Click(){
        allMedicineStock_Display();
    }

    public void allMedicineStock_Display(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmashop", "root", "root");

            preparedStatement = connection.prepareStatement("select * from medicine");
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<Medicine> medicines = FXCollections.observableArrayList();

            while (resultSet.next()){
                medicines.add(
                        new Medicine(
                                resultSet.getString("name"),
                                resultSet.getDouble("purchaserate"),
                                resultSet.getDouble("salingrate"),
                                resultSet.getDouble("batchno"),
                                resultSet.getString("expiry"),
                                resultSet.getString("suppliername"),
                                resultSet.getInt("quantity")
                        )
                );
            }

            nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameMedicine"));
            purchaseRate.setCellValueFactory(new PropertyValueFactory<>("purchaseRate"));
            salingPrice.setCellValueFactory(new PropertyValueFactory<>("salingRate"));
            batchNo.setCellValueFactory(new PropertyValueFactory<>("batchNo"));
            expiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
            supplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
            quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            table.setItems(medicines);

        } catch (Exception e) {
            System.out.println("Exception : "+e);
        }
    }
    public static class Medicine{
        private String nameMedicine;
        private double purchaseRate;
        private double salingRate;
        private int quantity;
        private double batchNo;
        private String supplierName;
        private String expiryDate;

        private Medicine(String nameMedicine, double purchaseRate, double salingRate,
                          double batchNo,String expiryDate,String supplierName,int quantity){

            this.nameMedicine = nameMedicine;
            this.purchaseRate = purchaseRate;
            this.salingRate = salingRate;
            this.quantity = quantity;
            this.batchNo = batchNo;
            this.supplierName = supplierName;
            this.expiryDate = expiryDate;
        }
        public double getPurchaseRate() {
            return purchaseRate;
        }
        public void setPurchaseRate(double purchaseRate) {
            this.purchaseRate = purchaseRate;
        }
        public double getSalingRate() {
            return salingRate;
        }
        public void setSalingRate(double salingRate) {
            this.salingRate = salingRate;
        }
        public int getQuantity() {
            return quantity;
        }
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        public double getBatchNo() {
            return batchNo;
        }
        public void setBatchNo(double batchNo) {
            this.batchNo = batchNo;
        }
        public String getSupplierName() {
            return supplierName;
        }
        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }
        public String getExpiryDate() {
            return expiryDate;
        }
        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }
        public String getNameMedicine() {
            return nameMedicine;
        }
        public void setNameMedicine(String nameMedicine) {
            this.nameMedicine = nameMedicine;
        }
    }


}
