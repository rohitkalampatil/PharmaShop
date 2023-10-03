package homePage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class InvoiceHistory implements Initializable {
    Connection connection;
    PreparedStatement preparedStatement;
    public TextField searchField;
    public TableView<Invoice_Customer> table= new TableView<>();
    public TableColumn<Invoice_Customer,String> invoice_No;
    public TableColumn<Invoice_Customer,String> customer_Name;
    public TableColumn<Invoice_Customer,String> invoice_Date;
    public TableColumn<Invoice_Customer,String> invoice_Amount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            initializeDatabaseConnection();
            allInvoice_Display();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void initializeDatabaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmashop", "root", "root");
    }

    public void searchButton_Click(){
        try{
            String searchInvoice=searchField.getText();
            int invoiceNo1 = Integer.parseInt(searchInvoice);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from invoice_customer where invoiceNo=?;");
            preparedStatement.setInt(1, invoiceNo1);

            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<Invoice_Customer> invoiceCustomers = FXCollections.observableArrayList();

            if (resultSet.next()){
                invoiceCustomers.add(
                        new Invoice_Customer(
                                resultSet.getInt("invoiceNo"),
                                resultSet.getString("customerName"),
                                resultSet.getString("invoiceDate"),
                                resultSet.getDouble("amount")
                        )
                );
            }
            invoice_No.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
            customer_Name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            invoice_Date.setCellValueFactory(new PropertyValueFactory<>("invoiceDate"));
            invoice_Amount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
            table.setItems(invoiceCustomers);
        }
        catch (Exception e){
            System.out.println("Exception : "+e);
        }
    }
    public void allInvoice_Display(){
        try {
            preparedStatement = connection.prepareStatement("select * from invoice_customer;");
            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<Invoice_Customer> invoiceCustomers = FXCollections.observableArrayList();

            while (resultSet.next()){
                invoiceCustomers.add(
                        new Invoice_Customer(
                                resultSet.getInt("invoiceNo"),
                                resultSet.getString("customerName"),
                                resultSet.getString("invoiceDate"),
                                resultSet.getDouble("amount")
                        )
                );
            }

            invoice_No.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
            customer_Name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            invoice_Date.setCellValueFactory(new PropertyValueFactory<>("invoiceDate"));
            invoice_Amount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
            table.setItems(invoiceCustomers);
        } catch (Exception e) {
            System.out.println("Exception : "+e);
        }
    }

    public static class Invoice_Customer{
        private int invoiceNo;
        private String customerName;
        private String invoiceDate;
        private double totalAmount;

        private Invoice_Customer(int invoiceNo,String customerName,String invoiceDate,double totalAmount){
            this.invoiceNo=invoiceNo;
            this.customerName=customerName;
            this.invoiceDate=invoiceDate;
            this.totalAmount=totalAmount;
        }

        public int getInvoiceNo() {
            return invoiceNo;
        }

        public void setInvoiceNo(int invoiceNo) {
            this.invoiceNo = invoiceNo;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getInvoiceDate() {
            return invoiceDate;
        }

        public void setInvoiceDate(String invoiceDate) {
            this.invoiceDate = invoiceDate;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }
    }
}
