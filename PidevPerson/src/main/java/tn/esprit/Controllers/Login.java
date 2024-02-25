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
    UserService UserService = new UserService();
    UserService su = new UserService();

    @FXML
    void SignIn(ActionEvent event) throws SQLException {
        Alert al;
        String encPass = su.encrypt(txtpassword.getText());
        User user = su.find(txtemail.getText(), encPass);
        if (user == null) {
            al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("invalid login or mot de passe");
            al.setHeaderText((String) null);
            al.show();
            return;
        }
        if (user instanceof Admin) {
            // Redirect to the Display screen
            try {
                Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                loginStage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayUser.fxml"));
                Parent root = loader.load();
                Stage displayStage = new Stage();
                displayStage.setScene(new Scene(root));
                displayStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (user instanceof Tourist) {
            Tourist tourist = (Tourist) user;
            System.out.println("tourist");
        } else if (user instanceof LocalCom) {
            LocalCom localcom = (LocalCom) user;
            System.out.println("localCom");
        } else {
            System.out.println("Autre type d'utilisateur ou null");
        }
        // Display a sign-in success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign-in Success");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully signed in!");
        alert.showAndWait();
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

    }


