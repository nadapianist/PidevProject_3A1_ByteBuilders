package tn.esprit.Controllers;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.ReviewChallenge;
import tn.esprit.services.ActivityService;
import tn.esprit.services.ChallengeService;
import tn.esprit.services.ReviewChallengeService;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class ReviewChallengeManagement  {
    private final ReviewChallengeService rs = new ReviewChallengeService();
////////////////////////////////////columns////////////////////////////////////
    @FXML
    private TableColumn<ReviewChallenge, Date> Datepub_column;
    @FXML
    private TableColumn<ReviewChallenge, String> Info_column;

    @FXML
    private TableView<ReviewChallenge> TableViewReviewChallenge;

    @FXML
    private TableColumn<ReviewChallenge, String> Titlerev_column;
    @FXML
    private TableColumn<ReviewChallenge, String> nameactivity_column;

    @FXML
    private TableColumn<ReviewChallenge, String> namechallenge_column;

    @FXML
    private TableColumn<ReviewChallenge, Integer> idact_column;

    @FXML
    private TableColumn<ReviewChallenge, Integer> id_chall_column;

//////////////////////////////////////text Fields/////////////////////
    @FXML
    private TextField ID_act_id;

    @FXML
    private TextField id_chall_id;
    private String imagePath;
    @FXML
    private TextField id_date_pub;

    @FXML
    private TextField id_info;

    @FXML
    private TextField id_title_rev;

    @FXML
    private TextField Search_rev_field;

    @FXML
    private ImageView imageView;
    @FXML
    private ComboBox<String> combo_id_activity;

    @FXML
    private ComboBox<String> combo_id_chall;
    @FXML
    private ComboBox<String> combo_rev;

    private ReviewChallenge selectedReview;
 /////////////////////////////////////////////////////////Buttons///////////////////////////
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

    ObservableList<String> sortOptions = FXCollections.observableArrayList("Title_rev", "Info", "Date_pub");

    //////////////////////////CRUD //////////////////////////////////
    @FXML
    void AddReviewChallenge(ActionEvent event) {
        // Check if an image is selected and displayed
        if (imageView.getImage() == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select an image before adding the activity.");
            return;
        }
        try {
            // Get the selected activity and challenge names from combo boxes
            String selectedActivityName = combo_id_activity.getValue();
            String selectedChallengeName = combo_id_chall.getValue();

            // Check if either activity or challenge is not selected
            if (selectedActivityName == null || selectedChallengeName == null) {
                throw new IllegalArgumentException("Please select both activity and challenge.");
            }

            // Get the IDs of selected activity and challenge
            int IDactInt = getActivityID(selectedActivityName);
            int IDChInt = getChallengeID(selectedChallengeName);

            // Validate all other fields
            String datePubString = id_date_pub.getText().trim();
            String titleRevText = id_title_rev.getText().trim();
            String idInfoText = id_info.getText().trim();

            if (datePubString.isEmpty() || titleRevText.isEmpty() || idInfoText.isEmpty()) {
                throw new IllegalArgumentException("All fields must be filled out.");
            }

            // Validate title contains only letters
            if (!titleRevText.matches("[a-zA-Z]+")) {
                throw new IllegalArgumentException("Title must contain only letters.");
            }

            // Define the date format expected
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            // Parse the string representation of the date into a Date object
            dateFormat.setLenient(false); // Set leniency to false to enforce strict date parsing
            java.util.Date utilDate = dateFormat.parse(datePubString);

            // Convert java.util.Date to java.sql.Date
            Date sqlDate = new Date(utilDate.getTime());

            // Create a new Activity object with the converted Date
            rs.addd(new ReviewChallenge(IDactInt, IDChInt, idInfoText, sqlDate, titleRevText,imagePath));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("A new review is Added");
            alert.showAndWait();
            RefreshTableView();
            clearFields();

        } catch (SQLException e) {
            handleException("SQL Error", e.getMessage());
        } catch (ParseException e) {
            handleException("Parse Error", "Invalid date format. Please enter date in format dd-MM-yyyy");
        } catch (NumberFormatException e) {
            handleException("Number Format Error", "Invalid ID format. Please enter a valid integer.");
        } catch (IllegalArgumentException e) {
            handleException("Validation Error", e.getMessage());
        }

    }


    @FXML
    void initialize() throws SQLException {
        // Initialize the ComboBox with sorting options
        combo_rev.setItems(sortOptions);

        try {

            RefreshTableView();
            combo_mod_act();
            combo_mod_ch();
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
        // Add listener to the text property of Search_rev_field TextField
        Search_rev_field.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // Call the search function each time the text changes
                SearchReview(newValue);
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Search Failed");
                alert.setContentText("An error occurred while searching for reviews: " + ex.getMessage());
                alert.showAndWait();
            }
        });
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
            rs.update(new ReviewChallenge(IDactInt, IDChInt, id_info.getText(), sqlDate, id_title_rev.getText(),imagePath));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("review updated Successfully ");
            alert.showAndWait();
            RefreshTableView();

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

            // Get the selected activity and challenge names from combo boxes
            String selectedActivityName = combo_id_activity.getValue();
            String selectedChallengeName = combo_id_chall.getValue();
            // Check if the ID text is not empty
             if (selectedActivityName != null || selectedChallengeName != null) {
                 // Get the IDs of selected activity and challenge
                 int IDactInt = getActivityID(selectedActivityName);
                 int IDChInt = getChallengeID(selectedChallengeName);

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
                RefreshTableView();
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
///////////////////////////////////////////search +sort //////////////////
void SearchReview(String searchTerm) throws SQLException {
    ReviewChallengeService rs = new ReviewChallengeService();
    ObservableList<ReviewChallenge> reviews = FXCollections.observableArrayList(rs.Search(searchTerm));

    // Update the TableView with the search results
    TableViewReviewChallenge.setItems(reviews);
}


    @FXML
    void SortReview(ActionEvent event) {
       /* ReviewChallengeService rs = new ReviewChallengeService();

        ObservableList<ReviewChallenge> reviews = rs.Sort();

        // Set the data model for the TableView with the sorted data
        TableViewReviewChallenge.setItems(reviews);

        // Update the columns to display the correct values
        Info_column.setCellValueFactory(new PropertyValueFactory<>("info"));
        Datepub_column.setCellValueFactory(new PropertyValueFactory<>("date_pub"));
        Titlerev_column.setCellValueFactory(new PropertyValueFactory<>("title_rev"));

        // Set cell value factory for nameactivity_column and namechallenge_column
        nameactivity_column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNameActivity()));
        namechallenge_column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNameChallenge()));

        // Refresh the TableView to reflect the changes
        TableViewReviewChallenge.refresh();*/
        String selectedCriteria = combo_rev.getValue();
        if (selectedCriteria != null) {
            try {
                switch (selectedCriteria) {
                    case "Date":
                        sortByDate();
                        break;
                    case "Title":
                        sortByTitle();
                        break;
                    case "Info":
                        sortByInfo();
                        break;
                    default:
                        // Default to sorting by date if no criteria matches
                        sortByDate();
                        break;
                }
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Sort Failed");
                alert.setContentText("An error occurred while sorting reviews: " + ex.getMessage());
                alert.showAndWait();
            }
        }
    }
    private void sortByDate() throws SQLException {
        ReviewChallengeService rs = new ReviewChallengeService();
        ObservableList<ReviewChallenge> reviews = rs.Sort();

        // Update the TableView with the sorted results
        TableViewReviewChallenge.setItems(reviews);
    }

    private void sortByTitle() throws SQLException {
        ReviewChallengeService rs = new ReviewChallengeService();
        ObservableList<ReviewChallenge> reviews = rs.SortTitle();

        // Update the TableView with the sorted results
        TableViewReviewChallenge.setItems(reviews);
    }

    private void sortByInfo() throws SQLException {
        ReviewChallengeService rs = new ReviewChallengeService();
        ObservableList<ReviewChallenge> reviews = rs.SortInfo();

        // Update the TableView with the sorted results
        TableViewReviewChallenge.setItems(reviews);
    }
////////////////////////////////////////CLEARING////////////////////////////
private void handleException(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
    clearFields();
}
    private void clearFields() {
        //ID_act_id.clear();
        //id_chall_id.clear();
        id_date_pub.clear();
        id_info.clear();
        id_title_rev.clear();
    }
    ///////////// //////////////////////////// Tableview Styling  ////////
    private void RefreshTableView() {
        try {
            List<ReviewChallenge> reviewChallenges = rs.diplayList();
            ObservableList<ReviewChallenge> observableList = FXCollections.observableList(reviewChallenges);
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
    void OnClickReview(MouseEvent event) {
        if (event.getClickCount() == 1) {
            // Single click detected
            selectedReview = TableViewReviewChallenge.getSelectionModel().getSelectedItem();
            if (selectedReview != null) {
                id_title_rev.setText(selectedReview.getTitle_rev());
                id_info.setText(String.valueOf(selectedReview.getInfo()));
                id_date_pub.setText(String.valueOf(selectedReview.getDate_pub()));

            } else {
                clearFields();
            }
        }
    }
////////////////////////////////gets//////////////////////////////

    // Helper method to get the ID of selected activity from its name
    private int getActivityID(String activityName) throws SQLException {
        ActivityService activityService = new ActivityService();
        return activityService.getActivityIDByName(activityName);
    }


    // Helper method to get the ID of selected challenge from its name
    private int getChallengeID(String challengeName) throws SQLException {
        ChallengeService challengeService = new ChallengeService();
        return challengeService.getChallengeIDByName(challengeName);
    }
    // Helper method to handle exceptions and show error alerts

    void combo_mod_act() {
        ActivityService activityService = new ActivityService();
        List<String> activityNames = activityService.getAllActivityNames();
        ObservableList<String> combo = FXCollections.observableArrayList(activityNames);
        combo_id_activity.getItems().clear();
        combo_id_activity.getItems().addAll(combo);
    }
    void combo_mod_ch() {
        ChallengeService challengeService = new ChallengeService();
        List<String> challengeNames = challengeService.getAllChallengeNames();
        ObservableList<String> combo = FXCollections.observableArrayList(challengeNames);
        combo_id_chall.getItems().clear();
        combo_id_chall.getItems().addAll(combo);
    }
    ////////////////////////////////////////////////////////////
    @FXML
    void importbtn(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(((Stage) imageView.getScene().getWindow()));

        if (selectedFile != null) {
            // Load the selected image into the ImageView
            try {
                imagePath = selectedFile.toURI().toString();
                Image image = new Image(imagePath);
                imageView.setImage(image);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Failed to load the selected image.");
                alert.showAndWait();
            }
        }
    }
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    //////////////////////////////////////////////////Buttons ///////////////////////////////////////////
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
        /*Parent root = FXMLLoader.load(getClass().getResource("/ReviewChallengeManagement.fxml"));
        reviewbtn.getScene().setRoot(root);*/

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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ConsultActivities.fxml")));
        Search_rev_field.getScene().setRoot(root);

    }

}







