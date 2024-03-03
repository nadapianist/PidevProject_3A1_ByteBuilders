package tn.esprit.Controllers;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;  // Make sure to import the correct Text class
import javafx.stage.Stage;
import tn.esprit.entities.forum;
import tn.esprit.entities.post;
import tn.esprit.services.forumService;
import javafx.event.ActionEvent;
import tn.esprit.services.postService;
import tn.esprit.utils.MyDb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
public class Forumuser {

    @FXML
    private Button ADDPOST;
    @FXML
    private HBox tfpostlist;
    @FXML
    private ImageView logoo;

    @FXML
    private ImageView logooo;

    @FXML
    private HBox tfforumlist;
    @FXML
    private VBox postsContainer;  // Assuming you have a container in your FXML file to display posts
    private final forumService fs = new forumService();
    private final postService ps = new postService();
    int idForum;

    Connection con;
    public Forumuser() {
        con = MyDb.getInstance().getCon();
    }

    @FXML
    void initialize() {
        // Call the method to retrieve forums and update the UI
        loadForums();

        String cssFile = getClass().getResource("/styles.css").toExternalForm();
        tfforumlist.getStylesheets().add(cssFile);
    }

    private void loadForums() {
        try {

            List<forum> forums = fs.displayList(); /* Call your method to get the list of forums from the database */
            ;

            // Create a VBox to hold all the rows of forums
            VBox forumsContainer = new VBox();
            forumsContainer.setSpacing(20); // Adjust the spacing between rows

            // Iterate through the forums
            for (int i = 0; i < forums.size(); i += 3) {
                // Create an HBox for each row of forums
                HBox rowBox = new HBox();
                rowBox.setSpacing(200); // Adjust the spacing between forums in a row

                // Add up to three forums to the current row
                for (int j = i; j < Math.min(i + 3, forums.size()); j++) {
                    forum f = forums.get(j);
                    // Create a VBox for each forum
                    VBox forumBox = new VBox();
                    forumBox.getStyleClass().add("forum-box");

                    // Add the delete button (supp) or any other action button if needed
                    // For example, you can add a button to navigate to the detailed view of the forum

                    Label categoryLabel = new Label(f.getCategory());
                    categoryLabel.getStyleClass().add("forum-category");
                    categoryLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");

                    Label contentLabel = new Label(f.getContentForum());
                    contentLabel.getStyleClass().add("forum-content");
                    contentLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");

                    Label nbPostsLabel = new Label("Number of Posts: " + f.getNB_posts());
                    nbPostsLabel.getStyleClass().add("forum-nb-posts-link");
                    nbPostsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #0073e6;");
                    // Add labels to the forumBox
                    forumBox.getChildren().addAll(categoryLabel, contentLabel, nbPostsLabel);
                    forumBox.setOnMouseClicked(event -> {
                        try {

                            idForum=f.getIDForum();

                            navigateToPostsPage(idForum);
                            //ps.displayListForum(f.getIDForum())
                        } catch (SQLException | IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    });
                    rowBox.getChildren().add(forumBox);
                }

                forumsContainer.getChildren().add(rowBox);
            }

            tfforumlist.getChildren().add(forumsContainer);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public int getIdForum()
    {
        return idForum;
    }
    public void setPosts(List<post> posts) {
        // Clear existing content in the container
        postsContainer.getChildren().clear();
        //VBox postsContainer = new VBox();
        // Iterate through the posts and display them
        for (post p : posts) {
            // Create a Node (e.g., a Label) for each post and add it to the container
            Label postLabel = new Label(p.getContentPost());
            postLabel.getStyleClass().add("post-label");  // You can add CSS styling if needed
            postsContainer.getChildren().add(postLabel);

        }
    }

    @FXML
    void ADDPOST(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/AddPost.fxml"));
        ADDPOST.getScene().setRoot(root);
    }
    @FXML
    void NEWS(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/News.fxml"));
        ADDPOST.getScene().setRoot(root);
    }

    private void navigateToPostsPage(int idForum) throws SQLException, IOException {
        // Load the FXML file and get the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Postsgategory.fxml"));
        Parent root = loader.load();
        Postsgategory p = loader.getController();
        p.setIdForum(idForum);
        Scene p1=new Scene(root);
        Stage Window = (Stage) ADDPOST.getScene().getWindow();
        Window.setScene(p1);
        Window.show();
    }
    @FXML
    void Dashboard(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/forumManagement.fxml"));
        ADDPOST.getScene().setRoot(root);
    }
    @FXML
    void VIEWALL(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/Posts.fxml"));
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


}