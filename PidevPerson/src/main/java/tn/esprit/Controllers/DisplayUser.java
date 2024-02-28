package tn.esprit.Controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public void updateL(ActionEvent actionEvent) {
        LocalCom localSelected = (LocalCom) tableL.getSelectionModel().getSelectedItem();
        if (localSelected != null) {
            closewindow(actionEvent);
            openupdateLocal(localSelected);
        } else {
            System.out.println("You must select a local committee");
        }
    }

    private void openupdateLocal(LocalCom localCom) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateLocalCom.fxml"));
        Parent root;
        try {
            root = loader.load();
            updateLocalCom modifiedLocal = loader.getController();
            modifiedLocal.initDonneesLocal(localCom);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

