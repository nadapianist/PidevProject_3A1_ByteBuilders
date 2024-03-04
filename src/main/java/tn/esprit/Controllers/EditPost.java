package tn.esprit.Controllers;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdk.jfr.Category;
import tn.esprit.entities.User;
import tn.esprit.entities.post;
import tn.esprit.services.ImageAPI;
import tn.esprit.services.forumService;
import tn.esprit.services.postService;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class EditPost implements Initializable{

    private post selectedPost;
    private int loggedInUserId;
    @FXML
    private ComboBox<String> category;

    @FXML
    private TextArea content;
    @FXML
    private ImageView PhotoPost;
    private String selectedImagePath;

    private final postService ps=new postService();
    private final forumService fs = new forumService();


    public void UpdatePost(ActionEvent actionEvent) throws SQLException {
        // Save the updated post data
        try {
            String ContentPost = content.getText().trim();
            String CategoryPost = category.getValue();
            int idForum= fs.getIdForum(CategoryPost);

            selectedPost.setContentPost(content.getText().trim());
            selectedPost.setCategoryPost(category.getValue());
            selectedPost.setPhotoPost(selectedImagePath);


            // Set the Cloudinary URL obtained after image upload
       String cloudinaryUrl = ImageAPI.uploadImage(selectedImagePath);
        selectedPost.setPhotoPost(cloudinaryUrl);

        selectedPost.setUserID(getLoggedInUserId());
            // Update other fields

            // Call the update method from your service to update the post

            ps.update(this.selectedPost);

            // Close the editing window
            ((Stage) content.getScene().getWindow()).close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    public void setPostData(post selectedPost) throws SQLException {
        this.selectedPost = selectedPost;
        content.setText(selectedPost.getContentPost());
        if (category != null) {
            // Fetch the category associated with the post
            String categoryPost = selectedPost.getCategoryPost();
            // Set the ComboBox value to the category
            category.setValue(categoryPost);
        } else {
            System.err.println("Error: ComboBox 'category' is null.");
        }
        int idForum = fs.getIdForum(category.getValue());
        loggedInUserId = getLoggedInUserId();

        // Set values for other fields
    }
    private int getLoggedInUserId() {
        // return 123; // Replace 1 with the actual ID
        //User loggedInUser = SessionManager.getCurrentUser();
        User loggedInUser= new User(462 );
        // Check if a user is logged in
        if (loggedInUser != null) {
            return loggedInUser.getUserID();
        } else {
            // Handle the case where no user is logged in
            // You might throw an exception, return a default value, or handle it as needed.
            return -1; // Example: return -1 if no user is logged in
        }

    }

    public void Backtoposts(ActionEvent actionEvent) {
    }

    @FXML
    void AddImage(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", ".jpeg", ".gif"));

        File file = fileChooser.showOpenDialog(new Stage());

        if ( file!= null) {

          /*  Image image = new Image(file.toURI().toString());
            PhotoPost.setImage(image);
            selectedImagePath="file:/"+file.getAbsolutePath();*/
            selectedImagePath = file.getAbsolutePath();
            Image image = new Image("file:" + selectedImagePath);
            PhotoPost.setImage(image);

            // Upload image to Cloudinary and get the secure URL
            String cloudinaryUrl = ImageAPI.uploadImage(selectedImagePath);

        }}


    public void handleCategorySelection(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Fetch categories from the database
            List<String> categories = fs.getAllCategories();

            // Assuming that category is your ComboBox
            if (category != null) {
                // If you have any default values, add them
                categories.add(0, "pick category!");

                // Set the ComboBox items
                category.setItems(FXCollections.observableArrayList(categories));
                category.setValue(categories.get(0)); // Set a default value if needed
            } else {
                System.err.println("Error: ComboBox 'category' is null.");
            }
        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }}

