
package tn.esprit.controller;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.control.Alert;
        import javafx.stage.FileChooser;
        import javafx.stage.Stage;
        import tn.esprit.Exception.InvalidLengthException;


        import javafx.fxml.Initializable;
        import javafx.scene.control.Button;
        import javafx.scene.control.ComboBox;
        import javafx.scene.control.TextArea;
        import javafx.scene.image.ImageView;
        import tn.esprit.entities.Image;
        import tn.esprit.entities.post;
        import tn.esprit.services.ImageAPI;
        import tn.esprit.services.postService;

        import javax.swing.text.html.HTML;
        import java.io.File;
        import java.io.IOException;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.sql.SQLException;
        import java.util.ResourceBundle;

        import static javax.swing.text.html.HTML.Attribute.MAXLENGTH;

public class AddPost implements Initializable {
    public final int  MAXLENGTH=10;



@FXML
private Button AddImage;
    @FXML
    private Button AddPostButton;



@FXML
private Button CancelAddingButton;

@FXML
private ComboBox <String> CategoryPost;

@FXML
private TextArea ContentPost;

@FXML
private Button ManageForumButton;

@FXML
private Button ManagePostsButton;

@FXML
private ImageView PhotoPost;
    private final postService ps=new postService();
    ObservableList<String> categoryPostList= FXCollections.observableArrayList("Activity","Challenge");

    @FXML
public void initialize(URL url, ResourceBundle resourceBundle){

CategoryPost.setValue("Location");
CategoryPost.setItems(categoryPostList);


    }


    @FXML
    void AddImage(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        fileChooser.setInitialDirectory(new File("C:/Users/tcp/Desktop/CRUD/src/main/java/image"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                // Obtenez le chemin absolu du fichier
                String imageFile = file.toURI().toURL().toString();
               // imageFile = imageFile.substring(2);
                Image.setImage(new Image(imageFile));

            } catch (MalformedURLException e) {
                e.printStackTrace();}
    }}

    @FXML
        void AddNewPost(ActionEvent event){
            try {
                ps.add(new post(
                        ContentPost.getText()
                        , PhotoPost.getId()
                        , (String) CategoryPost.getValue(),123

                ));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("A post is added");
                alert.showAndWait();
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
