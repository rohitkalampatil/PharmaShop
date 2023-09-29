package homePage;

import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class InvoiceHistory implements Initializable {
    Connection connection;
    PreparedStatement preparedStatement;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allInvoice_Display();
    }
    public void allInvoice_Display(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmashop", "root", "root");

            preparedStatement = connection.prepareStatement("");

        } catch (Exception e) {
            System.out.println("Exception : "+e);
        }
    }
}
