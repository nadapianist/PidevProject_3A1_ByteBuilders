package tn.esprit.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import tn.esprit.entities.post;
import tn.esprit.services.forumService;
import tn.esprit.services.postService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
public class AddPost implements Initializable  {

    @FXML
    private Button AddImage;
    @FXML
    private Button AddPostButton;



    @FXML
    private Button CancelAddingButton;

    @FXML
    private ComboBox <String> categoryPost;

    @FXML
    private TextArea ContentPost;

    @FXML
    private Button ManageForumButton;

    @FXML
    private Button ManagePostsButton;

    @FXML
    private ImageView PhotoPost;
    private final postService ps=new postService();
    private final forumService fs = new forumService();
    //ObservableList<String> categoryPostList= FXCollections.observableArrayList("Activity","Challenge");

    @FXML
    public void initialize(URL url , ResourceBundle resourceBundle){
        try {
            // Fetch categories from the database
            List<String> categories = fs.getAllCategories();
            // If you have any default values, add them
            categories.add(0, "pick category!");
            // Set the ComboBox items
            categoryPost.setItems(FXCollections.observableArrayList(categories));
            categoryPost.setValue(categories.get(0)); // Set a default value if needed
        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }



    @FXML
    void AddImage(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        File file = fileChooser.showOpenDialog(new Stage());

        if ( file!= null) {

            Image image = new Image(file.toURI().toString());
            PhotoPost.setImage(image);
            String selectedImagePath=file.getAbsolutePath();

        }}

    @FXML
    void AddNewPost(ActionEvent event) {
        try {
            String content = ContentPost.getText().trim();
            String category = categoryPost.getValue();
            String imagePath = (PhotoPost.getImage() != null) ? PhotoPost.getImage().getUrl() : null;

            if (content.isEmpty() || category == null || category.equals("pick category!")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Content and Category are mandatory fields");
                alert.showAndWait();
            } else {
                post newPost = new post(content, imagePath, category, 123);
                ps.add(newPost);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("A post is added");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void Backtoposts(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/UserPost.fxml"));
        CancelAddingButton.getScene().setRoot(root);
        System.out.println("moved");
    }

    @FXML
    void ToForumPage(ActionEvent event) {

    }

    @FXML
    void ToPostsPage(ActionEvent event) {

    }

    public void handleCategorySelection(ActionEvent actionEvent) {
    }
}