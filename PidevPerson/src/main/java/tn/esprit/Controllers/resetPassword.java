package tn.esprit.Controllers;

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
        // clear alert
        emailAlertId.setText("");

        if (!us.doesEmailExist(emailFieldId.getText())) {
            emailAlertId.setText("No account is associated with this e-mail address.");
            emailAlertId.setStyle("-fx-background-color: #ff4d4d;");
        } else {

            // Generate a verification code
            int UserID = us.getUidByEmail(emailFieldId.getText());
            User u = us.searchByUid(UserID);
            String verificationCode = verificationCodeGenerator.generateVerificationCode(u);
            u.setVerification_code(verificationCode);
            us.update(u);
            System.out.println(u);
            System.out.println(u.getEmail() + " " + verificationCode);

            // Send the verification email
            emailService.sendVerificationEmail(u.getEmail(), verificationCode);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("A verification code has been sent to the provided e-mail address.");
            alert.showAndWait();

            // Get the current stage from any node in the scene graph
            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the new FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));


            // Create a new stage for the new window
            Stage newStage = new Stage();


            // Set the scene with the new root
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.setTitle("Home");

            // Close the old stage
            oldStage.close();

            // Show the new stage
            newStage.show();

            System.out.println("moved");
        }

    }


}
