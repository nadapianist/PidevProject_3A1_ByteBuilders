package tn.esprit.Controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.stage.Stage;
import tn.esprit.entities.LocalCom;
import tn.esprit.entities.Tourist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import tn.esprit.entities.Tourist;
import tn.esprit.entities.User;
import tn.esprit.services.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class updateTourist {
    @FXML
    private TextField userID;

    @FXML
    private TextField BioID;

    @FXML
    private TextField EmailID;

    @FXML
    private TextField FnameID;

    @FXML
    private TextField LnameID;

    @FXML
    private PasswordField PasswordID;

    @FXML
    private TextField PhoneID;

    @FXML
    private TextField PreferencesID;


    tn.esprit.services.UserService UserService = new UserService();
    UserService su = new UserService();

    private Tourist creteModifiedTourist() {
        int idUtil = Integer.parseInt(userID.getText());
        String name = FnameID.getText();
        String lname = LnameID.getText();
        String email = EmailID.getText();
        String mdp = PasswordID.getText();
        int phone = Integer.parseInt(PhoneID.getText());
        String bio = BioID.getText();
        String pref = PreferencesID.getText();

        if (name.isEmpty() || lname.isEmpty() || email.isEmpty() || mdp.isEmpty() || bio.isEmpty()||pref.isEmpty()) {
            System.out.println("All fields are required.");
            return null;
        }

        Tourist modifiedTourist = new Tourist(idUtil, email, mdp, name, lname, phone, bio,pref);
        return modifiedTourist;
    }

    public void initDonneesTourist(Tourist tourist) {
        userID.setText(String.valueOf(tourist.getUserID()));
        EmailID.setText(String.valueOf(tourist.getEmail()));
        PasswordID.setText(tourist.getPwd());
        FnameID.setText(tourist.getFname());
        LnameID.setText(tourist.getLname());
        PhoneID.setText(String.valueOf(tourist.getPhone()));
        BioID.setText(tourist.getBio());
        PreferencesID.setText(tourist.getPreferences());
    }

    public void Modify(ActionEvent actionEvent) throws SQLException {
        Tourist modifiedTourist = creteModifiedTourist();
        if (modifiedTourist != null) {
            su.update(modifiedTourist);
            Stage currentStage = (Stage) FnameID.getScene().getWindow();
            currentStage.close();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayUser.fxml"));
                Parent root = loader.load();

                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    }