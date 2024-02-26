package tn.esprit.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import tn.esprit.entities.forum;
import tn.esprit.entities.post;
import tn.esprit.services.forumService;
import tn.esprit.services.postService;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
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
            ForumList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Update text fields with the selected forum's data
                    ContentForumid.setText(newSelection.getContentForum());
                   NB_postsid.setText(String.valueOf(newSelection.getNB_posts()));
                    Categoryid.setText(newSelection.getCategory());
                }
            });
            List<forum> forums=fs.displayList();
            ObservableList<forum> observableList= FXCollections.observableList(forums);
            ForumList.setItems(observableList);
            IDForum.setCellValueFactory(new PropertyValueFactory<>("IDForum"));
            ContentForum.setCellValueFactory(new PropertyValueFactory<>("ContentForum"));
            NB_posts.setCellValueFactory(new PropertyValueFactory<>("NB_posts"));
            Category.setCellValueFactory(new PropertyValueFactory<>("Category"));
            initpost();
        }catch (SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }}
    @FXML
void initpost(){
        try{
    List<post> posts=ps.displayList();
    ObservableList<post> observableListt= FXCollections.observableList(posts);
    PostsList.setItems(observableListt);
    IDPost.setCellValueFactory(new PropertyValueFactory<>("IDPost"));
    ContentPost.setCellValueFactory(new PropertyValueFactory<>("ContentPost"));
            PhotoPost.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPhotoPost()));
            PhotoPost.setCellFactory(param -> new TableCell<>() {
                private final ImageView imageView = new ImageView();
                {
                    // Set dimensions for the image view as needed
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                }
                @Override
                protected void updateItem(String imagePath, boolean empty) {
                    super.updateItem(imagePath, empty);

                    if (empty || imagePath == null) {
                        setGraphic(null);
                    } else {
                        // Assuming imagePath is a valid file path
                        Image image = new Image(new File(imagePath).toURI().toString());
                        imageView.setImage(image);
                        setGraphic(imageView);
                    }
                }
            });

    DatePost.setCellValueFactory(new PropertyValueFactory<>("DatePost"));
    UserID.setCellValueFactory(new PropertyValueFactory<>("UserID"));
    CategoryPost.setCellValueFactory(new PropertyValueFactory<>("CategoryPost"));}
        catch (SQLException e){
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
            alert.setContentText("A new forum is added");
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
        forum selectedForum = ForumList.getSelectionModel().getSelectedItem();

        if (selectedForum != null) {
            try {
                // Update the selected forum with the values from the text fields
                selectedForum.setContentForum(ContentForumid.getText());
                selectedForum.setNB_posts(Integer.parseInt(NB_postsid.getText()));
                selectedForum.setCategory(Categoryid.getText());

                // Call your service method to update the forum
                fs.update(selectedForum);

                // Refresh the TableView to reflect the changes
                ForumList.refresh();

                System.out.println("Forum updated!");
            } catch (SQLException | NumberFormatException e) {
                // Handle any SQL exception or number format exception
                e.printStackTrace();
            }
        }
    }




    /*@FXML
    void SearchByContent(ActionEvent event) {
        try {
            String content = ContentSearchField.getText();
            List<forum> SearchResults = fs.SearchByContent(content);
            ObservableList<forum> observableList= FXCollections.observableList(SearchResults);
            ForumList.setItems(observableList);
            IDForum.setCellValueFactory(new PropertyValueFactory<>("IDForum"));
            ContentForum.setCellValueFactory(new PropertyValueFactory<>("ContentForum"));
            NB_posts.setCellValueFactory(new PropertyValueFactory<>("NB_posts"));
            Category.setCellValueFactory(new PropertyValueFactory<>("Category"));


        }catch(SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }*/
    /*@FXML
    void SearchByContent(KeyEvent event) {
        try {
            String content = ContentSearchField.getText();
            List<forum> SearchResults;


            if (content.isEmpty()) {
                // If the search field is empty, display all forums
                SearchResults = fs.displayList();
            } else {
                // Use a service method to search by content dynamically
                SearchResults = fs.SearchByContent(content);            }

            ObservableList<forum> observableList = FXCollections.observableList(SearchResults);
            ForumList.setItems(observableList);
            IDForum.setCellValueFactory(new PropertyValueFactory<>("IDForum"));
            ContentForum.setCellValueFactory(new PropertyValueFactory<>("ContentForum"));
            NB_posts.setCellValueFactory(new PropertyValueFactory<>("NB_posts"));
            Category.setCellValueFactory(new PropertyValueFactory<>("Category"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }*/


    @FXML
    void ToForumPage(ActionEvent event) {

    }

    @FXML
    void ToPostsPage(ActionEvent event) {

    }


    public void DeletePostAD(ActionEvent actionEvent) {
        post selectedPost = PostsList.getSelectionModel().getSelectedItem();

        if (selectedPost != null) {
            try {
                // Call your service method to delete the forum
                ps.delete(selectedPost.getIDPost());

                // Remove the selected forum from the TableView
                PostsList.getItems().remove(selectedPost);
            } catch (SQLException e) {
                // Handle any SQL exception
                e.printStackTrace();
            }
        }

    }

    public void SearchByContent(javafx.scene.input.KeyEvent keyEvent) {
        try {
            String content = ContentSearchField.getText();
            List<forum> SearchResults=fs.SearchByContent(content);
            ObservableList<forum> observableList = FXCollections.observableList(SearchResults);
            ForumList.setItems(observableList);

            IDForum.setCellValueFactory(new PropertyValueFactory<>("IDForum"));
            ContentForum.setCellValueFactory(new PropertyValueFactory<>("ContentForum"));
            NB_posts.setCellValueFactory(new PropertyValueFactory<>("NB_posts"));
            Category.setCellValueFactory(new PropertyValueFactory<>("Category"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
