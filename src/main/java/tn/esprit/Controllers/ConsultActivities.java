package tn.esprit.Controllers;
import com.github.sarxos.webcam.WebcamPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tn.esprit.entities.Activity;
import tn.esprit.entities.Challenge;
import tn.esprit.services.ActivityService;
import tn.esprit.services.ChallengeService;
import tn.esprit.utils.MyDb;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;


public class ConsultActivities implements Initializable {

    Connection con;
    Statement stm;
    PreparedStatement pste;

    @FXML
    private ComboBox<String> combo_act_criteria;
    @FXML
    private ImageView logo;

    @FXML
    private TextField search_challengeid;

    @FXML
    private TextField searchid;

    @FXML
    private ComboBox<String> combo_challenge;

    @FXML
    private VBox vbox_challenge;
    @FXML
    private VBox vbox;

    @FXML
    private VBox box;
    @FXML
    private ComboBox<String> combobox;

    @FXML
    private ComboBox<String> combo_criteria;

    private int selectedActivityId = 0;
    private int selectedChallengeId = 0;

    ////////////////////menu buttons ////////////////////////////////////////////////////////

    @FXML
    void achievements(ActionEvent event)  throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ConsultActivities.fxml"));
        combobox.getScene().setRoot(root);
    }

    @FXML
    void forum(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Forumuser.fxml"));
        combobox.getScene().setRoot(root);
    }

    @FXML
    void home(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Home.fxml"));
        combobox.getScene().setRoot(root);
    }

    @FXML
    void locations(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LocationFront.fxml"));
        combobox.getScene().setRoot(root);
    }


    @FXML
    void services(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/HostelFront.fxml"));
        combobox.getScene().setRoot(root);

    }

    ///////////////////////////////sort+search  Activty /////////////////////////////////////////////
    @FXML
    void Sort_activity(ActionEvent event) {

        String selectedCriteria = combo_act_criteria.getValue();
        if (selectedCriteria != null) {
            // Clear existing items in the ComboBox
            combobox.getItems().clear();

            // Populate the ComboBox based on selected sorting criteria
            ObservableList<Activity> sortedactivities = null;
            switch (selectedCriteria) {
                case "Name":
                    sortedactivities = activityService.Sort();
                    break;
                case "Description":
                    sortedactivities = activityService.sortByDescription();
                    break;
                case "Date_act":
                    sortedactivities = activityService.Sort();
                    break;
                default:
                    // Handle invalid criteria selection
                    break;
            }


            if (sortedactivities != null) {
                for (Activity activity : sortedactivities) {
                    combobox.getItems().add(activity.getName());
                }
            }
        }
    }

    @FXML
    void Seach_activity(ActionEvent event) {
        String location = searchid.getText().trim();
        if (!location.isEmpty()) {
            try {
                Connection connection = MyDb.getInstance().getCon();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT a.name FROM activity a JOIN association_activity_location al ON a.id_act = al.Activity JOIN location l ON al.Location = l.id WHERE l.Name = ?");
                preparedStatement.setString(1, location);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Clear existing content
                combobox.getItems().clear();

                while (resultSet.next()) {
                    String activityName = resultSet.getString("name");
                    combobox.getItems().add(activityName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Handle empty location field
        }
    }

    /////////////////////////////////////////////search + sort challenge ///////////////////////////////////
    @FXML
    void sort_challenge(ActionEvent event) {

        String selectedCriteria = combo_criteria.getValue();
        if (selectedCriteria != null) {
            // Clear existing items in the ComboBox
            combo_challenge.getItems().clear();

            // Populate the ComboBox based on selected sorting criteria
            ObservableList<Challenge> sortedChallenges = null;
            switch (selectedCriteria) {
                case "Name":
                    sortedChallenges = challengeService.Sort();
                    break;
                case "Description":
                    sortedChallenges = challengeService.sortByDescription();
                    break;
                case "Points":
                    sortedChallenges = challengeService.sortByPoints();
                    break;
                default:
                    // Handle invalid criteria selection
                    break;
            }

            // Populate ComboBox with sorted challenge names
            if (sortedChallenges != null) {
                for (Challenge challenge : sortedChallenges) {
                    combo_challenge.getItems().add(challenge.getName_ch());
                }
            }
        }


    }
        // Helper method to populate ComboBox with challenge names
        private void populateComboBox(ObservableList<Challenge> sortedChallenges) {
            for (Challenge challenge : sortedChallenges) {
                combo_challenge.getItems().add(challenge.getName_ch());
            }
        }
    @FXML
    void Dashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ActivityManagement.fxml"));
        combobox.getScene().setRoot(root);

    }


    @FXML
    void search_challenge(ActionEvent event) {
        updateChallengeVBox();
        }

    private void updateChallengeVBox() {
        String challengeName = search_challengeid.getText().trim();
        if (!challengeName.isEmpty()) {
            try {
                Connection connection = MyDb.getInstance().getCon();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM challenge WHERE name_ch LIKE ?");
                preparedStatement.setString(1, challengeName + "%"); // Using LIKE for partial match
                ResultSet resultSet = preparedStatement.executeQuery();

                // Clear existing content
                vbox_challenge.getChildren().clear();

                // Display each matching challenge in the vbox_challenge
                while (resultSet.next()) {
                    // Clear existing content
                    vbox_challenge.getChildren().clear();

                    // Name label
                    String nameText = "*Name: ";
                    Label nameLabel = new Label(nameText);
                    nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;"); // Increase font size

                    // Name value
                    Label nameValueLabel = new Label(resultSet.getString("name_ch"));
                    nameValueLabel.setStyle("-fx-font-size: 17px;"); // Increase font size for name value

                    // Description label
                    String descriptionText = "*Description: ";
                    Label descriptionTextLabel = new Label(descriptionText);
                    descriptionTextLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;"); // Increase font size

                    // Description value
                    Label descriptionValueLabel = new Label(resultSet.getString("desc_ch"));
                    descriptionValueLabel.setStyle("-fx-font-size: 17px;"); // Increase font size for description
                    descriptionValueLabel.setWrapText(true); // Enable wrapping text to display description on multiple lines

                    // Points label
                    String pointsText = "*Points: ";
                    Label pointsTextLabel = new Label(pointsText);
                    pointsTextLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;"); // Increase font size

                    // Points value
                    Label pointsValueLabel = new Label(String.valueOf(resultSet.getInt("points")));
                    pointsValueLabel.setStyle("-fx-font-size: 17px;"); // Increase font size for points value

                    // Add border to the VBox with color #156d73
                    vbox_challenge.setStyle("-fx-padding: 20px 0 0 20px; " + // Padding from ComboBox
                            "-fx-border-color: #156d73; " + // Border color
                            "-fx-border-width: 2px; " + // Border width
                            "-fx-border-radius: 5px;"); // Border radius

                    // Set spacing between lines in VBox
                    VBox.setMargin(nameLabel, new Insets(0, 0, 10, 0)); // Bottom margin for nameLabel
                    VBox.setMargin(nameValueLabel, new Insets(0, 0, 10, 0)); // Bottom margin for nameValueLabel
                    VBox.setMargin(descriptionTextLabel, new Insets(0, 0, 10, 0)); // Bottom margin for descriptionTextLabel
                    VBox.setMargin(descriptionValueLabel, new Insets(0, 0, 10, 0)); // Bottom margin for descriptionValueLabel
                    VBox.setMargin(pointsTextLabel, new Insets(0, 0, 10, 0)); // Bottom margin for pointsTextLabel
                    VBox.setMargin(pointsValueLabel, new Insets(0, 0, 10, 0)); // Bottom margin for pointsValueLabel

                    vbox_challenge.getChildren().addAll(nameLabel, nameValueLabel, descriptionTextLabel, descriptionValueLabel, pointsTextLabel, pointsValueLabel);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Handle empty challenge name field
        }
    }

    private void populateComboBox() {
        try {
            Connection connection = MyDb.getInstance().getCon();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM activity");

            while (resultSet.next()) {
                String activityName = resultSet.getString("name");
                combobox.getItems().add(activityName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private ChallengeService challengeService;
    private ActivityService activityService;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        activityService=new ActivityService();
        // Initialize ChallengeService
        challengeService = new ChallengeService();
        // Add listener to the search text field
        search_challengeid.textProperty().addListener((observable, oldValue, newValue) -> {
            updateChallengeVBox(); // Update VBox on text change
        });

        // Populate challenge VBox on initialization
        updateChallengeVBox();
        populateComboBox(); // Populate the ComboBox with activity names
        populateChallengeComboBox(); // Populate the ComboBox with challenge names
        // Add listener for ComboBox selection
        combobox.setOnAction(event -> displayActivityInfo());
        combo_challenge.setOnAction(event -> displayChallengeInfo()); // Add listener for challenge ComboBox


    }

    private void displayActivityInfo() {
        try {
            String selectedActivity = combobox.getValue();
            if (selectedActivity != null) {
                Connection connection = MyDb.getInstance().getCon();
                Statement statement = connection.createStatement();
                String query = "SELECT * FROM activity WHERE name = '" + selectedActivity + "'";
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    // Clear existing content
                    box.getChildren().clear();

                    String nameText = "*Name: " + resultSet.getString("name");
                    Label nameLabel = new Label(nameText.substring(0, 6)); // "Name: "
                    nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;"); // Increase font size

                    // Name value
                    Label nameValueLabel = new Label(nameText.substring(6));
                    nameValueLabel.setStyle("-fx-font-size: 17px;"); // Increase font size for name value

                    String activityDateText = "*Activity Date: " + resultSet.getString("date_act");
                    Label activityDateLabel = new Label(activityDateText.substring(0, 15)); // "Activity Date: "
                    activityDateLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;"); // Increase font size

                    // Activity Date value
                    Label activityDateValueLabel = new Label(activityDateText.substring(15));
                    activityDateValueLabel.setStyle("-fx-font-size: 17px;"); // Increase font size for activity date value

                    Label descriptionText = new Label("*Description: ");
                    descriptionText.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;"); // Increase font size

                    // Description value
                    Label descriptionValueLabel = new Label(resultSet.getString("description"));
                    descriptionValueLabel.setStyle("-fx-font-size: 17px;"); // Increase font size for description
                    descriptionValueLabel.setWrapText(true); // Enable wrapping text to display description on multiple lines

                    // Label for image
                    Label imageLabel = new Label("*Image: ");
                    imageLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;"); // Increase font size

                    // Load image
                    String imagePath = resultSet.getString("image");
                    Image image = new Image(imagePath);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(150); // Set width of the image
                    imageView.setFitHeight(150); // Set height of the image
                    imageView.setPreserveRatio(true); // Preserve aspect ratio of the image

                    // Add border to the VBox with color #156d73
                    box.setStyle("-fx-padding: 20px 20px 30px 20px; " + // Padding from ComboBox
                            "-fx-border-color: #156d73; " + // Border color
                            "-fx-border-width: 2px; " + // Border width
                            "-fx-border-radius: 5px;"); // Border radius

                    // Set text color and font size in ComboBox
                    combobox.setStyle("-fx-text-fill: red; -fx-font-size: 15px;");

                    // Set color of selected item in ComboBox
                    combobox.setStyle("-fx-background-color: #88d711; -fx-font-size: 15px;");

                    // Set spacing between lines in VBox
                    VBox.setMargin(nameLabel, new Insets(0, 0, 10, 0)); // Bottom margin for nameLabel
                    VBox.setMargin(activityDateLabel, new Insets(0, 0, 10, 0)); // Bottom margin for activityDateLabel
                    VBox.setMargin(descriptionText, new Insets(0, 0, 10, 0)); // Bottom margin for descriptionText

                    // Add nodes to the VBox
                    box.getChildren().addAll(nameLabel, nameValueLabel, activityDateLabel, activityDateValueLabel, descriptionText, descriptionValueLabel, imageLabel, imageView);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void populateChallengeComboBox() {
        try {
            Connection connection = MyDb.getInstance().getCon();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name_ch FROM challenge");

            while (resultSet.next()) {
                String challengeName = resultSet.getString("name_ch");
                combo_challenge.getItems().add(challengeName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Other methods...

    private int getSelectedActivityId() {
        return selectedActivityId;
    }

    private int getSelectedChallengeId() {
        return selectedChallengeId;
    }

    private void displayChallengeInfo() {
        try {
            String selectedChallenge = combo_challenge.getValue();
            if (selectedChallenge != null) {
                Connection connection = MyDb.getInstance().getCon();
                Statement statement = connection.createStatement();
                String query = "SELECT * FROM challenge WHERE name_ch = '" + selectedChallenge + "'";
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    // Clear existing content
                    vbox_challenge.getChildren().clear();

                    // Name label
                    String nameText = "*Name: ";
                    Label nameLabel = new Label(nameText);
                    nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;"); // Increase font size

                    // Name value
                    Label nameValueLabel = new Label(resultSet.getString("name_ch"));
                    nameValueLabel.setStyle("-fx-font-size: 17px;"); // Increase font size for name value

                    // Description label
                    String descriptionText = "*Description: ";
                    Label descriptionTextLabel = new Label(descriptionText);
                    descriptionTextLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;"); // Increase font size

                    // Description value
                    Label descriptionValueLabel = new Label(resultSet.getString("desc_ch"));
                    descriptionValueLabel.setStyle("-fx-font-size: 17px;"); // Increase font size for description
                    descriptionValueLabel.setWrapText(true); // Enable wrapping text to display description on multiple lines

                    // Points label
                    String pointsText = "*Points: ";
                    Label pointsTextLabel = new Label(pointsText);
                    pointsTextLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;"); // Increase font size

                    // Points value
                    Label pointsValueLabel = new Label(String.valueOf(resultSet.getInt("points")));
                    pointsValueLabel.setStyle("-fx-font-size: 17px;"); // Increase font size for points value

                    // Add border to the VBox with color #156d73
                    vbox_challenge.setStyle("-fx-padding: 20px 0 0 20px; " + // Padding from ComboBox
                            "-fx-border-color: #156d73; " + // Border color
                            "-fx-border-width: 2px; " + // Border width
                            "-fx-border-radius: 5px;"); // Border radius

                    // Set text color and font size in ComboBox
                    combo_challenge.setStyle("-fx-text-fill: red; -fx-font-size: 15px;");

                    // Set color of selected item in ComboBox
                    combo_challenge.setStyle("-fx-background-color: #88d711; -fx-font-size: 15px;");

                    // Set spacing between lines in VBox
                    VBox.setMargin(nameLabel, new Insets(0, 0, 10, 0)); // Bottom margin for nameLabel
                    VBox.setMargin(nameValueLabel, new Insets(0, 0, 10, 0)); // Bottom margin for nameValueLabel
                    VBox.setMargin(descriptionTextLabel, new Insets(0, 0, 10, 0)); // Bottom margin for descriptionTextLabel
                    VBox.setMargin(descriptionValueLabel, new Insets(0, 0, 10, 0)); // Bottom margin for descriptionValueLabel
                    VBox.setMargin(pointsTextLabel, new Insets(0, 0, 10, 0)); // Bottom margin for pointsTextLabel
                    VBox.setMargin(pointsValueLabel, new Insets(0, 0, 10, 0)); // Bottom margin for pointsValueLabel

                    vbox_challenge.getChildren().addAll(nameLabel, nameValueLabel, descriptionTextLabel, descriptionValueLabel, pointsTextLabel, pointsValueLabel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void watch(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Video.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Video Player");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Validate(ActionEvent event) {
        String selectedActivity = combobox.getValue();
        String selectedChallenge = combo_challenge.getValue();

        // Check if both selections are not null
        if (selectedActivity != null && selectedChallenge != null) {
            try {
                int activityId = getActivityId(selectedActivity);
                int challengeId = getChallengeId(selectedChallenge);

                // Load review.fxml and pass activityId and challengeId to the controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/review.fxml"));
                Parent root = loader.load();
                ReviewManagementFront controller = loader.getController();
                controller.initData(activityId, challengeId);

                // Get the current stage and set the new scene onto the stage
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select both activity and challenge.");
        }
    }

    // Method to get the activity ID from its name
    private int getActivityId(String selectedActivityName) {
        int activityId = -1;
        try {
            Connection connection = MyDb.getInstance().getCon();
            PreparedStatement statement = connection.prepareStatement("SELECT id_act FROM activity WHERE name = ?");
            statement.setString(1, selectedActivityName);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                activityId = rs.getInt("id_act");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activityId;
    }

    // Method to get the challenge ID from its name
    private int getChallengeId(String selectedChallengeName) {
        int challengeId = -1;
        try {
            Connection connection = MyDb.getInstance().getCon();
            PreparedStatement statement = connection.prepareStatement("SELECT id_chall FROM challenge WHERE name_ch = ?");
            statement.setString(1, selectedChallengeName);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                challengeId = rs.getInt("id_chall");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return challengeId;
    }

    public void generate(ActionEvent event) {

    }
    public void generatee(String location) {

    }
    @FXML
    void calendar(ActionEvent event) throws IOException {


    }

    @FXML
    void cam(ActionEvent event) {

    }

}






