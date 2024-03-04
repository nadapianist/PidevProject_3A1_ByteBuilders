package tn.esprit.Controllers;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.entities.Location;
import tn.esprit.entities.Rating;
import tn.esprit.Exception.InvalidLengthException;
import tn.esprit.services.LocationService;
import tn.esprit.services.RatingService;
//import tn.esprit.services.RatingService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.IllegalFormatException;
import java.util.List;

public class ShowLocations {
    public final int  MAXLENGTH=10;
    public Button viewRating;
    @FXML
    private Label AvgLocRating;
    @FXML
    private Text avgRating;

    @FXML
    private TableView<Rating>RatingList;
    @FXML
    private TableColumn<Rating, Integer> nbStarsCol;

    @FXML
    private TableColumn<Rating, Date> ratingDateCol;
    @FXML
    private TableColumn<Rating, Integer> IdLocCol;

    @FXML
    private TableColumn<Rating, Integer> userIdCol;

    @FXML
    private TextField categorylocationId;
    @FXML
    private TextField activityLocationId;
    @FXML
    private TextField nameLocationId;
    @FXML
    private TextField infoLocationId;
    @FXML
    private TextField LocationHostelId;
    @FXML
    private TextField IDLocId;

    @FXML
    private Button ManageTransportButton;
    @FXML
    private Button ManageLocationsButton;
    @FXML
    private TextField NameSearchField;
    @FXML
    private Button DeleteLocButton;
    @FXML
    private Button EditButton;
    @FXML
    private Button AddButton;

    @FXML
    private TableColumn<Location, Integer> ActivityLocId;

    @FXML
    private TableColumn<Location, String> InfoLoc;

    @FXML
    private TableColumn<Location, String> LocHostelId;

    @FXML
    private TableView<Location> LocationList;

    @FXML
    private TableColumn<Location, String> NameLoc;

    @FXML
    private TableColumn<Location, String> categoryLoc;
    private final RatingService rs=new RatingService();
    private final LocationService ls=new LocationService();
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


    //List Initiation
    @FXML
    void initialize() {
        try {
            List<Location> Locations=ls.displayList();
            ObservableList<Location> observableList= FXCollections.observableList(Locations);
            LocationList.setItems(observableList);

            NameLoc.setCellValueFactory(new PropertyValueFactory<>("Name"));
            InfoLoc.setCellValueFactory(new PropertyValueFactory<>("info"));
            categoryLoc.setCellValueFactory(new PropertyValueFactory<>("category"));
            //ActivityLocId.setCellValueFactory(new PropertyValueFactory<>("ActivityID"));
            LocHostelId.setCellValueFactory(new PropertyValueFactory<>("hostelID"));

        }catch (SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    //Search and sort
    public void SearchByName(ActionEvent actionEvent) {
        try {
            String name = NameSearchField.getText();
            List<Location> SearchResults = ls.SearchLocations(name);
            ObservableList<Location> observableList= FXCollections.observableList(SearchResults);
            LocationList.setItems(observableList);
            NameLoc.setCellValueFactory(new PropertyValueFactory<>("Name"));
            InfoLoc.setCellValueFactory(new PropertyValueFactory<>("info"));
            categoryLoc.setCellValueFactory(new PropertyValueFactory<>("category"));
            //ActivityLocId.setCellValueFactory(new PropertyValueFactory<>("ActivityID"));
            LocHostelId.setCellValueFactory(new PropertyValueFactory<>("hostelID"));


        }catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void FilterByCategory(ActionEvent actionEvent) {
        try {

            List<Location> SearchResults = ls.SortLocations();
            ObservableList<Location> observableList= FXCollections.observableList(SearchResults);
            LocationList.setItems(observableList);
            NameLoc.setCellValueFactory(new PropertyValueFactory<>("Name"));
            InfoLoc.setCellValueFactory(new PropertyValueFactory<>("info"));
            categoryLoc.setCellValueFactory(new PropertyValueFactory<>("category"));
            //ActivityLocId.setCellValueFactory(new PropertyValueFactory<>("ActivityID"));
            LocHostelId.setCellValueFactory(new PropertyValueFactory<>("hostelID"));


        }catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    //CRUD
    public void AddNewLocation(ActionEvent actionEvent) {

        try{
            validateStringLength(nameLocationId.getText(), MAXLENGTH);
            ls.add(new Location(
                    Integer.parseInt(IDLocId.getText())
                    ,nameLocationId.getText()
                    ,infoLocationId.getText()
                    ,categorylocationId.getText()

                    ,Integer.parseInt(LocationHostelId.getText())

            ));
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("A location is added");
            alert.showAndWait();
        }
        catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Invalid ID format. Please enter a valid integer.");
            alert.showAndWait();
        }catch(InvalidLengthException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Invalid Input Length");
            alert.showAndWait();
        }
    }

    public static void validateStringLength(String str, int maxLength) throws InvalidLengthException {
        if (str.length() > maxLength) {
            throw new InvalidLengthException("The given string must not exceed " + maxLength + " characters.");
        }
    }

    public void SaveChanges(ActionEvent actionEvent) {
        try{
            ls.update(Integer.parseInt(IDLocId.getText()),
                    nameLocationId.getText(),
                    infoLocationId.getText());
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("This location is updated");
            alert.showAndWait();
        }
        catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }
    public void DeleteLocationById(ActionEvent actionEvent) {
        try{
            ls.delete(Integer.parseInt(IDLocId.getText()));
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("This location is deleted");
            alert.showAndWait();
        }
        catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }
    //pages navigation
    public void ToLocationPage(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/ShowLocations.fxml"));
        ManageLocationsButton.getScene().setRoot(root);
        System.out.println("moved");
    }

    public void ToTransportPage(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/ShowTransports.fxml"));
        ManageTransportButton.getScene().setRoot(root);
        System.out.println("moved");
    }

    @FXML
    void ShowRatings(ActionEvent actionEvent) {
       try {
            int idlocation=Integer.parseInt(IDLocId.getText());
            List<Rating> ratings = rs.displayList(idlocation);
            ObservableList<Rating> observableList = FXCollections.observableList(ratings);
            RatingList.setItems(observableList);

            userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
            IdLocCol.setCellValueFactory(new PropertyValueFactory<>("IdLoc"));
            ratingDateCol.setCellValueFactory(new PropertyValueFactory<>("ratingDate"));
            nbStarsCol.setCellValueFactory(new PropertyValueFactory<>("nbStars"));

            double sumOfStars = 0;
            int numOfRatings = 0;

            for (Rating rating : ratings) {
                if (rating.getIdLoc() == idlocation) {
                    sumOfStars += rating.getNbStars();
                    numOfRatings++;
                }
            }
            double avgRating = sumOfStars / Math.max(numOfRatings, 1d);
            System.out.println("Average Rating:"+String.format("%.2f", avgRating));
            AvgLocRating.setText("Average Rating:"+String.format("%.2f", avgRating));


        }
        catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
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
       /* Parent root = FXMLLoader.load(getClass().getResource("/ShowLocations.fxml"));
        locationbtn.getScene().setRoot(root);*/
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
