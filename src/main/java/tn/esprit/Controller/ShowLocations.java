package tn.esprit.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.Entity.Location;
import tn.esprit.Services.LocationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowLocations {

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
    private TextField ratingLocationId;
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
    @FXML
    private TableColumn<Location, Integer> ratingLoc;
    private final LocationService ls=new LocationService();
    @FXML
    void initialize() {
        try {
            List<Location> Locations=ls.displayList();
            ObservableList<Location> observableList= FXCollections.observableList(Locations);
            LocationList.setItems(observableList);
            NameLoc.setCellValueFactory(new PropertyValueFactory<>("Name"));
            InfoLoc.setCellValueFactory(new PropertyValueFactory<>("info"));
            categoryLoc.setCellValueFactory(new PropertyValueFactory<>("category"));
            ActivityLocId.setCellValueFactory(new PropertyValueFactory<>("ActivityID"));
            LocHostelId.setCellValueFactory(new PropertyValueFactory<>("hostelID"));
            ratingLoc.setCellValueFactory(new PropertyValueFactory<>("rating"));


        }catch (SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    public void SearchByName(ActionEvent actionEvent) {
        try {
            String name = NameSearchField.getText();
            List<Location> SearchResults = ls.SearchLocations(name);
            ObservableList<Location> observableList= FXCollections.observableList(SearchResults);
            LocationList.setItems(observableList);
            NameLoc.setCellValueFactory(new PropertyValueFactory<>("Name"));
            InfoLoc.setCellValueFactory(new PropertyValueFactory<>("info"));
            categoryLoc.setCellValueFactory(new PropertyValueFactory<>("category"));
            ActivityLocId.setCellValueFactory(new PropertyValueFactory<>("ActivityID"));
            LocHostelId.setCellValueFactory(new PropertyValueFactory<>("hostelID"));
            ratingLoc.setCellValueFactory(new PropertyValueFactory<>("rating"));

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
            ActivityLocId.setCellValueFactory(new PropertyValueFactory<>("ActivityID"));
            LocHostelId.setCellValueFactory(new PropertyValueFactory<>("hostelID"));
            ratingLoc.setCellValueFactory(new PropertyValueFactory<>("rating"));

        }catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void AddNewLocation(ActionEvent actionEvent) {
        try{
            ls.add(new Location(
                    nameLocationId.getText()
                    ,infoLocationId.getText()
                    ,categorylocationId.getText()
                    ,Integer.parseInt(activityLocationId.getText())
                    ,Integer.parseInt(LocationHostelId.getText())
                    ,Integer.parseInt(ratingLocationId.getText())
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



}
