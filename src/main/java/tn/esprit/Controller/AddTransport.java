package tn.esprit.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.Entity.Transport;
import tn.esprit.Services.TransportService;

import java.io.IOException;
import java.sql.SQLException;

public class AddTransport {
    @FXML
    private TextField BrandField;

    @FXML
    private Button CancelChanges;

    @FXML
    private TextField ChargingTimeField;

    @FXML
    private TextField DistanceField;

    @FXML
    private TextField IDTourField;

    @FXML
    private Button SaveChanges;

    @FXML
    private TextField TypeField;
   private final TransportService ts=new TransportService();

    public void NewData(ActionEvent actionEvent) {
        try{
        ts.add(new Transport(
                BrandField.getText()
                ,TypeField.getText()
                ,Integer.parseInt(DistanceField.getText())
                ,Integer.parseInt(ChargingTimeField.getText())
                ,Integer.parseInt(IDTourField.getText())
        ));
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("A Transport is added");
        alert.showAndWait();
    }
        catch(
    SQLException e){
        Alert alert= new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(e.getMessage());
        alert.showAndWait();

    }

    }

    public void BackHome(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/ShowTransports.fxml"));
        CancelChanges.getScene().setRoot(root);
        System.out.println("moved");
    }
}
