package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import tn.esprit.services.verifCode;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/*public class resetPwd {
    tn.esprit.services.UserService UserService = new UserService();
    UserService us = new UserService();
    verifCode vcg = new verifCode();


    @FXML
    private TextField emailF;

    @FXML
    void continueVer(ActionEvent event)throws SQLException, IOException {
        // clear alert
        emailF.setText("");

        if(!us.doesEmailExist(emailFieldId.getText())) {
            emailAlertId.setText("No account is associated with this e-mail address.");
            emailAlertId.setStyle("-fx-background-color: #ff4d4d;");
        }
        else {

            // Generate a verification code
            int uid = us.getUidByEmail(emailFieldId.getText());
            User u = us.searchByUid(uid);
            String verificationCode = VerificationCodeGenerator.generateVerificationCode(u);
            u.setVerification_code(verificationCode);
            us.update(u);
            System.out.println(u);
            System.out.println(u.getEmail()+" "+verificationCode);

            // Send the verification email
            EmailService.sendVerificationEmail(u.getEmail(), verificationCode);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("A verification code has been sent to the provided e-mail address.");
            alert.showAndWait();

            // Get the current stage from any node in the scene graph
            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the new FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/LogIn.fxml"));
            javafx.scene.image.Image icon = new Image("file:/C:/Users/DELL/Documents/3A/Semester 2/PIDEV/Tuni Art/src/images/logo.png");

            // Create a new stage for the new window
            Stage newStage = new Stage();
            newStage.getIcons().add(icon);

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

}*/
