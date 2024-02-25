package tn.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entities.forum;
import tn.esprit.entities.post;
import tn.esprit.services.forumService;
import tn.esprit.services.postService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class ForumManagement {

    @FXML
    private Button AddButton;

    @FXML
    private TableColumn<forum, String> Category;

    @FXML
    private TableColumn<post, String> CategoryPost;

    @FXML
    private TextField Categoryid;

    @FXML
    private TableColumn<forum, String> ContentForum;

    @FXML
    private TextField ContentForumid;

    @FXML
    private TableColumn<post, String> ContentPost;

    @FXML
    private TextField ContentSearchField;

    @FXML
    private TableColumn<post, Date> DatePost;

    @FXML
    private Button DeleteLocButton;

    @FXML
    private Button EditButton;

    @FXML
    private TableView<forum> ForumList;

    @FXML
    private TextField IDForumid;

    @FXML
    private TableColumn<post,Integer> IDPost;

    @FXML
    private TableColumn<forum,Integer> IDForum;

    @FXML
    private Button ManageForumButton;

    @FXML
    private Button ManagePostsButton;

    @FXML
    private TableColumn<post, Integer> NB_posts;

    @FXML
    private TextField NB_postsid;

    @FXML
    private TableColumn<post, String > PhotoPost;

    @FXML
    private TableView<post> PostsList;

    @FXML
    private Button SearchButton;

    @FXML
    private RadioButton SortButton;

    @FXML
    private TableColumn<post, Integer> UserID;
    private final postService ps=new postService();
    private final forumService fs=new forumService();

    @FXML
    void initialize() {
        try {

            List<forum> forums=fs.displayList();
            ObservableList<forum> observableList= FXCollections.observableList(forums);
            ForumList.setItems(observableList);
            IDForum.setCellValueFactory(new PropertyValueFactory<>("IDForum"));
            ContentForum.setCellValueFactory(new PropertyValueFactory<>("ContentForum"));
            NB_posts.setCellValueFactory(new PropertyValueFactory<>("NB_posts"));
            Category.setCellValueFactory(new PropertyValueFactory<>("Category"));
        }catch (SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void AddNewForum(ActionEvent event)  {
        try{
            forum f=new forum();
            //f.setIDForum(Integer.parseInt(IDForumid.getText()));
            f.setContentForum((ContentForumid.getText()));
            f.setNB_posts(Integer.parseInt(NB_postsid.getText()));
            f.setCategory(Categoryid.getText());
            fs.add(f);
            initialize();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("A new post is added");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }





    @FXML

    private void DeleteForumById(ActionEvent event) {
        forum selectedForum = ForumList.getSelectionModel().getSelectedItem();

        if (selectedForum != null) {
            try {
                // Call your service method to delete the forum
                fs.delete(selectedForum.getIDForum());

                // Remove the selected forum from the TableView
                ForumList.getItems().remove(selectedForum);
            } catch (SQLException e) {
                // Handle any SQL exception
                e.printStackTrace();
            }
        }
    }


    @FXML
    void FilterByCategory(ActionEvent event) {

    }

    @FXML
    void SaveChanges(ActionEvent event) {

    }

    @FXML
    void SearchByContent(ActionEvent event) {

    }

    @FXML
    void ToForumPage(ActionEvent event) {

    }

    @FXML
    void ToPostsPage(ActionEvent event) {

    }

}
