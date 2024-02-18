package tn.esprit.Controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;
import tn.esprit.Entity.Transport;
import tn.esprit.Services.TransportService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowTransports {
    @FXML
    private Button AddPageButton;

    @FXML
    private TableColumn<Transport, String> brand;

    @FXML
    private TableColumn<Transport, Integer> ChargingTime;

    @FXML
    private Button DeletePageButton;

    @FXML
    private TableColumn<Transport, Integer> distance;

    @FXML
    private TableColumn<Transport, Integer> IDtourist;

    @FXML
    private TableView<Transport> TransportList;

    @FXML
    private TableColumn<Transport, String> type;

    @FXML
    private Button UpdatePageButton;
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

    public void ToAddPage(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/AddTransport.fxml"));
        AddPageButton.getScene().setRoot(root);
        System.out.println("moved");
    }

    public void ToUpdatePage(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/UpdateTransport.fxml"));
        UpdatePageButton.getScene().setRoot(root);
        System.out.println("moved");
    }

    public void ToDeletePage(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/DeleteTransport.fxml"));
        DeletePageButton.getScene().setRoot(root);
        System.out.println("moved");
    }
}
