package tn.esprit.Controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import tn.esprit.Entity.Location;
import tn.esprit.Entity.Transport;
import tn.esprit.Services.TransportService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowTransports {
    @FXML
    private TextField SearchField;
    @FXML
    private TextField idTransportId;
    @FXML
    private TextField brandTrId;
    @FXML
    private TextField typeTrId;
    @FXML
    private TextField distanceTrId;
    @FXML
    private TextField chargeTimeTrId;
    @FXML
    private TextField idtouristTrId;
    @FXML
    private Button ManageLocationsButton;
    @FXML
    private Button AddButton;

    @FXML
    private TableColumn<Transport, String> brand;

    @FXML
    private TableColumn<Transport, Integer> ChargingTime;

    @FXML
    private Button DeleteButton;

    @FXML
    private TableColumn<Transport, Integer> distance;

    @FXML
    private TableColumn<Transport, Integer> IDtourist;

    @FXML
    private TableView<Transport> TransportList;

    @FXML
    private TableColumn<Transport, String> type;

    @FXML
    private Button UpdateButton;
    private final TransportService ts=new TransportService();
    @FXML
    void initialize() {
        try {
            List<Transport> Transports=ts.displayList();
            ObservableList<Transport> observableList= FXCollections.observableList(Transports);
            TransportList.setItems(observableList);
            brand.setCellValueFactory(new PropertyValueFactory<>("Brand"));
            type.setCellValueFactory(new PropertyValueFactory<>("Type"));
            distance.setCellValueFactory(new PropertyValueFactory<>("Distance"));
            ChargingTime.setCellValueFactory(new PropertyValueFactory<>("ChargingTime"));

            IDtourist.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transport, Integer>, ObservableValue<Integer>>() {
                @Override
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Transport, Integer> transportIntegerCellDataFeatures) {
                    // Assuming Transport has a method getIdTourist() that returns an Integer property
                    return new SimpleIntegerProperty(transportIntegerCellDataFeatures.getValue().getTouristID()).asObject();
                }
            });


        }catch (SQLException e){
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

    public void AddNewTransport(ActionEvent actionEvent) {
        try{
            ts.add(new Transport(
                    brandTrId.getText()
                    ,typeTrId.getText()
                    ,Integer.parseInt(distanceTrId.getText())
                    ,Integer.parseInt(chargeTimeTrId.getText())
                    ,Integer.parseInt(idtouristTrId.getText())
            ));
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("A Transport is added");
            alert.showAndWait();
        }
        catch(
                SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
        
    }

    public void SaveChanges(ActionEvent actionEvent) {
        try{
            ts.update(Integer.parseInt(idTransportId.getText())
                    ,brandTrId.getText()
                    ,Integer.parseInt(distanceTrId.getText())
                    ,Integer.parseInt(chargeTimeTrId.getText()));
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("transport data is updated");
            alert.showAndWait();
        }
        catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }

    public void DeleteTransportById(ActionEvent actionEvent) {
        try{
            ts.delete(Integer.parseInt(idTransportId.getText()));
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("This transport is deleted");
            alert.showAndWait();
        }
        catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }

    public void SortByType(ActionEvent actionEvent) {
        try {

            List<Transport> SortedList=ts.SortTransports();
            ObservableList<Transport> observableList= FXCollections.observableList(SortedList);
            TransportList.setItems(observableList);
            brand.setCellValueFactory(new PropertyValueFactory<>("Brand"));
            type.setCellValueFactory(new PropertyValueFactory<>("Type"));
            distance.setCellValueFactory(new PropertyValueFactory<>("Distance"));
            ChargingTime.setCellValueFactory(new PropertyValueFactory<>("ChargingTime"));

            IDtourist.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transport, Integer>, ObservableValue<Integer>>() {
                @Override
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Transport, Integer> transportIntegerCellDataFeatures) {
                    // Assuming Transport has a method getIdTourist() that returns an Integer property
                    return new SimpleIntegerProperty(transportIntegerCellDataFeatures.getValue().getTouristID()).asObject();
                }
            });
        }catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void SearchBrand(ActionEvent actionEvent) {
        try {

            List<Transport> SearchResults=ts.SearchTransports(SearchField.getText());
            ObservableList<Transport> observableList= FXCollections.observableList(SearchResults);
            TransportList.setItems(observableList);
            brand.setCellValueFactory(new PropertyValueFactory<>("Brand"));
            type.setCellValueFactory(new PropertyValueFactory<>("Type"));
            distance.setCellValueFactory(new PropertyValueFactory<>("Distance"));
            ChargingTime.setCellValueFactory(new PropertyValueFactory<>("ChargingTime"));

            IDtourist.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transport, Integer>, ObservableValue<Integer>>() {
                @Override
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Transport, Integer> transportIntegerCellDataFeatures) {
                    // Assuming Transport has a method getIdTourist() that returns an Integer property
                    return new SimpleIntegerProperty(transportIntegerCellDataFeatures.getValue().getTouristID()).asObject();
                }
            });
        }catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
