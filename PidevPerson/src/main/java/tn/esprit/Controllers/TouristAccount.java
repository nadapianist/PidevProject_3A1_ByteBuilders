package tn.esprit.Controllers;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entities.Tourist;
import tn.esprit.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import tn.esprit.Controllers.*;
import java.sql.SQLException;
import java.util.List;

public class TouristAccount {

    @FXML
    private TextField txtbio;

    @FXML
    private TextField txtlname;

    @FXML
    private TextField txtmail;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtphone;

    @FXML
    private TextField txtpref;

    @FXML
    private TextField txtpwd;



    private final UserService ser=new UserService();

    @FXML
    public void initialize() {
        refreshPage();
    }

    private void refreshPage() {
        Tourist connectedTourist = Login.getConnectedTourist();
        if (connectedTourist != null) {
            System.out.println("Connected Tourist Data: " + connectedTourist.toString());

            txtmail.setText(connectedTourist.getEmail());
            txtpwd.setText(connectedTourist.getPwd());
            txtname.setText(connectedTourist.getFname());
            txtlname.setText(connectedTourist.getLname());
            int phone = connectedTourist.getPhone();
            txtphone.setText(String.valueOf(phone));
            txtbio.setText(connectedTourist.getBio());
            txtpref.setText(connectedTourist.getPreferences());
        } else {
            System.out.println("Connected Tourist is null.");
        }
    }

    @FXML
    public void modify(ActionEvent actionEvent) {
        Tourist connectedTourist = Login.getConnectedTourist();
        if (connectedTourist != null) {
            String newEmail = txtmail.getText();
            String newpwd = txtpwd.getText();
            String newNom = txtname.getText();
            String newPrenom = txtlname.getText();
            int newNum = Integer.parseInt(txtphone.getText());
            String newBio = txtbio.getText();
            String newPref = txtpref.getText();

            connectedTourist.setEmail(newEmail);
            connectedTourist.setPwd(newpwd);
            connectedTourist.setFname(newNom);
            connectedTourist.setLname(newPrenom);
            connectedTourist.setPhone(newNum);
            connectedTourist.setBio(newBio);
            connectedTourist.setPreferences(newPref);

            try {
                ser.update(connectedTourist);
                showSuccessDialog("Your account has been modified successfully");

                // Refresh the page to display the updated data
                refreshPage();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void showSuccessDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
