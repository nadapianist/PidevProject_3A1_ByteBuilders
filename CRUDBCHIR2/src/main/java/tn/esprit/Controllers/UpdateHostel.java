package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.Services.HostelService;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateHostel {
    @FXML
    private Button CancelButton;

    @FXML
    private TextField NewNameH;

    @FXML
    private TextField NewNBS;
    @FXML
    private TextField NewINF;

    @FXML
    private TextField PID;

    @FXML
    private Button SaveButton;
    private final HostelService hs=new HostelService();
    @FXML
    void HomePage(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/ShowHostelList.fxml"));
        CancelButton.getScene().setRoot(root);
        System.out.println("moved");
    }

    @FXML
    void SaveNewData(ActionEvent event) {
        try {


            hs.update(Integer.parseInt(PID.getText()),NewNameH.getText(),Integer.parseInt(NewNBS.getText()),NewINF.getText());
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("A hostel is updated");
            alert.showAndWait();
        }
        catch (SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }
}
