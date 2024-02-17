package tn.esprit.Controller;

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
import tn.esprit.Entity.Location;
import tn.esprit.Services.LocationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowLocations {
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

    public void ToAddPage(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/AddLocation.fxml"));
        AddButton.getScene().setRoot(root);
        System.out.println("moved");
    }

    public void ToEditPage(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/UpdateLocation.fxml"));
        EditButton.getScene().setRoot(root);
        System.out.println("moved");
    }

    public void ToDeletePage(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/DeleteLocation.fxml"));
        DeleteLocButton.getScene().setRoot(root);
        System.out.println("moved");
    }
}
