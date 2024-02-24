package tn.esprit.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.Entity.Location;
import tn.esprit.Services.LocationService;

import java.io.IOException;
import java.sql.SQLException;

public class AddLocation {
    @FXML
    private Button AddButton;
    @FXML
    private TextField ActivityIdField;
    @FXML
    private TextField HostelIDField;
    @FXML
    private TextField NameField;
    @FXML
    private TextField ratingField;
    @FXML
    private Button ReturnButton;
    @FXML
    private TextField categoryField;

    @FXML
    private TextField infoField;
private final LocationService ls=new LocationService();

    public void AddNewLocation(ActionEvent actionEvent) {
        try{
        ls.add(new Location(
                 NameField.getText()
                ,infoField.getText()
                ,categoryField.getText()
                ,Integer.parseInt(ActivityIdField.getText())
                ,Integer.parseInt(HostelIDField.getText())
                ));
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("A location is added");
        alert.showAndWait();
    }
        catch(SQLException e){
        Alert alert= new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(e.getMessage());
        alert.showAndWait();

    }
    }

    public void ToHome(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/ShowLocations.fxml"));
        ReturnButton.getScene().setRoot(root);
        System.out.println("moved");
    }
}
