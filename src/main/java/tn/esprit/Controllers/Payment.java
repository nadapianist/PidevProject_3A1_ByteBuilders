package tn.esprit.Controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.utils.stripe_methode;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Payment {


    @FXML
    private void processPayment() {
       /* try {
            // Set your secret key here
            Stripe.apiKey = "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";

            // Create a PaymentIntent with other payment details
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(1000L) // Amount in cents (e.g., $10.00)
                    .setCurrency("usd")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            // If the payment was successful, display a success message
            System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
        } catch (StripeException e) {
            // If there was an error processing the payment, display the error message
            System.out.println("Payment failed. Error: " + e.getMessage());
        }*/
    }

    @FXML
    private TextField Tf_NumCarte;
    @FXML
    private TextField Tf_Annee;
    @FXML
    private TextField Tf_Mois;


    stripe_methode stm=new stripe_methode();

    @FXML
    private PasswordField Tf_Code_Verif;
    @FXML
    private Label lbl_total;




}
