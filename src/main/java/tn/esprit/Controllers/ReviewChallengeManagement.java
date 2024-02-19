package tn.esprit.Controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entities.Activity;
import tn.esprit.entities.Challenge;
import tn.esprit.entities.ReviewChallenge;
import tn.esprit.services.ActivityService;
import tn.esprit.services.ReviewChallengeService;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class ReviewChallengeManagement {
    private final ReviewChallengeService rs = new ReviewChallengeService();

    @FXML
    private TableColumn<ReviewChallenge, Date> Datepub_column;

    @FXML
    private TextField ID_act_id;


    @FXML
    private TableColumn<ReviewChallenge, String> Info_column;

    @FXML
    private TableView<ReviewChallenge> TableViewReviewChallenge;

    @FXML
    private TableColumn<ReviewChallenge, String> Titlerev_column;

    @FXML
    private TextField id_chall_id;

    @FXML
    private TextField id_date_pub;

    @FXML
    private TextField id_info;

    @FXML
    private TextField id_title_rev;

    @FXML
    private TextField Search_rev_field;

    @FXML
    private TableColumn<ReviewChallenge, String> nameactivity_column;

    @FXML
    private TableColumn<ReviewChallenge, String> namechallenge_column;

    @FXML
    private TableColumn<ReviewChallenge, Integer> idact_column;

    @FXML
    private TableColumn<ReviewChallenge, Integer> id_chall_column;

    @FXML
    void AddReviewChallenge(ActionEvent event) {
        try {
            // Validate all fields are not empty
            String idActText = ID_act_id.getText().trim();
            String idChText = id_chall_id.getText().trim();
            String datePubString = id_date_pub.getText().trim();
            String titleRevText = id_title_rev.getText().trim();
            String idInfoText = id_info.getText().trim();


            int IDactInt = Integer.parseInt(idActText);
            int IDChInt = Integer.parseInt(idChText);

            // Define the date format expected
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            // Parse the string representation of the date into a Date object
            dateFormat.setLenient(false); // Set leniency to false to enforce strict date parsing
            java.util.Date utilDate = dateFormat.parse(datePubString);

            // Convert java.util.Date to java.sql.Date
            Date sqlDate = new Date(utilDate.getTime());

            // Create a new Activity object with the converted Date
            rs.addd(new ReviewChallenge(IDactInt, IDChInt, idInfoText, sqlDate, titleRevText));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("A new review is Added");
            alert.showAndWait();
            //clearFields();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
           // clearFields();

        } catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Invalid date format. Please enter date in format dd-MM-yyyy");
            alert.showAndWait();
          //  clearFields();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Invalid ID format. Please enter a valid integer.");
            alert.showAndWait();
           // clearFields();

        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
         //  clearFields();
        }

    }

    @FXML
    void initialize() throws SQLException {
        try {
            List<ReviewChallenge> challenges = rs.diplayList();
            ObservableList<ReviewChallenge> observableList = FXCollections.observableList(challenges);
            TableViewReviewChallenge.setItems(observableList);
            Info_column.setCellValueFactory(new PropertyValueFactory<>("info"));
            idact_column.setCellValueFactory(new PropertyValueFactory<>("IDActivity"));
            id_chall_column.setCellValueFactory(new PropertyValueFactory<>("ID_challenge"));
            Datepub_column.setCellValueFactory(new PropertyValueFactory<>("date_pub"));
            Titlerev_column.setCellValueFactory(new PropertyValueFactory<>("title_rev"));
            nameactivity_column.setCellValueFactory(new PropertyValueFactory<>("nameActivity"));
            namechallenge_column.setCellValueFactory(new PropertyValueFactory<>("nameChallenge"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
         @FXML
      void UpdateReviewChallenge (ActionEvent event){
             try {
                 String dateString = id_date_pub.getText();

                 // Define the date format expected
                 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                 // Parse the string representation of the date into a Date object
                 java.util.Date utilDate = dateFormat.parse(dateString);

                 // Convert java.util.Date to java.sql.Date
                 Date sqlDate = new Date(utilDate.getTime());

                 // Convert the text into an integer
                 int IDactInt = Integer.parseInt(ID_act_id.getText());
                 int IDChInt = Integer.parseInt(id_chall_id.getText());


                 // Create a new review object with the converted Date
                 rs.update(new ReviewChallenge(IDactInt, IDChInt, id_info.getText(), sqlDate, id_title_rev.getText()));
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Confirmation");
                 alert.setContentText("review updated Successfully ");
                 alert.showAndWait();

             } catch (SQLException e) {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("ERROR");
                 alert.setContentText(e.getMessage());
                 alert.showAndWait();
             } catch (ParseException e) {
                 throw new RuntimeException(e);
             }
     }
        @FXML
        void DeleteReviewChallenge (ActionEvent event){
            try {
                // Retrieve the text from the TextField for the ID
                String IDTextAct =ID_act_id.getText();
                String IDTextCh =id_chall_id.getText();

                // Check if the ID text is not empty
                if (!(IDTextAct.isEmpty())&& !(IDTextCh.isEmpty())) {
                    // Convert the text into an integer
                    int IDactInt = Integer.parseInt(ID_act_id.getText());
                    int IDChInt = Integer.parseInt(id_chall_id.getText());
                    // Create a new Activity object with the provided ID
                    ReviewChallenge reviewChallengeToDelete = new ReviewChallenge();
                    reviewChallengeToDelete.setIDActivity(IDactInt);
                    reviewChallengeToDelete.setID_challenge(IDChInt);

                    // Remove the activity from the data source
                    rs.delete(reviewChallengeToDelete);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation");
                    alert.setContentText("review challenge deleted successfully.");
                    alert.showAndWait();
                } else {
                    // Display an error message if the ID text is empty
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please enter the review ID.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                // Handle the case where the ID text cannot be parsed as an integer
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter a valid integer for the review ID.");
                alert.showAndWait();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        }



