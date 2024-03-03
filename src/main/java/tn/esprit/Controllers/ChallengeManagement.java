package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.esprit.entities.Activity;
import tn.esprit.entities.Challenge;
import tn.esprit.services.ActivityService;
import tn.esprit.services.ChallengeService;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChallengeManagement {
    ////////////////////////////////////////////columns/////////////////////

    @FXML
    private TableColumn<Challenge, String> Name_ch_column;

    @FXML
    private TableView<Challenge> TableViewChallenge;

    @FXML
    private TableColumn<Challenge, String> desc_ch_column;

    @FXML
    private TableColumn<Challenge, Integer> id_ch_column;

    @FXML
    private TableColumn<Challenge,Integer> points_column;

    ///////////// ////////////////////////////text fileds////////

    @FXML
    private TextField desc_ch_id;

    @FXML
    private TextField id_chall;

    @FXML
    private TextField name_ch_id;

    @FXML
    private TextField Search_ch_field;

    @FXML
    private TextField points_id;

    ///////////// ////////////////////////////Buttons////////

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

    @FXML
    private ComboBox<String> combo_chall;
    private Challenge selectedChallenge;
    ObservableList<String> sortOptionss = FXCollections.observableArrayList("Name", "Description", "Points");
    private final ChallengeService cs = new ChallengeService();


    ///////////// ////////////////////////////CRUD ////////

    @FXML
    void AddChallenge(ActionEvent event) throws SQLException {
        try {
            // Validate all fields are not empty
            String name = name_ch_id.getText().trim();
            String description = desc_ch_id.getText().trim();
            String points = points_id.getText().trim();

            if (name.isEmpty() || description.isEmpty() || points.isEmpty()) {
                throw new IllegalArgumentException("All fields must be filled out.");
            }

            // Validate name input
            if (!name.matches("[a-zA-Z ]+")) {
                throw new IllegalArgumentException("Name must contain only letters.");
            }

            // Get the user ID from the TextField
            String IdText = id_chall.getText().trim();


            // Validate the points input
            int pts = Integer.parseInt(points);

            // Create a new challenge object
            cs.addd(new Challenge( name, description,pts));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("A new Challenge is Added");
            alert.showAndWait();
            RefreshTableView();
            clearFields();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Invalid points format. Please enter a valid integer.");
            alert.showAndWait();
            clearFields();

        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            clearFields();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            clearFields();
        }
    }


    @FXML
    void initialize() throws SQLException {
        // Initialize the ComboBox with sorting options
        combo_chall.setItems(sortOptionss);
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "");
            RefreshTableView();
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
        // Add listener to the text property of Search_ch_field TextField
        Search_ch_field.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // Call the search function each time the text changes
                Search_challenge(newValue);
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Search Failed");
                alert.setContentText("An error occurred while searching for challenges: " + ex.getMessage());
                alert.showAndWait();
            }
        });
    }
    private List<String> getTouristNamesFromDatabase() throws SQLException {
        List<String> touristNames = new ArrayList<>();
        Connection con = null; // Define connection variable
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "");
            // Query to fetch names of tourists from the database
            String query = "SELECT Fname FROM user";
            try (PreparedStatement statement = con.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                // Iterate through the result set and add names to the list
                while (resultSet.next()) {
                    String name = resultSet.getString("Fname");
                    touristNames.add(name);
                }
            }
        } finally {
            // Close the connection in the finally block to ensure it's always closed
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle exception appropriately
                }
            }
        }
        return touristNames;
    }

    @FXML
    void UpdateChallenge(ActionEvent event) throws SQLException {
        // Check if a challenge is selected in the TableView
        Challenge selectedChallenge = TableViewChallenge.getSelectionModel().getSelectedItem();
        if (selectedChallenge == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a challenge to update.");
            return;
        }

        try {
            // Validate all fields are not empty
            String name = name_ch_id.getText().trim();
            String description = desc_ch_id.getText().trim();
            String points = points_id.getText().trim();

            if (name.isEmpty() || description.isEmpty() || points.isEmpty()) {
                throw new IllegalArgumentException("All fields must be filled out.");
            }

            // Validate name input
            if (!name.matches("[a-zA-Z ]+")) {
                throw new IllegalArgumentException("Name must contain only letters.");
            }

            // Get the user ID from the TextField
            String userIdText = id_chall.getText().trim();

            // Validate user ID is not empty
            if (userIdText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter the user ID.");
                return;
            }

            // Validate the points input
            int pointsInt = Integer.parseInt(points);

            // Update the selected challenge
            selectedChallenge.setId_chall(Integer.parseInt(id_chall.getText()));
            selectedChallenge.setName_ch(name);
            selectedChallenge.setDesc_ch(description);
            selectedChallenge.setPoints(pointsInt);

            // Update the challenge in the database
            cs.update(selectedChallenge);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Challenge is updated Successfully");
            alert.showAndWait();
            RefreshTableView();
            clearFields();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Invalid input format. Please enter valid information.");
            alert.showAndWait();
            clearFields();

        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            clearFields();
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
                RefreshTableView();
                clearFields();
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

    ///////////// ////////////////////////////Search +sort ////////
    @FXML
    void Search_challenge(String searchTerm) throws SQLException {

        ChallengeService as = new ChallengeService();
        ObservableList<Challenge> challenges = FXCollections.observableArrayList(as.Search(searchTerm));

        Name_ch_column.setCellValueFactory(new PropertyValueFactory<>("name_ch"));
        desc_ch_column.setCellValueFactory(new PropertyValueFactory<>("desc_ch"));
        points_column.setCellValueFactory(new PropertyValueFactory<>("points"));

        TableViewChallenge.setItems(challenges);


    }

    @FXML
    void SortChallenge(ActionEvent event) {

        // Get the selected sorting criteria from the ComboBox
        String selectedCriteria = combo_chall.getValue();

        try {
            // Retrieve the sorted list of challenges based on the selected criteria
            ObservableList<Challenge> sortedChallenges = cs.Sorte(selectedCriteria);

            // Set the sorted challenges to the TableView
            TableViewChallenge.setItems(sortedChallenges);
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Sort Failed");
            alert.setContentText("An error occurred while sorting challenges: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    ///////////// //////////////////////////// Tableview Styling  ////////
    @FXML
    void OnClickChallenge(MouseEvent event) {
        if (event.getClickCount() == 1) {
            // Single click detected
            selectedChallenge = TableViewChallenge.getSelectionModel().getSelectedItem();
            if (selectedChallenge != null) {
                id_chall.setText(String.valueOf(selectedChallenge.getId_chall()));
                name_ch_id.setText(selectedChallenge.getName_ch());
                desc_ch_id.setText(selectedChallenge.getDesc_ch());
                points_id.setText(String.valueOf(selectedChallenge.getPoints()));

            } else {
                clearFields();
            }
        }
    }
    private void RefreshTableView() {
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
    ///////////// //////////////////////////// CLEARING  ////////
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        id_chall.clear();
        name_ch_id.clear();
        desc_ch_id.clear();
        points_id.clear();
    }
    //////////////////////////////////////////////////BUTTONS//////////
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

    @FXML
    void logout(ActionEvent event) throws IOException {
        Parent root =FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ConsultActivities.fxml")));
        name_ch_id.getScene().setRoot(root);

    }

}


