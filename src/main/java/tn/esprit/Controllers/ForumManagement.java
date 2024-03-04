package tn.esprit.Controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.scene.control.TableCell;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import javafx.stage.Stage;
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


    @FXML
    private TableColumn<post, Integer> UserID;
    private final postService ps=new postService();
    private final forumService fs=new forumService();
    public final int MAXLENGTH=15;
    private String selectedImagePath;
    @FXML
    private ComboBox<String> comboBoxx;

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
            initcatpo();
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
    @FXML
    public void sortPostsByCategory(ActionEvent actionEvent) {
        try {
            String selectedCategory = comboBoxx.getValue();

            // Check if "pick category" is selected
            if (selectedCategory == null || selectedCategory.equals("pick category!")) {
                // Get all posts
                List<post> searchResults = ps.displayList();
                ObservableList<post> observableList = FXCollections.observableList(searchResults);
                PostsList.setItems(observableList);
            } else {
                // Get posts based on the selected category
                List<post> searchResults = ps.sortPostsByCategory(selectedCategory);
                ObservableList<post> observableList = FXCollections.observableList(searchResults);
                PostsList.setItems(observableList);
            }

            IDPost.setCellValueFactory(new PropertyValueFactory<>("IDPost"));
            ContentPost.setCellValueFactory(new PropertyValueFactory<>("ContentPost"));
            PhotoPost.setCellValueFactory(new PropertyValueFactory<>("PhotoPost"));
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
    void initcatpo() {
        try{
            List<String> categories = ps.getAllCategories();
            // If you have any default values, add them
            categories.add(0, "pick category!");
            // Set the ComboBox items
            comboBoxx.setItems(FXCollections.observableArrayList(categories));
            comboBoxx.setValue(categories.get(0)); // Set a default value if needed
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    @FXML

    private void DeleteForumById(ActionEvent event) {
        forum selectedForum = ForumList.getSelectionModel().getSelectedItem();
        if (selectedForum != null) {
            try {
                // Check if the corresponding category's NB_posts is 0
                if (selectedForum.getNB_posts() == 0) {
                    // Call a method to delete posts associated with the category
                    deletePostsByCategory(selectedForum.getCategory());
                }

                // Call your service method to delete the forum
                fs.delete(selectedForum.getIDForum());

                // Remove the selected forum from the TableView
                ForumList.getItems().remove(selectedForum);

                // Refresh the TableView
                ForumList.refresh();

                // Update the ObservableList with the latest data
                List<forum> forums = fs.displayList();
                ObservableList<forum> observableList = FXCollections.observableList(forums);
                ForumList.setItems(observableList);

                // Refresh the PostsList TableView as well
                List<post> posts = ps.displayList();
                ObservableList<post> observableListPosts = FXCollections.observableList(posts);
                PostsList.setItems(observableListPosts);
                PostsList.refresh();

                initcat();
            } catch (SQLException e) {
                // Handle any SQL exception
                e.printStackTrace();
            }
        }
    }


    private void deletePostsByCategory(String category) {
        try {
            // Call your service method to delete posts based on category
            ps.deletePostsByCategory(category);

            // Refresh the PostsList to reflect the changes
            List<post> posts = ps.displayList();
            ObservableList<post> observableList = FXCollections.observableList(posts);
            PostsList.setItems(observableList);
        } catch (SQLException e) {
            // Handle any SQL exception
            e.printStackTrace();
        }
    }
    //////////////////////////////////////////////////BUTTONS/////////////////
    @FXML
    void activityBTN(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ActivityManagement.fxml"));
        activitybtn.getScene().setRoot(root);

    }
    @FXML
    void ForumBTN(ActionEvent event) throws IOException {
       /* Parent root = FXMLLoader.load(getClass().getResource("/forumManagement.fxml"));
        forumbtnid.getScene().setRoot(root);*/

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
    public void logout(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        try {
            // Open new window (displayUser)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            Stage displayUserStage = new Stage();
            displayUserStage.setScene(new Scene(root));
            displayUserStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}