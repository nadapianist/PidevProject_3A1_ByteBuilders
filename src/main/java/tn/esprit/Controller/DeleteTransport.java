package tn.esprit.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.Services.TransportService;

import java.io.IOException;
import java.sql.SQLException;

public class DeleteTransport {
    @FXML
    private Button BackToHomePage;

    @FXML
    private Button DeleteButton;

    @FXML
    private TextField IDTodelete;
    private final TransportService ts=new TransportService();
    public void DeleteData(ActionEvent actionEvent) {
        try{
            ts.delete(Integer.parseInt(IDTodelete.getText()));
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("This transport is deleted");
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
        Parent root= FXMLLoader.load(getClass().getResource("/ShowTransports.fxml"));
        BackToHomePage.getScene().setRoot(root);
        System.out.println("moved");
    }
}
