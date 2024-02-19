package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entities.Activity;
import tn.esprit.entities.Challenge;
import tn.esprit.services.ActivityService;
import tn.esprit.services.ChallengeService;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;

public class ChallengeManagement {


    @FXML
    private TableColumn<Challenge, String> Name_ch_column;

    @FXML
    private TableView<Challenge> TableViewChallenge;

    @FXML
    private TableColumn<Challenge, String> desc_ch_column;

    @FXML
    private TableColumn<Challenge, Integer> id_ch_column;

    @FXML
    private TextField desc_ch_id;

    @FXML
    private TextField id_chall;

    @FXML
    private TextField name_ch_id;

    @FXML
    private TextField Search_ch_field;

    @FXML
    private TableColumn<Challenge,Integer> points_column;

    @FXML
    private TextField points_id;
    private final ChallengeService cs = new ChallengeService();



    @FXML
    void AddChallenge(ActionEvent event) throws SQLException {
        try {
            // Validate all fields are not empty
            String name = name_ch_id.getText().trim();
            String description = desc_ch_id.getText().trim();
            String points = points_id.getText().trim();

            int IDChInt = Integer.parseInt(points);

            // Create a new challenge object
            cs.addd(new Challenge(name, description, IDChInt));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("A new Activity is Added");
            alert.showAndWait();
          //  clearFields();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
         //   clearFields();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Invalid points format. Please enter a valid integer.");
            alert.showAndWait();
          //  clearFields();

        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
           // clearFields();
        }

    }

    @FXML
    void initialize() throws SQLException {
        try {
            List<Challenge> challenges = cs.diplayList();
            ObservableList<Challenge> observableList = FXCollections.observableList(challenges);
            TableViewChallenge.setItems(observableList);
            id_ch_column.setCellValueFactory(new PropertyValueFactory<>("id_chall"));
            Name_ch_column.setCellValueFactory(new PropertyValueFactory<>("name_ch"));
            desc_ch_column.setCellValueFactory(new PropertyValueFactory<>("desc_ch"));
            points_column.setCellValueFactory(new PropertyValueFactory<>("points"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    @FXML
    void UpdateChallenge(ActionEvent event) {
        try {
            // Convert the text into an integer
            int IDChallengeInt = Integer.parseInt(id_chall.getText());
            int IDChInt = Integer.parseInt( points_id.getText());

            // Create a new Activity object with the converted Date
            cs.update(new Challenge( IDChallengeInt,name_ch_id.getText(), desc_ch_id.getText(),IDChInt));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("A new Activity is Added");
            alert.showAndWait();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void DeleteChallenge(ActionEvent event) {
        try {
            // Retrieve the text from the TextField for the ID
            String IDText = id_chall.getText();

            // Check if the ID text is not empty
            if (!IDText.isEmpty()) {
                // Convert the text into an integer
                int IDValue = Integer.parseInt(IDText);

                // Create a new challenge object with the provided ID
                Challenge challengeToDelete = new Challenge();
                challengeToDelete.setId_chall(IDValue);

                // Remove the challenge from the data source
                cs.delete(challengeToDelete);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Activity deleted successfully.");
                alert.showAndWait();
            } else {
                // Display an error message if the ID text is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter the activity ID.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            // Handle the case where the ID text cannot be parsed as an integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid integer for the activity ID.");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void NextReview(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ReviewChallengeManagement.fxml"));
        name_ch_id.getScene().setRoot(root);
    }

}


