package tn.esprit.Controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tn.esprit.entities.Admin;
import tn.esprit.entities.User;
import tn.esprit.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import tn.esprit.services.UserService;
import javafx.scene.Node;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminSignUp {

    @FXML
    private TextField mailA;

    @FXML
    private PasswordField pwdA;


    UserService UserService = new UserService();
    UserService su = new UserService();

    public Boolean VerifUserChamps() {
        boolean verif = false;
        String style = " -fx-border-color: red;";
        if (this.mailA.getText().trim().equals("")) {
            this.mailA.setStyle(style);
            verif = true;
        }

        if (this.pwdA.getText().trim().equals("")) {
            this.pwdA.setStyle(style);
            verif = true;
        }

        if (!verif) {
            return true;
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("all fields are required");
            al.setHeaderText((String) null);
            al.show();
            return false;
        }
    }

    public Boolean CheckLogin() throws SQLException {
        Boolean verif = true;
        List<User> list_user = this.UserService.listAll();

        for (int i = 0; i < list_user.size(); ++i) {
            if (((User) list_user.get(i)).getEmail().equals(this.mailA.getText())) {
                verif = false;
            }
        }

        if (!verif) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("Email exist already !");
            al.setHeaderText((String) null);
            al.show();
        }

        return verif;
    }

    //Input Control: Validate email
    public boolean ValidateEmail() {
        Pattern p = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher m = p.matcher(this.mailA.getText());
        if (m.find() && m.group().equals(this.mailA.getText())) {
            return true;
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("Email is wrong");
            mailA.setText("");
            al.setHeaderText((String) null);
            al.show();
            return false;
        }
    }

    private boolean validatePassword() {
        String password = pwdA.getText();
        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!?*-/]).{8,}$")) {
            showAlert("Error", "Password must be at least 8 characters and contain letters, numbers, uppercase, and lowercase, and at least one special character.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
@FXML
    public void signUpA(javafx.event.ActionEvent actionEvent) throws SQLException {
        Admin user = new Admin();

        // Perform validation checks
        if (this.CheckLogin() && this.VerifUserChamps() && this.ValidateEmail() && this.validatePassword()) {

            // Set user properties
            user.setEmail(this.mailA.getText());
            user.setPwd(this.su.encrypt(this.pwdA.getText()));

            // Add user to the database
            this.UserService.add(user);

            // Show success message
            Alert resAlert = new Alert(Alert.AlertType.INFORMATION);
            resAlert.setHeaderText((String) null);
            resAlert.setContentText("Account created successfully");
            resAlert.showAndWait();

            // Close current window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

            // Open login window
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayUser.fxml"));
                Parent root = loader.load();
                Stage loginStage = new Stage();
                loginStage.setScene(new Scene(root));
                loginStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
