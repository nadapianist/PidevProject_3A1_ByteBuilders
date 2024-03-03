package tn.esprit.Controllers;


//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.pdf.PdfPTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entities.Hostel;
import tn.esprit.services.HostelService;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;


/*import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;*/
public class HostelManagement  {
    Connection con;
    Statement stm;
    PreparedStatement pste;

    private final HostelService hs = new HostelService();
    @FXML
    private TextField HID;
    @FXML
    private TextField Name_hostelID;

    @FXML
    private TextField NBstarsID;
    @FXML
    private TextField InfoID;

    @FXML
    private TextField Search_field;


    @FXML
    private TableColumn<Hostel, String> id_hostel;

    @FXML
    private TableColumn<Hostel, String> Name_Hostel;

    @FXML
    private TableColumn<Hostel, String> NBstars;
    @FXML
    private TableColumn<Hostel, String> Info;
    @FXML
    private TableView<Hostel> ListHostel;

    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField locationfield;
    @FXML
    private URL location;

    @FXML
    private TextField IDHostel;

    @FXML
    private CategoryAxis x_axis;

    @FXML
    private NumberAxis y_axis;

    @FXML
    private BarChart<String, Integer> Most_searched;
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
    void AddHostel(ActionEvent event) throws SQLException {
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
            //Refresh();
            List<Hostel> Hostels=hs.displayList();
            ObservableList<Hostel> observableList= FXCollections.observableList(Hostels);
            ListHostel.setItems(observableList);
            id_hostel.setCellValueFactory(new PropertyValueFactory<>("IDHostel"));
            Name_Hostel.setCellValueFactory(new PropertyValueFactory<>("Name_hostel"));
            NBstars.setCellValueFactory(new PropertyValueFactory<>("NBstars"));
            Info.setCellValueFactory(new PropertyValueFactory<>("Info"));

        }catch (SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    /*@FXML
    void Refresh() throws SQLException {
        try {
            List<Hostel> Hostels=hs.displayList();
            ObservableList<Hostel> observableList= FXCollections.observableList(Hostels);
            ListHostel.setItems(observableList);
            id_hostel.setCellValueFactory(new PropertyValueFactory<>("IDHostel"));
            Name_Hostel.setCellValueFactory(new PropertyValueFactory<>("Name_hostel"));
            NBstars.setCellValueFactory(new PropertyValueFactory<>("NBstars"));
            Info.setCellValueFactory(new PropertyValueFactory<>("Info"));

        }catch (SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }*/
    @FXML
    void UpdateHostel(ActionEvent event) {
        try {


            hs.update(Integer.parseInt(HID.getText()),Name_hostelID.getText(),Integer.parseInt(NBstarsID.getText()),InfoID.getText());
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("A hostel is updated");
            alert.showAndWait();
            /*Refresh();
            clearFields();*/
        }
        catch (SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch (NumberFormatException e) {
            // Handle the case where the ID text cannot be parsed as an integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid integer for the reservation ID.");
            alert.showAndWait();
        }
    }


    @FXML
    void DeleteHostel(ActionEvent event) throws SQLException {
        try {
            hs.delete(Integer.parseInt(HID.getText()));
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Hostel deleted!");
            alert.showAndWait();
        } catch (SQLException e){
            // Display an error message if the ID text is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter the activity ID.");
            alert.showAndWait();
            //clearFields();
            //Refresh();
        }
        catch (NumberFormatException e) {
            // Handle the case where the ID text cannot be parsed hs an integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid integer for the activity ID.");
            alert.showAndWait();
            // clearFields();
        }
    }
    public void SearchByName(ActionEvent actionEvent) {
        try {
            String name = Search_field.getText();
            List<Hostel> SearchResults = hs.SearchHostels(name);
            ObservableList<Hostel> observableList= FXCollections.observableList(SearchResults);
            ListHostel.setItems(observableList);
            id_hostel.setCellValueFactory(new PropertyValueFactory<>("IDHostel"));
            Name_Hostel.setCellValueFactory(new PropertyValueFactory<>("Name_hostel"));
            NBstars.setCellValueFactory(new PropertyValueFactory<>("NBstars"));
            Info.setCellValueFactory(new PropertyValueFactory<>("Info"));

        }catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void FilterByNbs(ActionEvent actionEvent) {
        try {

            List<Hostel> SearchResults = hs.SortHostels();
            ObservableList<Hostel> observableList= FXCollections.observableList(SearchResults);
            ListHostel.setItems(observableList);
            id_hostel.setCellValueFactory(new PropertyValueFactory<>("IDHostel"));
            Name_Hostel.setCellValueFactory(new PropertyValueFactory<>("Name_hostel"));
            NBstars.setCellValueFactory(new PropertyValueFactory<>("NBstars"));
            Info.setCellValueFactory(new PropertyValueFactory<>("Info"));

        }catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void NextReservation(ActionEvent event) throws IOException {
        Parent root =FXMLLoader.load(getClass().getResource("/ReservationManagement.fxml"));
        Name_hostelID.getScene().setRoot(root);

    }
    /*private void clearFields() {
        HID.clear();
        Name_hostelID.clear();
        NBstarsID.clear();
        InfoID.clear();
    }*/
    private Stat statController; // Reference to the Stat controller

    public void setStatController(Stat statController) {
        this.statController = statController;
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
       /* if (statController != null) {
            statController.updateChart();
            System.out.println("button is clicked");
        }*/
    }
    //////////////////////////////////////////////////
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
        Parent root = FXMLLoader.load(getClass().getResource("/ReservationManagement.fxml"));
        reservationbtn.getScene().setRoot(root);

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
        Parent root = FXMLLoader.load(getClass().getResource("/DisplayUser.fxml"));
        userbtn.getScene().setRoot(root);

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



}









