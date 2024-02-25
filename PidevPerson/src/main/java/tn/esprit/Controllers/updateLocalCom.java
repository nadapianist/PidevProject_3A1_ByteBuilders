package tn.esprit.Controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.entities.LocalCom;
import tn.esprit.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class updateLocalCom {

    @FXML
    private TextField AvailabilityID;

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
    private TextField userID;

    UserService UserService = new UserService();
    UserService su = new UserService();

    private LocalCom createModifiedLocal() {
        int idUtil = Integer.parseInt(userID.getText());
        String name = FnameID.getText();
        String lname = LnameID.getText();
        String email = EmailID.getText();
        String mdp = PasswordID.getText();
        int phone = Integer.parseInt(PhoneID.getText());
        String avai = AvailabilityID.getText();

        if (name.isEmpty() || lname.isEmpty() || email.isEmpty() || mdp.isEmpty() || avai.isEmpty()) {
            System.out.println("All fields are required.");
            return null;
        }

        LocalCom localcomModified = new LocalCom(idUtil, email, mdp, name, lname, phone, avai);
        return localcomModified;
    }

    public void initDonneesLocal(LocalCom localCom) {
        userID.setText(String.valueOf(localCom.getUserID()));
        EmailID.setText(String.valueOf(localCom.getEmail()));
        PasswordID.setText(localCom.getPwd());
        FnameID.setText(localCom.getFname());
        LnameID.setText(localCom.getLname());
        PhoneID.setText(String.valueOf(localCom.getPhone()));
        AvailabilityID.setText(localCom.getAvailability());
    }

    public void Modify(ActionEvent actionEvent) throws SQLException {
        LocalCom modifiiedLocal = createModifiedLocal();
        if (modifiiedLocal != null) {
            su.update(modifiiedLocal);
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
