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
public class DeleteHostel {
    @FXML
    private Button CancelButton;

    @FXML
    private Button ConfDel;

    @FXML
    private TextField IdToDelete;
    private  final HostelService hs = new HostelService();
    public void DeleteHostel(ActionEvent actionEvent) {
        try {


            hs.delete(Integer.parseInt(IdToDelete.getText()));
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Hostel deleted!");
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
        CancelButton.getScene().setRoot(root);
        System.out.println("moved");
    }
}
