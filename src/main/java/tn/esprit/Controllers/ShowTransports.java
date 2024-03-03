package tn.esprit.Controllers;


//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.common.BitMatrix;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import tn.esprit.entities.Location;
import tn.esprit.entities.Transport;
import tn.esprit.Exception.InvalidLengthException;
import tn.esprit.services.TransportService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
public class ShowTransports {
    public final int  MAXLENGTH=10;
    @FXML
    private Button generateButton;
    @FXML
    private ImageView QrView;
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

    public void generateQRCodeForSelectedTransport() {
       /* Transport selectedTransport = TransportList.getSelectionModel().getSelectedItem();

        if (selectedTransport != null) {
            String transportData = selectedTransport.toString(); // Convert transport data to string for QR code generation
            System.out.println(transportData);
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                BitMatrix bitMatrix = new MultiFormatWriter().encode(transportData, BarcodeFormat.QR_CODE, 200, 200);
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
                byte[] qrCodeBytes = out.toByteArray();
                Image qrCodeImage = new Image(new ByteArrayInputStream(qrCodeBytes));
                QrView.setImage(qrCodeImage); // Display QR code in ImageView
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }
    public void AddNewTransport(ActionEvent actionEvent) {

        try{
            validateStringLength(brandTrId.getText(), MAXLENGTH);
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

    public void generateQr(MouseEvent mouseEvent) {
       // generateQRCodeForSelectedTransport();
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


}
