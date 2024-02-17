package tn.esprit.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.Services.ReservationService;


import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateReservation {
    @FXML
    private Button CancelButton;

    @FXML
    private TextField NewDATER;

    @FXML
    private TextField NewPM;
    @FXML
    private TextField IDT;

    @FXML
    private TextField IDH;

    @FXML
    private Button SaveButton;
    private final ReservationService hs=new ReservationService();
    @FXML
    void HomePage(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/ShowReservationList.fxml"));
        CancelButton.getScene().setRoot(root);
        System.out.println("moved");
    }

    @FXML
    void SaveNewData(ActionEvent event) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(NewDATER.getText());

            hs.update2(Integer.parseInt(IDT.getText()),Integer.parseInt(IDH.getText()),date,NewPM.getText());
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("A reservation is updated");
            alert.showAndWait();
        }
        catch (SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
