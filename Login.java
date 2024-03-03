package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tn.esprit.entities.Admin;
import tn.esprit.entities.LocalCom;
import tn.esprit.entities.Tourist;
import tn.esprit.entities.User;
import tn.esprit.services.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class Login {

    @FXML
    private TextField txtemail;

    @FXML
    private PasswordField txtpassword;
    private static User authenticatedUser;

    public static User getAuthenticatedUser() {
        return authenticatedUser;
    }


    @FXML
    void Local(ActionEvent event) {
        try {
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Close the login window
            loginStage.close();

            // Load the FXML file for the sign-up window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LocalComSignUp.fxml"));
            Parent root = loader.load();

            // Create a new stage for the sign-up window
            Stage signUpStage = new Stage();
            signUpStage.setScene(new Scene(root));
            signUpStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void Tourist(ActionEvent event) {
        try {
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Close the login window
            loginStage.close();

            // Load the FXML file for the sign-up window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TouristSignUp.fxml"));
            Parent root = loader.load();

            // Create a new stage for the sign-up window
            Stage signUpStage = new Stage();
            signUpStage.setScene(new Scene(root));
            signUpStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    UserService userService = new UserService();

    UserService su = new UserService();
    private static Tourist connectedTourist;
    public static Tourist getConnectedTourist() {
        return connectedTourist;
    }

    @FXML
    void SignIn(ActionEvent event) throws SQLException {
        Alert al;

        if (!VerifUserChamps()) {
            return;
        }

        String encPass = su.encrypt(txtpassword.getText());
        User user = su.find(txtemail.getText(), encPass);

        if (user == null) {
            al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("Invalid login or password");
            al.setHeaderText((String) null);
            al.show();
            return;
        }

        if (user.getStatus()) {
            al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("Account is deactivated. Please contact support.");
            al.setHeaderText((String) null);
            al.show();
            return;
        }

        if (user != null) {
            authenticatedUser = user;
        }

        // Redirect based on user type
        if (user instanceof Admin) {
            // Redirect to the Display screen
            loadFXML("/DisplayUser.fxml", "Display User");
        } else if (user instanceof Tourist) {
            connectedTourist = (Tourist) user;
            // Redirect to TouristAccount screen
            loadFXML("/home.fxml", "Tourist Account");
        } else if (user instanceof LocalCom) {
            LocalCom localcom = (LocalCom) user;
            System.out.println("localCom");
        } else {
            System.out.println("Other user type or null");
        }

        // Display a sign-in success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign-in Success");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully signed in!");
        alert.showAndWait();
    }

    private void loadFXML(String fxmlPath, String title) {
        try {
            Stage loginStage = (Stage) txtemail.getScene().getWindow();
            loginStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public Boolean VerifUserChamps() {
        boolean verif = false;
        String style = " -fx-border-color: red;";
        if (this.txtemail.getText().trim().equals("")) {
            this.txtemail.setStyle(style);
            verif = true;
        }

        if (this.txtpassword.getText().trim().equals("")) {
            this.txtpassword.setStyle(style);
            verif = true;
        }


        if (!verif) {
            return true;
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("Verifier les champs");
            al.setHeaderText((String) null);
            al.show();
            return false;
        }
    }
    @FXML
    void RedirectToReset(ActionEvent event) throws IOException {
        // Get the current stage (the login window)
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Load the Forgot Password FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resetPwd.fxml"));
        Parent root = loader.load();

        // Create a new stage for the forgot password window
        Stage forgotPasswordStage = new Stage();
        forgotPasswordStage.setScene(new Scene(root));

        // Close the login window
        currentStage.close();

        // Show the forgot password window
        forgotPasswordStage.show();
    }


    }




