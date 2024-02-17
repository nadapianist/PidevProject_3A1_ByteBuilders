package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.Services.HostelService;
import tn.esprit.Entities.Hostel;

import java.io.IOException;
import java.sql.SQLException;

public class AddHostel {

    private  final HostelService hs = new HostelService();

    @FXML
    private TextField Name_hostelID;

    @FXML
    private TextField NBstarsID;
    @FXML
    private TextField InfoID;
    @FXML
    private Button NavButton;
    @FXML
    void AddHostel(ActionEvent event)  {
        try {


        hs.add(new Hostel( Name_hostelID.getText(), Integer.parseInt(NBstarsID.getText()),InfoID.getText()));
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("A new hostel is added");
        alert.showAndWait();
    }
        catch (SQLException e){
        Alert alert= new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(e.getMessage());
        alert.showAndWait();

    }

}


    public void HomePage(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/ShowHostelList.fxml"));
        NavButton.getScene().setRoot(root);
        System.out.println("moved");
    }
}
