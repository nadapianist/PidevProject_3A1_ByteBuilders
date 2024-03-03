package tn.esprit.Controllers;


import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

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
    }

}