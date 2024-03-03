package tn.esprit.Controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entities.Hostel;
import javafx.scene.input.KeyEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class book {
    @FXML
    private TextField Search_field;
    @FXML
    private TextField Tf_NumCarte;
    @FXML
    private TextField Tf_Annee;
    @FXML
    private TextField Tf_Mois;
    @FXML
    private VBox vbox_details;
    @FXML
    private Button dashboard;

    @FXML
    private ImageView logo;
    @FXML
    private Label hostelNameLabel;

    @FXML
    private Label nbstarLabel;

    @FXML
    private Label infoLabel;

    @FXML
    void Dashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ActivityManagement.fxml"));
        Search_field.getScene().setRoot(root);
    }

    private Hostel hostel;

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
        // Now you can use this.hostel to access hostel information and update UI elements in the FXML
        updateUI();
    }

    private void updateUI() {
        if (hostel != null) {
            hostelNameLabel.setText("Hostel Name: " + hostel.getName_hostel());
            nbstarLabel.setText("Nb° stars: " + hostel.getNBstars());
            infoLabel.setText("Info: " + hostel.getInfo());
        } else {
            // Handle the case when hostel is null
            hostelNameLabel.setText("Hostel Name: N/A");
            nbstarLabel.setText("Nb° stars: N/A");
            infoLabel.setText("Info: N/A");
        }
    }

    @FXML
    private void Button_Valider(ActionEvent event) {
        try {
            // Perform actions after successful payment
            // For example, you can update the UI or navigate to another page
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/PaymentOnline.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


            // Close the current window and open PaymentOnline.fxml

        } catch (IOException e) {
            // Handle exceptions here, e.g., show an error dialog
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Payment Error");
            alert.setContentText("An error occurred during payment processing: " + e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    private void handleCreditCardInput(KeyEvent event) {
        String input = Tf_NumCarte.getText();
        // Regular expression for a valid credit card number (16 digits)
        String regex = "\\d{16}";
        if (!input.matches(regex)) {
            Tf_NumCarte.setStyle("-fx-border-color: red;");
        } else {
            Tf_NumCarte.setStyle("-fx-border-color: green;");
        }
    }
    private boolean check_expDate(TextField Tf_Annee, TextField Tf_Mois) {
        int year = Integer.parseInt(Tf_Annee.getText());
        int month = Integer.parseInt(Tf_Mois.getText());
        boolean valid = false;
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        if (year > currentYear || (year == currentYear && month >= currentMonth)) {
            valid = true;
        }

        return valid;
    }
    private boolean check_card_num(String cardNumber) {
        // Trim the input string to remove any leading or trailing whitespace
        cardNumber = cardNumber.trim();
        // Step 1: Check length
        int length = cardNumber.length();
        if (length < 13 || length > 19) {
            return false;
        }
        String regex = "^(?:(?:4[0-9]{12}(?:[0-9]{3})?)|(?:5[1-5][0-9]{14})|(?:6(?:011|5[0-9][0-9])[0-9]{12})|(?:3[47][0-9]{13})|(?:3(?:0[0-5]|[68][0-9])[0-9]{11})|(?:((?:2131|1800|35[0-9]{3})[0-9]{11})))$";
        // Create a Pattern object with the regular expression
        Pattern pattern = Pattern.compile(regex);

        // Match the pattern against the credit card number
        Matcher matcher = pattern.matcher(cardNumber);

        // Return true if the pattern matches, false otherwise
        return matcher.matches();
    }
    @FXML
    private void processPayment(ActionEvent actionEvent) {
        // Perform credit card validation
        String input = Tf_NumCarte.getText();
        // Regular expression for a valid credit card number (16 digits)
        String regex = "\\d{16}";
        if (!check_card_num(input) || !input.matches(regex)) {
            Tf_NumCarte.setStyle("-fx-border-color: red;");
            // Show error alert for invalid credit card number
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Credit Card Number");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid credit card number.");
            alert.showAndWait();
            return; // Return if the credit card number is invalid
        } else {
            Tf_NumCarte.setStyle("-fx-border-color: green;");
        }

        // Perform expiry date validation
        if (!check_expDate(Tf_Annee, Tf_Mois)) {
            Tf_Annee.setStyle("-fx-border-color: red;");
            Tf_Mois.setStyle("-fx-border-color: red;");
            // Show error alert for invalid expiry date
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Expiry Date");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid expiry date.");
            alert.showAndWait();
            return; // Return if the expiry date is invalid
        } else {
            Tf_Annee.setStyle("-fx-border-color: green;");
            Tf_Mois.setStyle("-fx-border-color: green;");
        }

        try {
            // Set your secret key here
            Stripe.apiKey = "sk_test_51OpnDDAf6965nhXEGZ41YBHDh7NJ4Uigm7rwRpOB6fDGn9sWxHYCgDCZ2AzfiJs3B8xhBZBM8KSYVtLSTNSSefz400CTjx7oXF";

            // Create a PaymentIntent with other payment details
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(1000L) // Amount in cents (e.g., $10.00)
                    .setCurrency("usd")
                    .addPaymentMethodType("card") // Specify payment method types
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            // Show alert for successful payment
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Payment Successful");
            alert.setHeaderText(null);
            alert.setContentText("Your payment was successful. PaymentIntent ID: " + intent.getId());
            alert.showAndWait();

            // Write credit card information to a text file
            writeCreditCardInfoToFile(input, Tf_Annee.getText(), Tf_Mois.getText());

        } catch (StripeException e) {
            // If there was an error processing the payment, display the error message
            System.out.println("Payment failed. Error: " + e.getMessage());
        }
    }

    private void writeCreditCardInfoToFile(String cardNumber, String expiryYear, String expiryMonth) {
        try {
            // Create a FileWriter object with the desired file name
            FileWriter writer = new FileWriter("credit_card_info.txt");
            // Write credit card information to the file
            writer.write("*****************************Credit Card Receipt*****************************\n");
            writer.write("Credit Card Number: " + cardNumber + "\n");
            writer.write("Expiry Date: " + expiryMonth + "/" + expiryYear + "\n");

            // Close the FileWriter object
            writer.close();

            System.out.println("Credit card information written to credit_card_info.txt");

        } catch (IOException e) {
            // Handle any IO exceptions
            e.printStackTrace();
        }
    }


}
