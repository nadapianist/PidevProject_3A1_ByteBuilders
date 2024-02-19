package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //define the stage that we're going to start with

        FXMLLoader loader=new FXMLLoader(getClass().getResource("/ActivityManagement.fxml"));
        //define the root
        Parent root=loader.load();
        Scene scene=new Scene(root);

        //define the name of the stage "/ActivityManagement.fxml" that we're going to start with

        stage.setTitle("manageChallenge");
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
