package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entities.Hostel;
import tn.esprit.entities.Reservation;
import tn.esprit.services.ReservationService;


import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ReservationManagement {

    @FXML
    private TextField Search_field;
    @FXML
    private TextField IdtouristID;

    @FXML
    private TextField IDhID;
    @FXML
    private TextField date_reservID;

    @FXML
    private TextField payment_methodID;
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
    @FXML
    private TextField IdToDelete;
    @FXML
    private TextField Search_ch_field;
    @FXML
    private BarChart<String, Integer> St;

    private final ReservationService rs = new ReservationService();
    @FXML
    private CategoryAxis x_axis;

    @FXML
    private NumberAxis y_axis;
    @FXML
    private Button activitybtn;

    @FXML
    private Button challengebtn;

    @FXML
    private Button forumbtnid;

    @FXML
    private Button hostelbtn;
    @FXML
    private Button locationbtn;

    @FXML
    private Button reservationbtn;

    @FXML
    private Button reviewbtn;

    @FXML
    private Button transportbtn;

    @FXML
    private Button userbtn;

    @FXML
    void AddReservation(ActionEvent event) {
        try {
            // Parse date_reserv text into a Date object
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(date_reservID.getText());

            // Add the reservation using the parsed date and payment method
            rs.add(new Reservation(Integer.parseInt(IdtouristID.getText()),Integer.parseInt(IDhID.getText()),date, payment_methodID.getText()));

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

        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Invalid points format. Please enter a valid integer.");
            alert.showAndWait();
            //  clearFields();

        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            // clearFields();
        }

    }

    @FXML
    void initialize() throws SQLException {
        try {
            List<Reservation> Reservations=rs.displayList();
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
    void UpdateReservation(ActionEvent event) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(date_reservID.getText());

            rs.update2(Integer.parseInt(IdtouristID.getText()),Integer.parseInt(IDhID.getText()),date,payment_methodID.getText());
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

        }catch (NumberFormatException e) {
            // Handle the case where the ID text cannot be parsed as an integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid integer for the reservation ID.");
            alert.showAndWait();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void DeleteReservation(ActionEvent actionEvent) {
        try {


            rs.delete(Integer.parseInt(IdtouristID.getText()),Integer.parseInt(IDhID.getText()));
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Reservation deleted!");
            alert.showAndWait();

        } catch (NumberFormatException e) {
            // Handle the case where the ID text cannot be parsed as an integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid integer for the reservation ID.");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void SearchByDate(ActionEvent actionEvent) {
        try {
            LocalDate date = LocalDate.parse(Search_field.getText());
            List<Reservation> Reservations = rs.SearchReservations(date);
            ObservableList<Reservation> observableList = FXCollections.observableList(Reservations);
            ListReservation.setItems(observableList);
            Idtourist.setCellValueFactory(new PropertyValueFactory<>("Idtourist"));
            IDh.setCellValueFactory(new PropertyValueFactory<>("IDh"));
            date_reserv.setCellValueFactory(new PropertyValueFactory<>("date_reserv"));
            payment_method.setCellValueFactory(new PropertyValueFactory<>("payment_method"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void FilterByPM(ActionEvent actionEvent) {
        try {

            List<Reservation> Reservations=rs.SortReservations();
            ObservableList<Reservation> observableList= FXCollections.observableList(Reservations);
            ListReservation.setItems(observableList);
            Idtourist.setCellValueFactory(new PropertyValueFactory<>("Idtourist"));
            IDh.setCellValueFactory(new PropertyValueFactory<>("IDh"));
            date_reserv.setCellValueFactory(new PropertyValueFactory<>("date_reserv"));
            payment_method.setCellValueFactory(new PropertyValueFactory<>("payment_method"));

        }catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void NextHostel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/HostelManagement.fxml"));
        IdtouristID.getScene().setRoot(root);
    }

    //////////////////////////////////////////////////BUTTONS/////////////////
    @FXML
    void activityBTN(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ActivityManagement.fxml"));
        activitybtn.getScene().setRoot(root);

    }
    @FXML
    void ForumBTN(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/forumManagement.fxml"));
        forumbtnid.getScene().setRoot(root);

    }

    @FXML
    void LocationBTN(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ShowLocations.fxml"));
        locationbtn.getScene().setRoot(root);
    }

    @FXML
    void ReservationBTN(ActionEvent event) throws IOException {
      /*  Parent root = FXMLLoader.load(getClass().getResource("/ReservationManagement.fxml"));
        reservationbtn.getScene().setRoot(root);*/

    }

    @FXML
    void ReviewBTN(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ReviewChallengeManagement.fxml"));
        reviewbtn.getScene().setRoot(root);

    }

    @FXML
    void TransportBTN(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ShowTransports.fxml"));
        transportbtn.getScene().setRoot(root);

    }

    @FXML
    void hostelBTN(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/HostelManagement.fxml"));
        hostelbtn.getScene().setRoot(root);

    }

    @FXML
    void userBTN(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/DisplayUser.fxml"));
        userbtn.getScene().setRoot(root);

    }
    @FXML
    void challengeBTN(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ChallengeManagement.fxml"));
        userbtn.getScene().setRoot(root);
    }
    public void logout(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        try {
            // Open new window (displayUser)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            Stage displayUserStage = new Stage();
            displayUserStage.setScene(new Scene(root));
            displayUserStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}


