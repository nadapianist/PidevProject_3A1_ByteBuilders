package tn.esprit.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.image.Image;

import javafx.scene.control.TableCell;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import tn.esprit.Exception.InvalidLengthException;
import tn.esprit.entities.forum;
import tn.esprit.entities.post;
import tn.esprit.services.forumService;
import tn.esprit.services.postService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ForumManagement  {

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
    private ComboBox<String> comboBox;

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
    private TableColumn<post, String > PhotoPost;

    @FXML
    private TableView<post> PostsList;

    @FXML
    private Button SearchButton;

    @FXML
    private TableColumn<post, Integer> UserID;
    private final postService ps=new postService();
    private final forumService fs=new forumService();
    public final int MAXLENGTH=15;
    private String selectedImagePath;


    @FXML
    void initialize() {
        try {
            ForumList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Update text fields with the selected forum's data
                    ContentForumid.setText(newSelection.getContentForum());
                    // NB_postsid.setText(String.valueOf(newSelection.getNB_posts()));
                    Categoryid.setText(newSelection.getCategory());
                }
            });
            List<forum> forums=fs.displayList();
            ForumList.refresh();
            ObservableList<forum> observableList= FXCollections.observableList(forums);
            ForumList.setItems(observableList);
            IDForum.setCellValueFactory(new PropertyValueFactory<>("IDForum"));
            ContentForum.setCellValueFactory(new PropertyValueFactory<>("ContentForum"));
            NB_posts.setCellValueFactory(new PropertyValueFactory<>("NB_posts"));
            Category.setCellValueFactory(new PropertyValueFactory<>("Category"));
            initpost();
         initcat();
        }catch (SQLException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void initcat() {
        try{
            List<String> categories = fs.getAllCategories();
            // If you have any default values, add them
            categories.add(0, "pick category!");
            // Set the ComboBox items
            comboBox.setItems(FXCollections.observableArrayList(categories));
            comboBox.setValue(categories.get(0)); // Set a default value if needed
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    void initpost() {
        try {
            List<post> posts = ps.displayList();
            ObservableList<post> observableListt = FXCollections.observableList(posts);
            PostsList.setItems(observableListt);
            IDPost.setCellValueFactory(new PropertyValueFactory<>("IDPost"));
            ContentPost.setCellValueFactory(new PropertyValueFactory<>("ContentPost"));

            // Corrected PropertyValueFactory for PhotoPost
            PhotoPost.setCellValueFactory(new PropertyValueFactory<>("PhotoPost"));

            // Set the PhotoPost column cell factory
            PhotoPost.setCellFactory(column -> {
                return new TableCell<post, String>() {
                    private final ImageView imageView = new ImageView();

                    {
                        imageView.setFitWidth(150);
                        imageView.setFitHeight(130);
                        setGraphic(imageView);
                    }

                    @Override
                    protected void updateItem(String imageUrl, boolean empty) {
                        super.updateItem(imageUrl, empty);
                        if (imageUrl == null || empty) {
                            imageView.setImage(null);
                        } else {
                            try {
                                Image image = new Image(imageUrl);
                                imageView.setImage(image);
                            } catch (Exception e) {
                                e.printStackTrace();
                                imageView.setImage(null);
                            }
                        }
                    }
                };
            });

            DatePost.setCellValueFactory(new PropertyValueFactory<>("DatePost"));
            UserID.setCellValueFactory(new PropertyValueFactory<>("UserID"));
            CategoryPost.setCellValueFactory(new PropertyValueFactory<>("CategoryPost"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void AddNewForum(ActionEvent event)  {
        try{
            validateStringLength(ContentForum.getText(), MAXLENGTH);
            if (Categoryid.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Category must be filled");
                alert.showAndWait();}
            else if  (fs.isCategoryExists(Categoryid.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Category already exists");
                alert.showAndWait();}
            else{
                forum f=new forum();
                f.setContentForum((ContentForumid.getText()));
                f.setCategory(Categoryid.getText());
                fs.add(f);
                initialize();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("A new forum is added");
                alert.showAndWait();}
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch(InvalidLengthException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Content must not exceed 30 characters");
            alert.showAndWait();
        }
    }
    public static void validateStringLength(String str, int maxLength) throws InvalidLengthException {
        if (str.length() > maxLength) {
            throw new InvalidLengthException("The given string must not exceed " + maxLength + " characters.");
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
                List<forum> forums = fs.displayList();
                ObservableList<forum> observableList = FXCollections.observableList(forums);
                ForumList.setItems(observableList);
                initcat();
            } catch (SQLException e) {
                // Handle any SQL exception
                e.printStackTrace();
            }
        }
    }

    @FXML
    void SaveChanges(ActionEvent event) {
        forum selectedForum = ForumList.getSelectionModel().getSelectedItem();
        if (selectedForum != null) {
            try {
                // Update the selected forum with the values from the text fields
                selectedForum.setContentForum(ContentForumid.getText());
                // selectedForum.setNB_posts(Integer.parseInt(NB_postsid.getText()));
                selectedForum.setCategory(Categoryid.getText());
                // Call your service method to update the forum
                fs.update(selectedForum);
                // Refresh the TableView to reflect the changes
                ForumList.refresh();
                initcat();
                System.out.println("Forum updated!");
            } catch (SQLException | NumberFormatException e) {
                // Handle any SQL exception or number format exception
                e.printStackTrace();
            }
        }
    }


    @FXML
    void ToPostsPage(ActionEvent event) throws IOException {
            Parent root= FXMLLoader.load(getClass().getResource("/forumManagement.fxml"));
        ManagePostsButton.getScene().setRoot(root);
            System.out.println("moved");
        }


  /*  public void DeletePostAD(ActionEvent actionEvent) {
        post selectedPost = PostsList.getSelectionModel().getSelectedItem();

        if (selectedPost != null) {
            try {
                // Call your service method to delete the forum
                ps.delete(selectedPost.getIDPost());
                // ForumList.refresh();
                List<forum> forums = fs.displayList();
                ObservableList<forum> observableList = FXCollections.observableList(forums);
                ForumList.setItems(observableList);

                // Remove the selected forum from the TableView
                PostsList.getItems().remove(selectedPost);
            } catch (SQLException e) {
                // Handle any SQL exception
                e.printStackTrace();
            }
        }
    }*/
  public void DeletePostAD(ActionEvent actionEvent) {
      post selectedPost = PostsList.getSelectionModel().getSelectedItem();

      if (selectedPost != null) {
          try {
              // Call your service method to delete the post
              ps.delete(selectedPost.getIDPost());

              // Remove the selected post from the TableView
              PostsList.getItems().remove(selectedPost);

              // Refresh the ForumList to reflect the changes
              List<forum> forums = fs.displayList();
              ObservableList<forum> observableList = FXCollections.observableList(forums);
              ForumList.setItems(observableList);
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
    /*public void SortForum(ActionEvent actionEvent) {
        try {
            String selectedCategory=comboBox.getValue();
            List<forum> SearchResults = fs.SortForum(selectedCategory);
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
    }
*/
    public void SortForum(ActionEvent actionEvent) {
        try {
            String selectedCategory = comboBox.getValue();

            // Check if "pick category" is selected
            if (selectedCategory == null || selectedCategory.equals("pick category!")) {
                // Get all forums
                List<forum> SearchResults = fs.displayList();
                ObservableList<forum> observableList = FXCollections.observableList(SearchResults);
                ForumList.setItems(observableList);
            } else {
                // Get forums based on the selected category
                List<forum> SearchResults = fs.SortForum(selectedCategory);
                ObservableList<forum> observableList = FXCollections.observableList(SearchResults);
                ForumList.setItems(observableList);
            }

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