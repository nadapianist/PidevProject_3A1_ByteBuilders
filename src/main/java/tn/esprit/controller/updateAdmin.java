package tn.esprit.controller;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import tn.esprit.entities.Admin;
import java.io.IOException;
import java.sql.SQLException;

public class updateAdmin {

    @FXML
    private TextField modidA;

    @FXML
    private TextField modmailA;

    @FXML
    private PasswordField modpwdA;


    private final UserService ser = new UserService();

    public void modA(ActionEvent actionEvent) throws SQLException {
        Admin adminModifie = createModifiedAdmin();
        if (adminModifie != null) {
            ser.update(adminModifie);
            Stage currentStage = (Stage) modmailA.getScene().getWindow();
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

    private Admin createModifiedAdmin() {
        int idUtil = Integer.parseInt(modidA.getText());
        String email = modmailA.getText();
        String mdp = modpwdA.getText();

        if (email.isEmpty() || mdp.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs obligatoires.");
            return null;
        }

        Admin adminModifie = new Admin(idUtil, email, mdp);
        return adminModifie;
    }

    public void initDonneesAdmin(Admin admin) {

        modidA.setText(String.valueOf(admin.getUserID()));
        modmailA.setText(admin.getEmail());
        modpwdA.setText(admin.getPwd());
    }
}

