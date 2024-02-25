package tn.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFx extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader=new FXMLLoader(getClass().getResource("/DisplayUser.fxml"));

        Parent root=loader.load();

        Scene scene=new Scene(root);
        stage.setTitle("SignUp");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}