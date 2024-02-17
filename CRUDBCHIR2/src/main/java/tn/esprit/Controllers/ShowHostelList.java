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
import tn.esprit.Entities.Hostel;
import tn.esprit.Services.HostelService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowHostelList {
    @FXML
    private Button DeleteButton;
    @FXML
    private Button AddButton;
    @FXML
    private Button UpdateButton;
    @FXML
    private TableColumn<Hostel, String> Name_Hostel;

    @FXML
    private TableColumn<Hostel, String> NBstars;
    @FXML
    private TableColumn<Hostel, String> Info;
    @FXML
    private TableView<Hostel> ListHostel;
    private final HostelService hs=new HostelService();
    @FXML
    void initialize() {
        try {
            List<Hostel> Hostels=hs.displayList();
            ObservableList<Hostel> observableList= FXCollections.observableList(Hostels);
            ListHostel.setItems(observableList);
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
    @FXML
    void NextPage(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/AddHostel.fxml"));
        AddButton.getScene().setRoot(root);
        System.out.println("moved");
    }

    public void DeletePage(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/DeleteHostel.fxml"));
        DeleteButton.getScene().setRoot(root);
        System.out.println("moved");
    }

    public void UpdatePage(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/UpdateHostel.fxml"));
        UpdateButton.getScene().setRoot(root);
        System.out.println("moved");
    }
}
