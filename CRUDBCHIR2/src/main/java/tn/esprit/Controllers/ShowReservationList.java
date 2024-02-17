package tn.esprit.Controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.Entities.Reservation;
import tn.esprit.Services.ReservationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
public class ShowReservationList {
    @FXML
    private Button DeleteButton;
    @FXML
    private Button AddButton;
    @FXML
    private Button UpdateButton;
    @FXML
    private TableColumn<Reservation, String> Idtourist;

    @FXML
    private TableColumn<Reservation, String> IDh;
    @FXML
    private TableColumn<Reservation, String> date_reserv;

    @FXML
    private TableColumn<Reservation, String> payment_method;
    @FXML
    private TableView<Reservation> ListReservation;
    private final ReservationService hs=new ReservationService();
    @FXML
    void initialize() {
        try {
            List<Reservation> Reservations=hs.displayList();
            ObservableList<Reservation> observableList= FXCollections.observableList(Reservations);
            ListReservation.setItems(observableList);
            Idtourist.setCellValueFactory(new PropertyValueFactory<>("Idtourist"));
            IDh.setCellValueFactory(new PropertyValueFactory<>("IDh"));
            date_reserv.setCellValueFactory(new PropertyValueFactory<>("date_reserv"));
            payment_method.setCellValueFactory(new PropertyValueFactory<>("payment_method"));


        }catch (SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    @FXML
    void NextPage(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/AddReservation.fxml"));
        AddButton.getScene().setRoot(root);
        System.out.println("moved");
    }

    public void DeletePage(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/DeleteReservation.fxml"));
        DeleteButton.getScene().setRoot(root);
        System.out.println("moved");
    }

    public void UpdatePage(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/UpdateReservation.fxml"));
        UpdateButton.getScene().setRoot(root);
        System.out.println("moved");
    }
}
