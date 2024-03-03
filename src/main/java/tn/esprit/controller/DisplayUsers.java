package tn.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tn.esprit.entities.User;
import tn.esprit.services.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DisplayUsers {
    private final UserService ser=new UserService();

    @FXML
    private TableColumn<?, ?> emailcol;

    @FXML
    private TableColumn<?, ?> pwdcol;

    @FXML
    private TextField search;
    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<?, ?> ueridcol;

    @FXML
    void sort(ActionEvent event) {
        try {
            List<User> sortedList = ser.diplayListsortedbymail();
            ObservableList<User> obers = FXCollections.observableList(sortedList);
            tableView.setItems(obers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void initialize() {
        try {
            refreshTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Ajouter un gestionnaire d'événements pour le bouton de recherche
        search.setOnKeyPressed(this::handleSearchKeyPressed);

        // Ajouter un écouteur de changement de texte pour la recherche interactive
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // Effectuer la recherche avec le nouveau texte
                List<User> searchResults = ser.searchByName(newValue);

                // Mettre à jour la table avec les résultats de la recherche
                ObservableList<User> obers = FXCollections.observableList(searchResults);
                tableView.setItems(obers);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void refreshTable() throws SQLException {
        List<User> list = ser.listAll();
        ObservableList<User> obers = FXCollections.observableList(list);
        tableView.setItems(obers);
        ueridcol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        pwdcol.setCellValueFactory(new PropertyValueFactory<>("pwd"));
    }

    private void handleSearchKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                // Récupérer le texte de recherche
                String searchTerm = search.getText();

                // Effectuer la recherche
                List<User> searchResults = ser.searchByName(searchTerm);

                // Mettre à jour la table avec les résultats de la recherche
                ObservableList<User> obers = FXCollections.observableList(searchResults);
                tableView.setItems(obers);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void displayUser(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        try {
            // Open new window (displayUser)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayUser.fxml"));
            Parent root = loader.load();
            Stage displayUserStage = new Stage();
            displayUserStage.setScene(new Scene(root));
            displayUserStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

