package tn.esprit.Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import tn.esprit.entities.Admin;
import tn.esprit.entities.LocalCom;
import tn.esprit.entities.Tourist;
import tn.esprit.entities.User;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class News {

    @FXML
    private ListView<String> newsListView;

    @FXML
    private VBox newsContainer; // Injecting the VBox directly using @FXML

    private final String apiKey = "9b9eee09e51440dfb755b0ecdde68978";
    private final String newsApiUrl = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + apiKey;

    @FXML
    public void initialize() {
        try {
            JSONArray articles = fetchNewsArticles();
            displayNews(articles);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error
        }
    }

    private JSONArray fetchNewsArticles() throws IOException {
        URL url = new URL(newsApiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream inputStream = connection.getInputStream();
        StringBuilder response = new StringBuilder();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            response.append(new String(buffer, 0, bytesRead));
        }
        inputStream.close();

        connection.disconnect();

        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse.getJSONArray("articles");
    }

    private void displayNews(JSONArray articles) {
        for (int i = 0; i < articles.length(); i++) {
            JSONObject article = articles.getJSONObject(i);

            String title = article.getString("title");
            String url = article.getString("url");
            String imageUrl = article.optString("urlToImage"); // Use optString to handle missing or non-string values

            if (imageUrl.isEmpty()) {
                continue; // Skip this article if imageUrl is empty or not a string
            }

            VBox articleBox = new VBox();
            articleBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1px; -fx-padding: 10px;");

            ImageView imageView = new ImageView(new Image(imageUrl));
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);

            Hyperlink titleLink = new Hyperlink(title);
            titleLink.setOnAction(event -> openWebPage(url));

            articleBox.getChildren().addAll(imageView, titleLink);
            newsContainer.getChildren().add(articleBox);
        }
    }


    private void openWebPage(String url) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URL(url).toURI());
            } catch (Exception e) {
                e.printStackTrace();
                // Handle error
            }
        }
    } //////////////////////////////////////////////////////////////////////////////////
    public void home(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
        newsContainer.getScene().setRoot(root);
    }
    @FXML
    void achievements(ActionEvent event)  throws IOException {
       /* Parent root = FXMLLoader.load(getClass().getResource("/ConsultActivities.fxml"));
        combobox.getScene().setRoot(root);*/
    }

    @FXML
    void forum(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Forumuser.fxml"));
        newsContainer.getScene().setRoot(root);
    }

    @FXML
    void locations(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LocationFront.fxml"));
        newsContainer.getScene().setRoot(root);
    }


    @FXML
    void services(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/HostelFront.fxml"));
        newsContainer.getScene().setRoot(root);

    }

    public void account(ActionEvent actionEvent) {
        // Retrieve the authenticated user from the Login class
        User user = Login.getAuthenticatedUser();

        if (user == null) {
            // Handle the case where the user is not authenticated
            showAlert("Error", "You are not authenticated. Please sign in.");
            return;
        }

        // Redirect based on user type
        if (user instanceof Admin) {
            // Handle the case where the user is logged in as Admin
            // You can redirect to a different page or display a message
            showAlert("Information", "You are logged in as an Admin.");
        } else if (user instanceof Tourist) {
            // Redirect to TouristAccount screen
            loadTouristAccountScreen(actionEvent, (Tourist) user);
        } else if (user instanceof LocalCom) {
            // Redirect to LocalComAccount screen
            loadLocalComAccountScreen(actionEvent, (LocalCom) user);
        } else {
            // Handle other cases or show a login page
            // You may want to implement a login functionality here
        }
    }
    ///
    private void loadTouristAccountScreen(ActionEvent event, Tourist tourist) {
        try {
            // Load the TouristAccount.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TouristAccount.fxml"));
            Parent root = loader.load();
            TouristAccount touristAccountController = loader.getController();
            touristAccountController.initialize();  // Pass the Tourist instance
            showStage(event, root, "Tourist Account");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLocalComAccountScreen(ActionEvent event, LocalCom localcom) {
        try {
            // Load the LocalComAccount.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LocalComAccount.fxml"));
            Parent root = loader.load();
            showStage(event, root, "LocalCom Account");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showStage(ActionEvent event, Parent root, String title) {
        // Your existing showStage method
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}