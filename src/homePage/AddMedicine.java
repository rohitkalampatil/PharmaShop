package homePage;

import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddMedicine implements Initializable {

    Connection connection;
    PreparedStatement preparedStatement;
    public TextField medicineName;
    public TextField purchaseRate;
    public TextField salingRate;
    public TextField quantity;
    public TextField batchNo;
    public TextField supplierName;
    public DatePicker expiryDate;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver load success");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmashop", "root", "root");
            System.out.println("connected success");
        } catch (Exception e) {
            System.out.println("Exception : "+e);
        }
    }
    public void addButton_Click() throws SQLException {
        try {
            double purchase = Double.parseDouble(purchaseRate.getText());
            double sale = Double.parseDouble(salingRate.getText());
            double batch = Double.parseDouble(batchNo.getText());
            LocalDate date = expiryDate.getValue();

            preparedStatement = connection.prepareStatement("insert into medicine values (?,?,?,?,?,?,?);");
            preparedStatement.setString(1,medicineName.getText());
            preparedStatement.setDouble(2,purchase);
            preparedStatement.setDouble(3,sale);
            preparedStatement.setString(4,quantity.getText());
            preparedStatement.setDouble(5,batch);
            preparedStatement.setString(6,supplierName.getText());
            preparedStatement.setString(7,date.toString());

            int r = preparedStatement.executeUpdate();

            if (r>0){
                System.out.println("added medicine");
                clearAll();
                connection.close();
            }

        }catch (Exception e){
            connection.close();
            System.out.println("added medicine"+e);
        }
    }
    public void clearButton_Click(){
        clearAll();
    }
    private void clearAll(){
        medicineName.clear();
        purchaseRate.clear();
        salingRate.clear();
        quantity.clear();
        batchNo.clear();
        supplierName.clear();
        expiryDate.setValue(null);
    }

}
