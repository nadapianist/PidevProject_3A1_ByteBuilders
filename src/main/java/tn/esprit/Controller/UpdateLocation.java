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

public class UpdateLocation {
    @FXML
    private Button CancelUpdateButton;

    @FXML
    private Button ConfirmUpdateButton;

    @FXML
    private TextField Idloc;

    @FXML
    private TextField NewInfosId;

    @FXML
    private TextField NewNameId;
    private final LocationService ls=new LocationService();
    public void SaveChanges(ActionEvent actionEvent) {
        try{
            ls.update(Integer.parseInt(Idloc.getText()),NewNameId.getText(),NewInfosId.getText());
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("This location is updated");
            alert.showAndWait();
        }
        catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }


    }

    public void ReturnToList(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/ShowLocations.fxml"));
        CancelUpdateButton.getScene().setRoot(root);
        System.out.println("moved");
    }
}
