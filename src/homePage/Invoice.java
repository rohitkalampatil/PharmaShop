package homePage;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Invoice implements Initializable {

    private Connection connection;
    private double total = 0;
    private int invoiceno;
    private String invoicedate;
    public TableView<Sale> table;
    public TableColumn<Sale, String> nameMedicine, ratePerUnit, quantity, amount;
    public TextField nameInput, priceInput, quantityInput, totleAmount,invoiceNo,nameCustomer;
    public DatePicker date;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initializeDatabaseConnection();
            setupTableColumns();
            totleAmount.setText(String.valueOf(total));
            setupTableListener();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    private void initializeDatabaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmashop", "root", "root");
    }

    private void setupTableColumns() {
        nameMedicine.setCellValueFactory(new PropertyValueFactory<>("nameMedicine"));
        ratePerUnit.setCellValueFactory(new PropertyValueFactory<>("ratePerUnit"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    private void setupTableListener() {
        table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                double productAmount = newValue.getAmount();
            }
        });
    }

    public void addMedicine_Click() {
        try {
            String productName = nameInput.getText();
            double productPrice = Double.parseDouble(priceInput.getText());
            int productQuantity = Integer.parseInt(quantityInput.getText());
            double productAmount = productPrice * productQuantity;

            total += productAmount;

            Sale product = new Sale(productName, productPrice, productQuantity, productAmount);
            table.getItems().add(product);

            totleAmount.setText(String.valueOf(total));

            // Clear input fields
            nameInput.clear();
            priceInput.clear();
            quantityInput.clear();
        } catch (NumberFormatException e) {
            // Handle invalid input (e.g., non-numeric values)
        }
    }

    public void deleteMedicine_Click() {
        ObservableList<Sale> productSelected = table.getSelectionModel().getSelectedItems();
        if (!productSelected.isEmpty()) {
            Sale selectedProduct = productSelected.get(0);
            total -= selectedProduct.getAmount();
            table.getItems().remove(selectedProduct);
            totleAmount.setText(String.valueOf(total));
        }
    }

    public void saveInvoice_Click() {
        // Implement the logic to save the invoice data to the database
        LocalDate invoiceDate = date.getValue();

        invoicedate=invoiceDate.toString();

        String customername = nameCustomer.getText();

        invoiceno=Integer.parseInt(invoiceNo.getText());

        try {
            // Use PreparedStatement to insert data into the database
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO invoice (productName, productPrice, productQuantity, totalAmount,invoiceDate,invoiceNo) VALUES (?, ?, ?, ?, ?, ?);"
            );
            PreparedStatement statement=connection.prepareStatement(
                    "insert into invoice_customer values(?,?,?,?);"
            );

            for (Sale sale : table.getItems()) {
                preparedStatement.setString(1, sale.getNameMedicine());
                preparedStatement.setDouble(2, sale.getRatePerUnit());
                preparedStatement.setInt(3, sale.getQuantity());
                preparedStatement.setDouble(4, sale.getAmount());
                preparedStatement.setString(5, invoicedate);
                preparedStatement.setInt(6, invoiceno);
                statement.setDouble(4, sale.getAmount());

                preparedStatement.executeUpdate();
            }

            statement.setInt(1,invoiceno);
            statement.setString(2,customername);
            statement.setString(3, invoicedate);
            statement.executeUpdate();

            table.setItems(null);
            total = 0;
            nameCustomer.clear();
            invoiceNo.clear();
            date=null;
            totleAmount.setText(String.valueOf(total));
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database errors
        }
    }

    public static class Sale {
        private String nameMedicine;
        private double ratePerUnit;
        private int quantity;
        private double amount;

        public Sale(String nameMedicine, double ratePerUnit, int quantity, double amount) {
            this.nameMedicine = nameMedicine;
            this.ratePerUnit = ratePerUnit;
            this.quantity = quantity;
            this.amount = amount;

        }

        public String getNameMedicine() {
            return nameMedicine;
        }

        public void setNameMedicine(String nameMedicine) {
            this.nameMedicine = nameMedicine;
        }

        public double getRatePerUnit() {
            return ratePerUnit;
        }

        public void setRatePerUnit(double ratePerUnit) {
            this.ratePerUnit = ratePerUnit;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

    }
}
