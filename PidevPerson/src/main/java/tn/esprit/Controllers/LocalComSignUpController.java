package tn.esprit.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.entities.Tourist;
import tn.esprit.entities.User;
import tn.esprit.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LocalComSignUpController {

    UserService UserService = new UserService();
    UserService su = new UserService();


    @FXML
    private TextField AvailabilityID;

    @FXML
    private TextField EmailID;

    @FXML
    private TextField FnameID;

    @FXML
    private TextField LnameID;

    @FXML
    private TextField PhoneID;

    @FXML
    private PasswordField PwdID;



    public Boolean VerifUserChamps() {
        boolean verif = false;
        String style = " -fx-border-color: red;";
        if (this.EmailID.getText().trim().equals("")) {
            this.EmailID.setStyle(style);
            verif = true;
        }

        if (this.PwdID.getText().trim().equals("")) {
            this.PwdID.setStyle(style);
            verif = true;
        }

        if (this.FnameID.getText().trim().equals("")) {
            this.FnameID.setStyle(style);
            verif = true;
        }
        if (this.LnameID.getText().trim().equals("")) {
            this.LnameID.setStyle(style);
            verif = true;
        }

        if (this.PhoneID.getText().trim().equals("")) {
            this.PhoneID.setStyle(style);
            verif = true;
        }

        if (this.AvailabilityID.getText().trim().equals("")) {
            this.AvailabilityID.setStyle(style);
            verif = true;
        }

        if (!verif) {
            return true;
        } else {
            Alert al = new Alert(AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("Verifier les champs");
            al.setHeaderText((String) null);
            al.show();
            return false;
        }
    }

    public Boolean CheckLogin() throws SQLException {
        Boolean verif = true;
        List<User> list_user = this.UserService.listAll();

        for (int i = 0; i < list_user.size(); ++i) {
            if (((User) list_user.get(i)).getEmail().equals(this.EmailID.getText())) {
                verif = false;
            }
        }

        if (!verif) {
            Alert al = new Alert(AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("Email exist already !");
            al.setHeaderText((String) null);
            al.show();
        }

        return verif;
    }

    public boolean ValidateEmail() {
        Pattern p = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher m = p.matcher(this.EmailID.getText());
        if (m.find() && m.group().equals(this.EmailID.getText())) {
            return true;
        } else {
            Alert al = new Alert(AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("Email is wrong");
            EmailID.setText("");
            al.setHeaderText((String) null);
            al.show();
            return false;
        }
    }

    public boolean validatePhoneNumber() {
        Pattern p = Pattern.compile("[0-9]+\\.[0-9]+|[0-9]+");
        Matcher m = p.matcher(this.PhoneID.getText());
        if (m.find() && m.group().equals(this.PhoneID.getText()) && this.PhoneID.getText().length() == 8) {
            return true;
        } else {
            Alert al = new Alert(AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("phone number only can contain 8 didgets");
            PhoneID.setText("");
            al.setHeaderText((String) null);
            al.show();
            return false;
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean restrictSpacesInFnameName() {
        String trimmedValue = FnameID.getText().trim();
        if (!FnameID.getText().equals(trimmedValue)) {
            FnameID.setText(trimmedValue);
        }

        if (trimmedValue.contains(" ")) {
            showAlert("Error", "Spaces are not allowed in the FirstName.");
            FnameID.setText("");
            return false;
        }
        return true;
    }
    private boolean allowOnlyLettersInField(TextField textField, String fieldName) {
        String trimmedValue = textField.getText().trim();
        if (!textField.getText().equals(trimmedValue)) {
            textField.setText(trimmedValue);
        }

        if (!trimmedValue.matches("^[a-zA-Z]*$")) {
            showAlert("Error", "Only letters are allowed in the " + fieldName + " field.");
            textField.setText("");
            return false;
        }
        return true;
    }

    public void SignUPLoc(javafx.event.ActionEvent actionEvent) throws SQLException {
        Tourist user = new Tourist();
        boolean allValidationsPassed = false; // Variable to track validation status

        if (this.CheckLogin() && this.VerifUserChamps() &&
                this.validatePhoneNumber() && this.ValidateEmail() &&
                this.restrictSpacesInFnameName() && this.allowOnlyLettersInField(FnameID, "First Name") &&
                this.allowOnlyLettersInField(LnameID, "Last Name") &&
                this.allowOnlyLettersInField(AvailabilityID, "Specialization") && this.validatePhoneNumber()) {
            allValidationsPassed = true; // Set to true if all validations pass

            user.setEmail(this.EmailID.getText());
            user.setPwd(this.su.encrypt(this.PwdID.getText()));
            user.setFname(this.FnameID.getText());
            user.setLname(this.LnameID.getText());
            user.setPhone(Integer.parseInt(this.PhoneID.getText()));
            user.setBio(this.AvailabilityID.getText());

            this.UserService.add(user);
            Alert resAlert = new Alert(AlertType.INFORMATION);
            resAlert.setHeaderText((String) null);
            resAlert.setContentText("Account created successfully");
            resAlert.showAndWait();
        }

        if (allValidationsPassed) { // Redirect only if all validations passed
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

            // Open login window
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
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
