package tn.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {
    public MainFx() {
    }

    public void start (Stage stage) throws IOException {
      FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/forumManagement.fxml"));
     // FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/AddPost.fxml"));

        Parent root=loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Post Management");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
