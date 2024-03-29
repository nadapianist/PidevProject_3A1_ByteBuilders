package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entities.Admin;
import tn.esprit.entities.LocalCom;
import tn.esprit.entities.Tourist;
import tn.esprit.entities.User;
import tn.esprit.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DisplayUser {

    @FXML
    private TableColumn<?, ?> colbioT;

    @FXML
    private TableColumn<?, ?> colemailA;

    @FXML
    private TableColumn<?, ?> colemailL;

    @FXML
    private TableColumn<?, ?> colemailT;

    @FXML
    private TableColumn<?, ?> colfnL;

    @FXML
    private TableColumn<?, ?> colfnT;

    @FXML
    private TableColumn<?, ?> colidA;

    @FXML
    private TableColumn<?, ?> colidL;

    @FXML
    private TableColumn<?, ?> colidT;

    @FXML
    private TableColumn<?, ?> collnL;

    @FXML
    private TableColumn<?, ?> collnT;

    @FXML
    private TableColumn<?, ?> colpnL;

    @FXML
    private TableColumn<?, ?> colpnT;

    @FXML
    private TableColumn<?, ?> availL;

    @FXML
    private TableColumn<?, ?> colprefT;

    @FXML
    private TableColumn<?, ?> colpwdA;

    @FXML
    private TableColumn<?, ?> colpwdL;

    @FXML
    private TableColumn<?, ?> colpwdT;

    @FXML
    private TableView<User> tabeT;

    @FXML
    private TableView<User> tableA;

    @FXML
    private TableView<User> tableL;
    private tn.esprit.entities.Admin Admin;

////////////////////////////////////////////Buttons/////////////////////

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


    private final UserService ser=new UserService();



    @FXML
    void  initialize() {
        try {
            List<User> allUsers = ser.listAll();
            ObservableList<User> allUsersData = FXCollections.observableArrayList(allUsers);

            FilteredList<User> adminFilteredList = new FilteredList<>(allUsersData, user -> user instanceof Admin);
            FilteredList<User> touristFilteredList = new FilteredList<>(allUsersData, user -> user instanceof Tourist);
            FilteredList<User> localcomFilteredList = new FilteredList<>(allUsersData, user -> user instanceof LocalCom);

            tableA.setItems(adminFilteredList);
            tabeT.setItems(touristFilteredList);
            tableL.setItems(localcomFilteredList);
            configuretableA();
            configuretableT();
            configuretableL();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configuretableA() {
        colidA.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        colemailA.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colpwdA.setCellValueFactory(new PropertyValueFactory<>("pwd"));

    }

    private void configuretableT() {
        colidT.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        colemailT.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colpwdT.setCellValueFactory(new PropertyValueFactory<>("pwd"));
        colfnT.setCellValueFactory(new PropertyValueFactory<>("Fname"));
        collnT.setCellValueFactory(new PropertyValueFactory<>("Lname"));
        colpnT.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colbioT.setCellValueFactory(new PropertyValueFactory<>("Bio"));
        colprefT.setCellValueFactory(new PropertyValueFactory<>("Preferences"));

    }

    private void configuretableL() {
        colidL.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        colemailL.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colpwdL.setCellValueFactory(new PropertyValueFactory<>("pwd"));
        colfnL.setCellValueFactory(new PropertyValueFactory<>("Fname"));
        collnL.setCellValueFactory(new PropertyValueFactory<>("Lname"));
        colpnL.setCellValueFactory(new PropertyValueFactory<>("phone"));
        availL.setCellValueFactory(new PropertyValueFactory<>("Availability"));

    }

    public void deleteT(ActionEvent actionEvent) throws SQLException {
        User touristselec = tabeT.getSelectionModel().getSelectedItem();
        if (touristselec != null) {
            ser.delete(touristselec.getUserID());

            initialize();
        } else {

            System.out.println("You must select a Tourist.");
        }

    }

    @FXML
    void deleteA(ActionEvent event) throws SQLException {
        User adminselec = tableA.getSelectionModel().getSelectedItem();
        if (adminselec != null) {
            ser.delete(adminselec.getUserID());

            initialize();
        } else {

            System.out.println("You must select an Admin.");
        }

    }

    @FXML
    void deleteL(ActionEvent event)throws SQLException {

        User localselec = tableL.getSelectionModel().getSelectedItem();
        if (localselec != null) {
            ser.delete(localselec.getUserID());

            initialize();
        } else {

            System.out.println("You must select a Local committee.");
        }

    }

    @FXML
    void UpdateA(ActionEvent event) {
        Admin adminselec = (Admin) tableA.getSelectionModel().getSelectedItem();
        if (adminselec != null) {
            closewindow(event);
            openupdateAdmin(adminselec);
        } else {
            System.out.println("You must select an admin");
        }

    }
    private void openupdateAdmin(Admin admin) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateAdmin.fxml"));
        Parent root;
        try {
            root = loader.load();
            updateAdmin updateAdmin = loader.getController();
            updateAdmin.initDonneesAdmin(admin);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closewindow(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void AddA(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminSignUp.fxml"));
            Parent root = loader.load();

            Stage signUpStage = new Stage();
            signUpStage.setScene(new Scene(root));
            signUpStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayUsers(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/displayUsers.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deactivateT(ActionEvent actionEvent) throws SQLException {
        User touristSelected = tabeT.getSelectionModel().getSelectedItem();
        if (touristSelected != null) {
            touristSelected.setStatus(true);  // Set status to true (deactivate)
            ser.updateUserStatus(touristSelected.getUserID(), false);  // Update status in the database
            showStatusAlert("Tourist deactivated. Status: " );
            initialize();
        } else {
            System.out.println("You must select a Tourist.");
        }

    }

    @FXML
    void deactivateL(ActionEvent actionEvent) throws SQLException {
        User localSelected = tableL.getSelectionModel().getSelectedItem();
        if (localSelected != null) {
            ser.deactivateUser(localSelected.getUserID());
            showStatusAlert("Local committee deactivated. Status: " );
            initialize();
        } else {
            System.out.println("You must select a Local committee.");
        }
    }

    @FXML
    void activateT(ActionEvent actionEvent) throws SQLException {
        User touristSelected = tabeT.getSelectionModel().getSelectedItem();
        if (touristSelected != null) {
            touristSelected.setStatus(false);  // Set status to false (activate)
            ser.updateUserStatus(touristSelected.getUserID(), true);  // Update status in the database
            showStatusAlert("Tourist activated. Status: " );
            initialize();
        } else {
            System.out.println("You must select a Tourist.");
        }

    }

    @FXML
    void activateL(ActionEvent actionEvent) throws SQLException {
        User localSelected = tableL.getSelectionModel().getSelectedItem();
        if (localSelected != null) {
            ser.activateUser(localSelected.getUserID());
            showStatusAlert("Local committee activated. Status: ");
            initialize();
        } else {
            System.out.println("You must select a Local committee.");
        }
    }

    private void showStatusAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
       /* Parent root = FXMLLoader.load(getClass().getResource("/DisplayUser.fxml"));
        userbtn.getScene().setRoot(root);*/

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



