package tn.esprit.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.entities.User;
import tn.esprit.services.UserService;
import tn.esprit.services.*;

import java.io.IOException;
import java.sql.SQLException;

public class resetPassword {

    @FXML
    private Button continueButton;

    @FXML
    private Text emailAlertId;

    @FXML
    private TextField emailFieldId;

    @FXML
    private TextField txtnouveaumdp;
    @FXML
    private TextField txtcodeverif;
    @FXML
    private Text emailTextId;

    @FXML
    private ImageView goBackButtonId;

    @FXML
    private Text resetPwdTextId;

    @FXML
    void HoverIn(MouseEvent event) {
        continueButton.setStyle("-fx-background-color: #E15B10; -fx-background-radius: 55;");

    }

    @FXML
    void HoverOut(MouseEvent event) {
        continueButton.setStyle("-fx-background-color: #E18B10; -fx-background-radius: 55;");

    }



    UserService us = new UserService();
    emailService em = new emailService();
    verificationCodeGenerator vcg = new verificationCodeGenerator();

    @FXML
    void redirectToVerif(ActionEvent event) throws SQLException, IOException {
        emailAlertId.setText("");
        try {
            if (!us.doesEmailExist(emailFieldId.getText())) {
                emailAlertId.setText("No account is associated with this e-mail address.");
                emailAlertId.setStyle("-fx-background-color: #ff4d4d;");
            } else {
                int userId = us.getUidByEmail(emailFieldId.getText());

                // Generate a new verification code
                String verificationCode = vcg.generateVerificationCode(us.searchByUid(userId));

                // Update the verification code in the database
                us.updateVerificationCode(userId, verificationCode);

                // Envoi de l'e-mail avec le code de vérification
                em.sendVerificationEmail(us.getEmailByUserId(userId), verificationCode);

                showInformationAlert("Confirmation", "A verification code has been sent to the provided e-mail address.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void changerMdp(ActionEvent actionEvent) {
        try {
            String email = emailFieldId.getText();
            String verificationCode = txtcodeverif.getText();
            String newPassword = txtnouveaumdp.getText();

            // Check if the email exists
            if (!us.doesEmailExist(email)) {
                showErrorAlert("Error", "No account is associated with this e-mail address.");
                return;
            }

            int userId = us.getUidByEmail(email);
            User user = us.searchByUid(userId);

            // Check if the entered verification code is correct
            if (user.getVerifcode() == null || !verificationCode.trim().equals(user.getVerifcode().trim())) {
                System.out.println("Database Verification Code: " + user.getVerifcode());
                System.out.println("Entered Verification Code: " + verificationCode);
                showErrorAlert("Error", "Invalid verification code. Please check your code and try again.");
                return;
            }

            // Update the user's password
            us.updatePassword(userId, newPassword);

            // Clear the verification code in the database
            us.updateVerificationCode(userId, null);

            // Display success alert
            showInformationAlert("Success", "Password has been successfully updated.");

        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred while updating the password.");
        }
    }

}
