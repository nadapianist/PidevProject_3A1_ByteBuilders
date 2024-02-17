package tn.esprit.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.Services.LocationService;

import java.io.IOException;
import java.sql.SQLException;

public class DeleteLocation {

    @FXML
    private TextField IdToDelete;
    @FXML
    private Button CloseWindowButton;
    private final LocationService ls=new LocationService();

    public void EraseData(ActionEvent actionEvent) {
        try{
            ls.delete(Integer.parseInt(IdToDelete.getText()));
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("This location is deleted");
            alert.showAndWait();
        }
        catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }

    public void HomePage(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/ShowLocations.fxml"));
        CloseWindowButton.getScene().setRoot(root);
        System.out.println("moved");
    }
}
