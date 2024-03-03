package tn.esprit.Controllers;

import com.github.sarxos.webcam.Webcam;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReviewManagementFront implements Initializable {
    // Instance variables to store activity ID and challenge ID

    @FXML
    private Label activityLabel;

    @FXML
    private ImageView logo;
    @FXML
    private ScrollPane scrollpane;

    @FXML
    private ImageView imageView;
    private String imagePath;
   // @FXML
  //  private Label activityLabel;
   Connection con;
    Statement stm;
    PreparedStatement pste;
    @FXML
    private Label challengeLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Initialize database connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "");
            // Initialize selected review variables
            selectedActivityID = 0;
            selectedChallengeID = 0;
            selectedReviewInfo = "";
            selectedReviewTitle = "";
            displayReviews();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Initialize the controller
    }
    private int selectedActivityID;
    private int selectedChallengeID;
   /* public void initData(int selectedActivityId, int selectedChallengeId) {
        this.selectedActivityID = selectedActivityId;
        this.selectedActivityID = selectedChallengeId;
    }*/
    public void initData(int selectedActivityId, int selectedChallengeId) {
        this.selectedActivityID = selectedActivityId;
        this.selectedChallengeID = selectedChallengeId;
        // Initialize database connection
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void addReview(int activityId, int challengeId, String info, String datePub, String title,String image) {

        // Check if an image is selected and displayed
        if (imageView.getImage() == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select an image before adding the activity.");
            return;
        }
        try {
            // Filter bad words from the info parameter
            String filteredInfo = filterBadWords(info);

            // Parse date string into a java.util.Date object
            // Define the date format expected
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            // Parse the string representation of the date into a Date object
            dateFormat.setLenient(false); // Set leniency to false to enforce strict date parsing
            java.util.Date utilDate = dateFormat.parse(datePub);

            // Convert java.util.Date to java.sql.Date
            Date sqlDate = new Date(utilDate.getTime());
            // Prepare the INSERT statement
            String insertQuery = "INSERT INTO reviewchallenge (IDActivity, ID_challenge, Info, Date_pub, Title_rev,image) VALUES (?, ?, ?, ?, ?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setInt(1, activityId);
            preparedStatement.setInt(2, challengeId);
            preparedStatement.setString(3, filteredInfo); // Insert filtered info
            preparedStatement.setDate(4,sqlDate );
            preparedStatement.setString(5, title);
            preparedStatement.setString(6, image);

            // Execute the statement
            preparedStatement.executeUpdate();

            // Close the statement
            preparedStatement.close();
            clearFields();
            // Show confirmation message
            System.out.println("Review added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQL exception appropriately
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    // Method to add review using the provided data
    public void addReview(String info, String datePub, String title) {
        addReview(selectedActivityID, selectedChallengeID, info, datePub, title,imagePath);

    }
    private int selectedReviewId;
    private String selectedReviewInfo;
    private String selectedReviewTitle;

    private void displayReviews() throws SQLException {
        List<VBox> reviewVBoxes = new ArrayList<>();

        // Retrieve reviews from the database
        String query = "SELECT * FROM reviewchallenge";
        try (PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Create a VBox for each review
                VBox reviewVBox = new VBox();
                reviewVBox.setStyle("-fx-background-color: lightgrey; " +
                        "-fx-padding: 10px; " +
                        "-fx-border-width: 3px; " +
                        "-fx-border-radius: 20px; " +  // Rounded border
                        "-fx-pref-width: 150px; " +  // Adjust minimum width
                        "-fx-pref-height: 30px; " +  // Adjust minimum height
                        "-fx-font-size: 14px; " +  // Increase font size
                        "-fx-spacing: 10px;");  // Spacing between VBoxes

                // Add margin to the VBox
                reviewVBox.setMargin(reviewVBox, new Insets(10, 0, 0, 0)); // Adjust top margin as needed

                // Set the width and height of VBox to match the ScrollPane
                reviewVBox.prefWidthProperty().bind(scrollpane.widthProperty());
                reviewVBox.prefHeightProperty().bind(scrollpane.heightProperty());

                // Add spacing between VBox
                reviewVBox.setSpacing(10);

                // Populate VBox with review information
                int activityId = resultSet.getInt("IDActivity");
                int challengeId = resultSet.getInt("ID_challenge");
                String info = resultSet.getString("Info");
                String datePub = resultSet.getString("Date_pub");
                String title = resultSet.getString("Title_rev");

                // Load image
                String imageURL = resultSet.getString("image");
                Image image = new Image(imageURL);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100); // Set width of the image
                imageView.setPreserveRatio(true); // Preserve image aspect ratio

                // Create labels for title and its value
                Label titleLabel = new Label("Title:");
                Label titleValueLabel = new Label(title);
                titleValueLabel.setWrapText(true); // Enable wrapping text

                // Apply CSS styling to "Title:" label to make it bold and larger
                titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

                // Create labels for "Info" and its value
                Label infoLabel = new Label("Info:");
                Label infoValueLabel = new Label(info);
                infoValueLabel.setWrapText(true); // Enable wrapping text

                // Apply CSS styling to "Info:" label to make it bold and larger
                infoLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

                // Create labels for "Date_pub" and its value
                Label dateLabel = new Label("Date_pub:");
                Label dateValueLabel = new Label(datePub);
                dateValueLabel.setWrapText(true); // Enable wrapping text

                // Apply CSS styling to "Date_pub:" label to make it bold and larger
                dateLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

                // Add labels for "Info", "Title", and "Date_pub" to the VBox
                // Add labels for "Info", "Title", and "Date_pub" to the VBox
                reviewVBox.getChildren().addAll(titleLabel, titleValueLabel, infoLabel, infoValueLabel, dateLabel, dateValueLabel, imageView);


                // Add a mouse click event handler to the VBox to select the review
                reviewVBox.setOnMouseClicked(event -> {
                    // Retrieve the selected review's information
                    selectedActivityID = activityId;
                    selectedChallengeID = challengeId;
                    selectedReviewInfo = info;
                    selectedReviewTitle = title;

                    // Display the selected review's information in the TextArea and TextField
                    infoid.setText(selectedReviewInfo);
                    titleid.setText(selectedReviewTitle);

                    // Prompt the user to confirm deletion
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Review");
                    alert.setHeaderText("Are you sure you want to delete this review?");
                    alert.setContentText("This action cannot be undone.");

                    // Handle the user's response
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            // Call the deleteReview method to delete the review
                            deleteReview(activityId, challengeId);
                            // After deleting the review, refresh the display
                            try {
                                displayReviews();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                });

                // Add VBox to list
                reviewVBoxes.add(reviewVBox);
            }
        }

        // Add all VBox nodes to the ScrollPane content
        VBox contentVBox = new VBox();
        contentVBox.getChildren().addAll(reviewVBoxes);

        scrollpane.setContent(contentVBox);
    }

    private void refreshScrollPane() {
        try {
            displayReviews(); // Fetch the updated list of reviews and populate the ScrollPane
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during the refresh process
        }
    }
    private void deleteReview(int activityId, int challengeId) {
        try {
            // Prepare the DELETE statement
            String deleteQuery = "DELETE FROM reviewchallenge WHERE IDActivity = ? AND ID_challenge = ?";
            PreparedStatement preparedStatement = con.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, activityId);
            preparedStatement.setInt(2, challengeId);

            // Execute the statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Close the statement
            preparedStatement.close();

            if (rowsAffected > 0) {
                System.out.println("Review deleted successfully.");
            } else {
                System.out.println("No review found with the specified IDs.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQL exception appropriately
        }
    }


    @FXML
    private TextArea infoid;

    @FXML
    private TextField dateid;

    @FXML
    private TextField titleid;
    @FXML
    void AddRev(ActionEvent event) {

            // Retrieve review details from your UI components
            String info = infoid.getText(); // Assuming infoid is the TextArea where user enters review information
            String title = titleid.getText(); // Assuming titleid is the TextField where user enters review title

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Format the current date to match the format expected by the database (assuming it's "dd-MM-yyyy")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
            // Call addReview method to add the review
            addReview(info, formattedDate, title);
        // After adding the review, refresh the content of the ScrollPane
        refreshScrollPane();
        }
    private void updateReview(int activityId, int challengeId, String updatedInfo, String updatedTitle, String updatedDate, String image) {
        try {
            // Filter bad words from the updated information
            String filteredInfo = filterBadWords(updatedInfo);

            // Prepare the UPDATE statement
            String updateQuery = "UPDATE reviewchallenge SET Info = ?, Date_pub = ?, Title_rev = ?, image = ? WHERE IDActivity = ? AND ID_challenge = ?";
            PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, filteredInfo);

            // Convert the updated date string to the correct format
            LocalDate localDate = LocalDate.parse(updatedDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            Date sqlDate = Date.valueOf(localDate);
            preparedStatement.setDate(2, sqlDate);

            preparedStatement.setString(3, updatedTitle);
            preparedStatement.setString(4, image); // Set the image path
            preparedStatement.setInt(5, activityId);
            preparedStatement.setInt(6, challengeId);

            // Execute the statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Close the statement
            preparedStatement.close();

            if (rowsAffected > 0) {
                System.out.println("Review updated successfully.");
            } else {
                System.out.println("No review found with the specified IDs.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating review: " + e.getMessage());
            e.printStackTrace();
        }
    }


@FXML
void UpdateRev(ActionEvent event) {
    // Check if a review is selected
    if (selectedActivityID != 0 && selectedChallengeID != 0) {
        // Retrieve the updated review information from UI components
        String updatedInfo = infoid.getText();
        String updatedTitle = titleid.getText();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String updatedDate = currentDate.format(formatter);

        // Call the method to update the review with the new information and image path
        updateReview(selectedActivityID, selectedChallengeID, updatedInfo, updatedTitle, updatedDate, imagePath);

        // After updating the review, refresh the content of the ScrollPane
        refreshScrollPane();
        clearFields();
        // Clear the selection and UI components after updating the review
        clearSelection();
    } else {
        // Display an error message if no review is selected
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Please select a review to update.");
        alert.showAndWait();
    }
}

    private void clearSelection() {
        selectedReviewId = 0;
        selectedReviewInfo = "";
        selectedReviewTitle = "";
        infoid.clear();
        titleid.clear();
    }
    private String[] badWords = {"idiot", "rubbish", "moron","jerk","heck","darn", "shit"}; // Add your bad words here
    // Method to replace bad words with stars
    private String filterBadWords(String input) {
        String filteredText = input;
        for (String badWord : badWords) {
            // Create a regex pattern for each bad word to match both lowercase and uppercase occurrences
            Pattern pattern = Pattern.compile(badWord, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(filteredText);
            // Replace bad words with stars
            filteredText = matcher.replaceAll("*".repeat(badWord.length()));
        }
        return filteredText;
    }
    @FXML
    void cam(ActionEvent event) {
        Stage webcamStage = new Stage();
        webcamStage.setTitle("Webcam Feed");

        // Create a webcam instance and set its properties
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();

        // Create an ImageView to display the webcam feed
        ImageView imageView = new ImageView();

        // Create a button to capture an image
        Button captureButton = new Button("Capture");
        captureButton.setOnAction(e -> {
            System.out.println("Capturing image...");
            // Capture a webcam frame
            BufferedImage bufferedImage = webcam.getImage();

            // Convert the BufferedImage to a JavaFX Image
            javafx.scene.image.Image image = SwingFXUtils.toFXImage(bufferedImage, null);

            // Optionally, you can save the captured image to a file here
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                    new FileChooser.ExtensionFilter("JPEG Files", "*.jpg", "*.jpeg"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            File file = fileChooser.showSaveDialog(webcamStage);
            if (file != null) {
                try {
                    ImageIO.write(bufferedImage, "png", file);
                    System.out.println("Image saved successfully at: " + file.getAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println("Failed to save image.");
                }
            }

            // Display a message to the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Image Captured");
            alert.setHeaderText(null);
            alert.setContentText("Image captured successfully!");
            alert.showAndWait();
        });

        // Start a new thread to continuously update the ImageView with webcam frames
        Thread updateThread = new Thread(() -> {
            while (true) {
                // Capture a webcam frame
                BufferedImage bufferedImage = webcam.getImage();

                // Convert the BufferedImage to a JavaFX Image
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                // Update the ImageView with the new image
                Platform.runLater(() -> imageView.setImage(image));

                // Sleep for a short duration before capturing the next frame
                try {
                    Thread.sleep(1000 / 20); // 20 frames per second
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();

        // Set up the layout for the scene
        VBox layout = new VBox(imageView, captureButton);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);

        // Set up the scene and show the stage
        webcamStage.setScene(new Scene(layout, 640, 520)); // Increased height to accommodate the button
        webcamStage.show();
    }

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

////////////////////////////////////////////////////////////////////

    public void Dashboard(ActionEvent actionEvent) {
    }

    public void forum(ActionEvent actionEvent) {
    }

    public void services(ActionEvent actionEvent) {
    }

    public void achievements(ActionEvent actionEvent) {
    }

    public void locations(ActionEvent actionEvent) {
    }

    public void home(ActionEvent actionEvent) {
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void clearFields() {
        // Clear the text fields
        infoid.clear();
        titleid.clear();

        // Clear the image view
        imageView.setImage(null);
    }

}
