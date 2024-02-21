package tn.esprit.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class NavMenu {
    @FXML
    private Button ManageTransportButton;
    @FXML
    private Button ManageLocationsButton;

    public void ManageLocations(ActionEvent actionEvent) throws IOException {


    }

    public void ManageTransport(ActionEvent actionEvent) throws IOException {

    }

    public void ToTransportPage(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/ShowTransports.fxml"));
        ManageTransportButton.getScene().setRoot(root);
        System.out.println("moved");
    }

    public void ToLocationPage(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/ShowLocations.fxml"));
        ManageLocationsButton.getScene().setRoot(root);
        System.out.println("moved");
    }
}
