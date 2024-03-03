package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.Controllers.Calendar;
import tn.esprit.Controllers.Login;
import tn.esprit.Controllers.ReviewManagementFront;

import java.io.IOException;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {

          // FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConsultActivities.fxml"));
         //   FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddPost.fxml"));
         //FXMLLoader loader = new FXMLLoader(getClass().getResource("/ActivityManagement.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
         //   FXMLLoader loader = new FXMLLoader(getClass().getResource("/forumManagement.fxml"));

            // Define the root
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Define the name of the stage that we're going to start with
            primaryStage.setTitle("Tunism Desktop App");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., display an error message)
        }
       /* try {
            // Load the FXML file for the main UI
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/calendar.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("Hello!");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., display an error message)
        }*/
       /* try {
            // Load the FXML file for the login view
            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loginLoader.load();

            // Get the Login controller instance
            Login loginController = loginLoader.getController();

            // Create an instance of ReviewManagementFront
            ReviewManagementFront reviewManagementFront = new ReviewManagementFront();

            // Set the ReviewManagementFront instance in the Login controller
            loginController.setReviewManagementFront(reviewManagementFront);

            // Show the login view
            Scene scene = new Scene(root);
            primaryStage.setTitle("Login");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., display an error message)
        }*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
