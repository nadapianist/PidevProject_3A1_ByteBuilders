package tn.esprit.Controllers;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.entities.Activity;
import tn.esprit.services.ActivityService;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import tn.esprit.utils.MyDb;

public class ActivityManagement {
    Connection con;
    Statement stm;
    private Statement ste;
    PreparedStatement pste;
    @FXML
    private ResourceBundle resources;
    ////////////////////////////////////////////columns/////////////////////
    @FXML
    private TableView<Activity> TableViewActivity;
    @FXML
    private TableColumn<Activity, Integer> id_act_column;

    @FXML
    private TableColumn<Activity, Date> Date_act;

    @FXML
    private TableColumn<Activity, String> Desc_act;

    @FXML
    private TableColumn<Activity, String> Name_act;


    ////////////////////////////////////////////TextFields/////////////////////
    @FXML
    private TextArea textarea;
    @FXML
    private TextField Date_actid;

    @FXML
    private TextField Descriptionid;

    @FXML
    private TextField Nameid;

    @FXML
    private TextField Search_field;
    @FXML
    private TextField IDActivity;
    @FXML
    private TextField locationfield;
    @FXML
    private DatePicker datepick;
    @FXML
    private ComboBox<String> combo_chall;
    @FXML
    private AnchorPane anchor;


   //////////////////////////////////////////////BarChart////////////////////////

    @FXML
    private CategoryAxis x_axis;

    @FXML
    private NumberAxis y_axis;

    @FXML
    private BarChart<String, Integer> Chart;
    @FXML
    private BarChart<String, Integer> Most_searched;
    @FXML
    private ComboBox<String> combo;

    @FXML
    private URL location;
    @FXML
    private ImageView imageView;

    @FXML
    private VBox activitiesContainer;

    @FXML
    private ComboBox<String> activity_combo;

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

    private String imagePath;
    private Activity selectedActivity;
    private final ActivityService as = new ActivityService();
    ObservableList<String> sortOptions = FXCollections.observableArrayList("Name", "Description", "Date");
    private final ObservableList<String> data = FXCollections.observableArrayList();


////////////////////////////////////////////////CRUD//////////////////////////////////////////
    @FXML
    void AddActivity(ActionEvent event) throws SQLException {
        // Check if an image is selected and displayed
        if (imageView.getImage() == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select an image before adding the activity.");
            return;
        }

        // Proceed with adding the activity
        try {
            // Validate all fields are not empty
            String name = Nameid.getText().trim();
            LocalDate localDate = datepick.getValue(); // Retrieve selected date from DatePicker
            String description = Descriptionid.getText().trim();

            // Convert LocalDate to java.sql.Date
            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

            // Create a new Activity object with the converted Date and image file path
            Activity activity = new Activity(name, localDate, description, imagePath);

            // Add the activity to the database
            as.add(activity);

            // Refresh the TableView
            RefreshTableView();

            showAlert(Alert.AlertType.INFORMATION, "Confirmation", "A new Activity is Added");

            clearFields();

        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "ERROR", e.getMessage());
            clearFields();
        }
    }


    @FXML
    void initialize() throws SQLException {
        // Initialize the ComboBox with sorting options
        activity_combo.setItems(sortOptions);
// Add listener to ComboBox selection changes
        activity_combo.setOnAction(event -> {
            try {
                // Trigger sorting when the ComboBox selection changes
                SortActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        stat();

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "");

            RefreshTableView();
            TableViewActivity.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            // Add listener to TableView selection model to display date on DatePicker
            TableViewActivity.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    datepick.setValue(newSelection.getDate_act());
                }
            });
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        // Add listener to the text property of Search_field TextField
        Search_field.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // Call the search function each time the text changes
                searchActivity(newValue);
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Search Failed");
                alert.setContentText("An error occurred while searching for activities: " + ex.getMessage());
                alert.showAndWait();
            }
        });
    }


    @FXML
    void UpdateActivity(ActionEvent event) {
        // Check if an activity is selected in the TableView
        Activity selectedActivity = TableViewActivity.getSelectionModel().getSelectedItem();
        if (selectedActivity == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select an activity to update.");
            return;
        }

        try {
            LocalDate localDate = datepick.getValue();

            // Check if a date is selected
            if (localDate == null) {
                showAlert(Alert.AlertType.ERROR, "ERROR", "Please select a date");
                return;
            }

            // Convert LocalDate to java.sql.Date
            Date sqlDate = Date.valueOf(localDate);

            // Get the user ID from the TextField
            String IdText = IDActivity.getText().trim();

            // Validate user ID is not empty
            if (IdText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter the challenge ID.");
                return;
            }


            // Update the selected activity with the retrieved user ID
            selectedActivity.setName(Nameid.getText());
            selectedActivity.setDate_act(localDate);
            selectedActivity.setDescription(Descriptionid.getText());


            // Update the activity in the database
            as.update(selectedActivity);

            showAlert(Alert.AlertType.INFORMATION, "Confirmation", "Activity is updated Successfully");
            RefreshTableView();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid user ID.");
            clearFields();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "ERROR", e.getMessage());
            clearFields();
        }
    }


    @FXML
    void DeleteActivity(ActionEvent event) throws SQLException {

        // Retrieve the ID from the IDActivity TextField
        String IDText = IDActivity.getText();

        // Check if the ID text is not empty
        if (!IDText.isEmpty()) {
            try {
                // Convert the text into an integer
                int IDValue = Integer.parseInt(IDText);

                // Create a new Activity object with the provided ID
                Activity activityToDelete = new Activity();
                activityToDelete.setId_act(IDValue);

                // Delete the activity from the database
                as.delete(activityToDelete);

                // Show a confirmation message
                showAlert(Alert.AlertType.INFORMATION, "Confirmation", "Activity deleted successfully.");

                // Refresh the TableView
                RefreshTableView();
                clearFields();
            } catch (NumberFormatException e) {
                // Handle the case where the ID text cannot be parsed as an integer
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid integer for the activity ID.");
            } catch (SQLException e) {
                // Show an error message if deletion fails
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete activity: " + e.getMessage());
            }
        } else {
            // Display an error message if the ID text is empty
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter the activity ID.");
        }
    }

    ///////////////////////////////SORT +SEARCH ///////////////////////////////

    private void searchActivity(String searchText) throws SQLException {
        ActivityService as = new ActivityService();
        ObservableList<Activity> activities = FXCollections.observableArrayList(as.Search(searchText));

        Name_act.setCellValueFactory(new PropertyValueFactory<>("name"));
        Date_act.setCellValueFactory(new PropertyValueFactory<>("date_act"));
        Desc_act.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableViewActivity.setItems(activities);
    }




    @FXML
    void SortActivity() {

        ActivityService as = new ActivityService();

        ObservableList<Activity> activities = as.Sort();

        Name_act.setCellValueFactory(new PropertyValueFactory<>("name"));
        Date_act.setCellValueFactory(new PropertyValueFactory<>("date_act"));
        Desc_act.setCellValueFactory(new PropertyValueFactory<>("description"));


        TableViewActivity.setItems(activities);


    }
    ///////////// //////////////////////////// Tableview Styling  ////////
    private void RefreshTableView() {
        try {
            List<Activity> activities = as.diplayList();
            ObservableList<Activity> observableList = FXCollections.observableList(activities);
            TableViewActivity.setItems(observableList);
            id_act_column.setCellValueFactory(new PropertyValueFactory<>("id_act"));
            Name_act.setCellValueFactory(new PropertyValueFactory<>("name"));
            Date_act.setCellValueFactory(new PropertyValueFactory<>("date_act"));
            Desc_act.setCellValueFactory(new PropertyValueFactory<>("description"));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    @FXML
    void OnClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            // Single click detected
            selectedActivity = TableViewActivity.getSelectionModel().getSelectedItem();
            if (selectedActivity != null) {

                // Populate fields with selected activity's data
                Nameid.setText(selectedActivity.getName());
                Descriptionid.setText(selectedActivity.getDescription());

                // Set the IDActivity TextField with the ID of the selected activity
                IDActivity.setText(String.valueOf(selectedActivity.getId_act()));


            }
        } else {
            // If no item is selected, clear the fields and ComboBox
            clearFields();
            combo.setValue(null);
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

        Nameid.clear();
        Descriptionid.clear();
        imagePath = ""; // Clear the image path variable

    }
///////////////////////////////////GETS////////////////////////////////////////

    private String getTouristNameFromDatabase(int userId) throws SQLException {
        String userName = null;

        // Query to fetch the name of the tourist with the given user ID
        String query = "SELECT Fname FROM user WHERE UserID = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userId); // Set the user ID parameter

            ResultSet resultSet = statement.executeQuery();

            // Check if the result set has any rows
            if (resultSet.next()) {
                userName = resultSet.getString("Fname"); // Retrieve the user's name
            }
        }

        return userName;
    }
    private List<String> getTouristNamesFromDatabase() throws SQLException {
        List<String> touristNames = new ArrayList<>();
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
        return touristNames;
    }
    //////////////////////////////////STAT////////////////////////////////////


    @FXML
    private void stat() {
    }
    /////////////////////////////////////////////////////////////Import//////////////////////////

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
    //////////////////////////////////////////////////BUTTONS/////////////////
    @FXML
    void activityBTN(ActionEvent event)throws IOException {
       /* Parent root = FXMLLoader.load(getClass().getResource("/ActivityManagement.fxml"));
        activitybtn.getScene().setRoot(root);
*/
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
        Nameid.getScene().setRoot(root);

    }


}








