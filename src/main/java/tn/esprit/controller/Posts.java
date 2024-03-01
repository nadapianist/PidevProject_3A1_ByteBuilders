package tn.esprit.controller;

import com.modernmt.text.profanity.ProfanityFilter;
import com.modernmt.text.profanity.dictionary.Profanity;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import tn.esprit.services.ServiceComment;
import javafx.fxml.Initializable;
import tn.esprit.entities.Comment;
import tn.esprit.entities.User;
import tn.esprit.entities.Tourist;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import tn.esprit.entities.post;
import tn.esprit.services.ServiceComment;
import tn.esprit.services.forumService;
import tn.esprit.services.postService;

import javafx.scene.layout.VBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Posts implements Initializable  {
    @FXML
    private Button ADDPOST;

    @FXML
    private ImageView logoo;

    @FXML
    private ImageView logooo;

    @FXML
    private HBox tfpostlist;
    @FXML
    private ScrollPane scrollPane;

    private final postService ps = new postService();
    private final forumService fs = new forumService();
    ServiceComment serviceComment = new ServiceComment();
    private ProfanityFilter profanityFilter = new ProfanityFilter();


    @FXML
    public void initialize(URL url , ResourceBundle resourceBundle) {
        loadPosts();
        String cssFile = getClass().getResource("/styles.css").toExternalForm();
        tfpostlist.getStylesheets().add(cssFile);




    }
   /* private void loadPosts() {
        try {

            List<post> posts = ps.displayList();
            for (post p : posts) {

                HBox postBox = new HBox();

                ImageView photoImageView = new ImageView(new Image(p.getPhotoPost()));
                photoImageView.setFitWidth(100);
                photoImageView.setPreserveRatio(true);

                Label contentLabel = new Label(p.getContentPost());
                contentLabel.getStyleClass().add("post-content");
                contentLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");

                TextField newCommentField = new TextField();
                newCommentField.textProperty().addListener((observable, oldValue, newValue) -> {
                    // Call the findAndReplaceProfanity method to detect and replace profanity in TextArea
                    String   text= findProfanity(newValue);
                    deleteString(newValue,text,newCommentField);
                });
                newCommentField.setStyle("-fx-font-size: 14px;");

                Button addCommentButton = new Button("Add Comment");
                addCommentButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

                VBox commentsContainer = new VBox();
                commentsContainer.setSpacing(6); // Adjust spacing as needed
                VBox.setVgrow(commentsContainer, Priority.ALWAYS);


                addCommentButton.setOnAction(event -> {
                    Comment newComment = new Comment(
                            123, // Replace with the actual user ID of the logged-in user
                            p.getIDPost(),
                            newCommentField.getText()
                    );

                    try {
                        serviceComment.addComment(newComment);
                        showNotification("success","comment added");

                        // Update the timestamp of the new comment
                        newComment.setDate_c(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                        VBox commentBox = createCommentBox(newComment);
                        commentsContainer.getChildren().add(commentBox);

                        // Clear the comment field after adding the comment
                        newCommentField.clear();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });

                loadComments(p.getIDPost(), commentsContainer);


                // Add the components to the postBox in the desired order
                postBox.getChildren().addAll(photoImageView, contentLabel, newCommentField, addCommentButton, commentsContainer);
                tfpostlist.getChildren().add(postBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }*/
   private void loadPosts() {
       try {
           List<post> posts = ps.displayList();

           // Create a VBox to hold all the rows of posts
           VBox postsContainer = new VBox();
          // postsContainer.setSpacing(20); // Adjust the spacing between rows

           // Iterate through the posts
           for (int i = 0; i < posts.size(); i += 3) {
               // Create an HBox for each row of posts
               HBox rowBox = new HBox();
               rowBox.setSpacing(200); // Adjust the spacing between posts in a row

               // Add up to three posts to the current row
               for (int j = i; j < Math.min(i + 3, posts.size()); j++) {
                   post p = posts.get(j);
                   // Create a VBox for each post
                   VBox postBox = new VBox();
                   postBox.getStyleClass().add("post-box");
                   // Add the delete button (supp)
                   FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH_ALT);
                   deleteIcon.setFill(Color.RED);
                   deleteIcon.setGlyphSize(25);
                   deleteIcon.setCursor(Cursor.HAND);
                   deleteIcon.setOnMouseClicked(event -> {
                       // Handle delete post action
                       deletePost(p);
                       // Optionally, you can refresh the UI after deletion
                   });
                   ImageView photoImageView = new ImageView(new Image(p.getPhotoPost()));
                   photoImageView.setFitWidth(100);
                   photoImageView.setPreserveRatio(true);
                   Label contentLabel = new Label(p.getContentPost());
                   contentLabel.getStyleClass().add("post-content");
                   contentLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");
                   TextField newCommentField = new TextField();
                   newCommentField.textProperty().addListener((observable, oldValue, newValue) -> {
                       // Call the findAndReplaceProfanity method to detect and replace profanity in TextArea
                       String   text= findProfanity(newValue);
                       deleteString(newValue,text,newCommentField);
                   });
                   newCommentField.setStyle("-fx-font-size: 14px;");
                   Button addCommentButton = new Button("Add Comment");
                   addCommentButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                   VBox commentsContainer = new VBox();
                   commentsContainer.setSpacing(6); // Adjust spacing as needed
                   VBox.setVgrow(commentsContainer, Priority.ALWAYS);
                   addCommentButton.setOnAction(event -> {
                       Comment newComment = new Comment(
                               123, // Replace with the actual user ID of the logged-in user
                               p.getIDPost(),
                               newCommentField.getText()
                       );
                       try {
                           serviceComment.addComment(newComment);
                           showNotification("success","comment added");
                           newComment.setDate_c(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                           VBox commentBox = createCommentBox(newComment);
                           commentsContainer.getChildren().add(commentBox);
                           newCommentField.clear();
                       } catch (SQLException e) {
                           e.printStackTrace();
                           throw new RuntimeException(e);
                       }
                   });

                   loadComments(p.getIDPost(), commentsContainer);
                   postBox.getChildren().addAll(photoImageView, contentLabel, newCommentField, addCommentButton, commentsContainer, deleteIcon);
                   rowBox.getChildren().add(postBox);
               }

               postsContainer.getChildren().add(rowBox);
           }
           tfpostlist.getChildren().add(postsContainer);
       } catch (SQLException e) {
           e.printStackTrace();
           throw new RuntimeException(e);
       }
   }
   private void deletePost(post selectedPost) {
        try {
            ps.delete(selectedPost.getIDPost());

            showNotification("Post Deleted", "The post has been successfully deleted.");
            clearAndReloadPosts();

        } catch (SQLException e) {
            e.printStackTrace();
            showNotification("Error", "An error occurred while deleting the post.");
        }}
    private void clearAndReloadPosts() {
        // Clear the existing posts
        tfpostlist.getChildren().clear();

        // Reload posts
        loadPosts();
    }

    private String findProfanity(String text) {
        String text1="";
        // Test if the string contains profanity
        boolean containsProfanityEn = profanityFilter.test("en", text);
        boolean containsProfanityFr = profanityFilter.test("fr", text);

        // Check if profanity is found in any language
        if (containsProfanityEn || containsProfanityFr) {
            // Show an alert indicating profanity is found
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Profanity Detected");
            alert.setHeaderText("Profanity has been detected in the text.");
            alert.setContentText("Please refrain from using offensive language.");
            alert.showAndWait();
            Profanity profanityen=profanityFilter.find("en",text);
            Profanity profanityfr=profanityFilter.find("fr",text);
            if(containsProfanityEn)
                text1=profanityen.text();
            else {
                text1=profanityfr.text();
            }
        }
        return text1;

    }
    private void deleteString(String text,String stringToDelete,TextField description) {
        // Check if the new text contains the string to delete
        if (text.contains(stringToDelete)) {
            // Delete the string from the text
            Platform.runLater(() -> {
                // Calculate start and end indices of the string to delete
                int startIndex = text.indexOf(stringToDelete);
                int endIndex = startIndex + stringToDelete.length();
                // Update the text area content with the string deleted
                description.replaceText(startIndex, endIndex, "");
                // Move the caret position to the end of the text area
                description.positionCaret(description.getText().length());
            });
        }
    }
    private void refreshComments(VBox postBox, int postId) {
        try {
            // Clear existing comments in the postBox
            postBox.getChildren().removeIf(node -> node instanceof VBox);
            // Retrieve the updated list of comments for the given postId
            List<Comment> comments = serviceComment.getCommentsByPostId(postId);
            // Add the updated comments to the postBox
            for (Comment comment : comments) {
                VBox commentBox = createCommentBox(comment);
                postBox.getChildren().add(commentBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e); // Handle the exception appropriately
        }
    }

    private void loadComments(int postId, VBox postBox) {
        try {
            List<Comment> comments = serviceComment.getCommentsByPostId(postId);
            for (Comment comment : comments) {
                VBox commentBox = createCommentBox(comment);
                postBox.getChildren().add(commentBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private VBox createCommentBox(Comment comment) {
        VBox commentBox = new VBox();
        Label commentLabel = new Label(comment.getCommentt());
        if (comment.getDate_c() != null) {
            try {
        // Format and display the comment date
        LocalDateTime commentDate = LocalDateTime.parse(comment.getDate_c(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Duration duration = Duration.between(commentDate, LocalDateTime.now());
        String formattedDate = formatDuration(duration);
        Label dateLabel = new Label(formattedDate);

        commentBox.getChildren().addAll(commentLabel, dateLabel);
            } catch (DateTimeParseException e) {
                // Handle parsing exception, log it, or display a default date
                System.err.println("Error parsing date: " + comment.getDate_c());
                e.printStackTrace();
                // You can add a default date label or handle it based on your requirements
            }
        } else {
            // If date_c is null, only add the comment label
            commentBox.getChildren().add(commentLabel);
        }

        return commentBox;
    }

    private String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String formattedDuration;

        if (absSeconds < 60) {
            formattedDuration = " "+ absSeconds + " seconds ago";
        } else if (absSeconds < 3600) {
            long minutes = absSeconds / 60;
            formattedDuration =" "+ minutes + " minutes ago";
        } else if (absSeconds < 86400) {
            long hours = absSeconds / 3600;
            formattedDuration = " "+hours +" hours ago";
        } else if (absSeconds < 2592000) {
            long days = absSeconds / 86400;
            formattedDuration = " "+days + " days ago";
        } else if (absSeconds < 31536000) {
            long months = absSeconds / 2592000;
            formattedDuration =" "+ months + " months ago";
        } else {
            long years = absSeconds / 31536000;
            formattedDuration = " "+ years +" years ago";
        }

        return formattedDuration;
    }

    @FXML
    void ADDPOST(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/AddPost.fxml"));
        ADDPOST.getScene().setRoot(root);
    }

    @FXML
    void Dashboard(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/forumManagement.fxml"));
        ADDPOST.getScene().setRoot(root);
    }

    @FXML
    void achievements(ActionEvent event) {

    }

    @FXML
    void forum(ActionEvent event) {

    }

    @FXML
    void home(ActionEvent event) {

    }

    @FXML
    void locations(ActionEvent event) {

    }

    @FXML
    void services(ActionEvent event) {

    }
    private Stage stage; // Reference to the stage

    // Method to set the stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .owner(stage) // Set the owner window
                .showInformation();
    }

}
