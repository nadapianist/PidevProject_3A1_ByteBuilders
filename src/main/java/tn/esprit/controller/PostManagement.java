package tn.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tn.esprit.entities.post;
import tn.esprit.controller.PostDao;

import java.util.Date;
import java.util.List;

public class PostManagement {

    @FXML
    private TableColumn<post,String> ContentPostid;

    @FXML
    private TableColumn<post, Date> DatePostid;

    @FXML
    private Button ManageForumButton;

    @FXML
    private Button ManagePostsButton;

    @FXML
    private TableColumn<post, String> PhotoPostid;

    @FXML
    private TableColumn<post, String> PostIDid;

    @FXML
    private TableColumn<post, Integer> UserIDid;

    @FXML
    private TableColumn<post, String> categoryPostid;

    @FXML
    private Button deletePostAdmin;

    @FXML
    private TableView<post> postList;
    @FXML
    void initialize() {
        // Fetch and display the list of posts when the application starts
        loadPosts();
    }

    @FXML
    void DeletePostAdmin(ActionEvent event) {

    }

    @FXML
    void ToForumPage(ActionEvent event) {

    }

    @FXML
    void ToPostsPage(ActionEvent event) {

    }
    // Method to load and display the list of posts
    private void loadPosts() {
        // Assuming PostDao is a class responsible for database operations related to posts
        PostDao postDao = new PostDao();

        // Fetch the list of posts from the database
        List<post> posts = postDao.getAllPosts();

        // Clear existing items in the TableView
        postList.getItems().clear();

        // Add fetched posts to the TableView
        postList.getItems().addAll(posts);
    }

}
