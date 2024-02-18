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

public class UpdateTransport {
    @FXML
    private Button CloseWindowButton;

    @FXML
    private TextField NewBrand;

    @FXML
    private TextField NewChargingTime;

    @FXML
    private TextField NewDistance;

    @FXML
    private TextField TransportID;

    @FXML
    private Button UpdateButton;
private final TransportService ts=new TransportService();
    @FXML
    void ReturnToList(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/ShowTransports.fxml"));
        CloseWindowButton.getScene().setRoot(root);
        System.out.println("moved");
    }

    @FXML
    void SaveChanges(ActionEvent event) {
        try{
            ts.update(Integer.parseInt(TransportID.getText())
                    ,NewBrand.getText()
                    ,Integer.parseInt(NewDistance.getText())
                    ,Integer.parseInt(NewChargingTime.getText()));
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("transport data is updated");
            alert.showAndWait();
        }
        catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

    }
}
