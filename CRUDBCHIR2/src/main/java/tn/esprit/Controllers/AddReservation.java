package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.Entities.Reservation;
import tn.esprit.Services.ReservationService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddReservation {
    private final ReservationService hs = new ReservationService();

    @FXML
    private TextField IdtouristID;

    @FXML
    private TextField IDhID;
    @FXML
    private TextField date_reservID;

    @FXML
    private TextField payment_methodID;

    @FXML
    private Button NavButton;

    @FXML
    void AddReservation(ActionEvent event) {
        try {
            // Parse date_reserv text into a Date object
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(date_reservID.getText());

            // Add the reservation using the parsed date and payment method
            hs.add(new Reservation(Integer.parseInt(IdtouristID.getText()),Integer.parseInt(IDhID.getText()),date, payment_methodID.getText()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("A new reservation is added");
            alert.showAndWait();
        } catch (SQLException | ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void goToReservationList(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ShowReservationList.fxml"));
        NavButton.getScene().setRoot(root);
        System.out.println("moved");
    }
}
