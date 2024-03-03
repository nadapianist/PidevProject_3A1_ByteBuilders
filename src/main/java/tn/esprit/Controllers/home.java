package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tn.esprit.entities.Admin;
import tn.esprit.entities.LocalCom;
import tn.esprit.entities.Tourist;
import tn.esprit.entities.User;

import java.io.IOException;

public class home {
    public void home(ActionEvent actionEvent) {
        try {
            // Load the Home.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = loader.load();

            // Set up the primary stage
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Home Page");
            primaryStage.setScene(new Scene(root));

            // Show the stage
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
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
