package tn.esprit.Controllers;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entities.Activity;
import tn.esprit.services.ActivityService;
import tn.esprit.utils.MyDb;


import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;



import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
public class ActivityManagement  {
    Connection con;
    Statement stm;
    PreparedStatement pste;

    private final ActivityService as = new ActivityService();
    @FXML
    private TextField Date_actid;

    @FXML
    private TextField Descriptionid;

    @FXML
    private TextField Nameid;

    @FXML
    private TextField Search_field;

    @FXML
    private TableColumn<?, ?> id_act_column;

    @FXML
    private TableColumn<Activity, Date> Date_act;

    @FXML
    private TableColumn<Activity, String> Desc_act;

    @FXML
    private TableColumn<Activity, String> Name_act;

    @FXML
    private TableView<Activity> TableViewActivity;

    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField locationfield;
    @FXML
    private URL location;

    @FXML
    private TextField IDActivity;

    @FXML
    private CategoryAxis x_axis;

    @FXML
    private NumberAxis y_axis;

    @FXML
    private BarChart<String, Integer> Most_searched;

    @FXML
    void AddActivity(ActionEvent event) throws SQLException {
        try {
            // Validate all fields are not empty
            String name = Nameid.getText().trim();
            String dateString = Date_actid.getText().trim();
            String description = Descriptionid.getText().trim();

            // Define the date format expected
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dateFormat.setLenient(false); // Set leniency to false to enforce strict date parsing

            // Parse the string representation of the date into a Date object
            java.util.Date utilDate = dateFormat.parse(dateString);

            // Convert java.util.Date to java.sql.Date
            Date sqlDate = new Date(utilDate.getTime());

            // Create a new Activity object with the converted Date
            as.addd(new Activity(name, sqlDate, description));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("A new Activity is Added");
            alert.showAndWait();

            // Clear fields after successfully adding the activity
          //  clearFields();

        } catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Invalid date format. Please enter date in format dd-MM-yyyy");
            alert.showAndWait();

            // Clear fields if there was a parsing error
           // clearFields();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

            // Clear fields if there was a database error
          //  clearFields();

        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

            // Clear fields if there was an invalid input or empty field
            //clearFields();
        }

    }


    @FXML
    void initialize() throws SQLException {
        try {
            List<Activity> activities = as.diplayList();
            ObservableList<Activity> observableList = FXCollections.observableList(activities);
            TableViewActivity.setItems(observableList);
            id_act_column.setCellValueFactory(new PropertyValueFactory<>("id_act"));
            Name_act.setCellValueFactory(new PropertyValueFactory<>("name"));
            Date_act.setCellValueFactory(new PropertyValueFactory<>("date_act"));
            Desc_act.setCellValueFactory(new PropertyValueFactory<>("description"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void UpdateActivity(ActionEvent event) {
        try {
            String dateString = Date_actid.getText();

            // Define the date format expected
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            // Parse the string representation of the date into a Date object
            java.util.Date utilDate = dateFormat.parse(dateString);

            // Convert java.util.Date to java.sql.Date
            Date sqlDate = new Date(utilDate.getTime());

            // Convert the text into an integer
            int IDValue = Integer.parseInt(IDActivity.getText());

            // Create a new Activity object with the converted Date
            as.update(new Activity(IDValue, Nameid.getText(), sqlDate, Descriptionid.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Activity is updated Sucessfully");
            alert.showAndWait();
          //  clearFields();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
          //  clearFields();
        } catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Invalid date format. Please enter date in format dd-MM-yyyy");
            alert.showAndWait();
           // clearFields();
        }
    }


    @FXML
    void DeleteActivity(ActionEvent event) throws SQLException {

        try {
            // Retrieve the text from the TextField for the ID
            String IDText = IDActivity.getText();

            // Check if the ID text is not empty
            if (!IDText.isEmpty()) {
                // Convert the text into an integer
                int IDValue = Integer.parseInt(IDText);

                // Create a new Activity object with the provided ID
                Activity activityToDelete = new Activity();
                activityToDelete.setId_act(IDValue);

                // Remove the activity from the data source
                as.delete(activityToDelete);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Activity deleted successfully.");
                alert.showAndWait();
               // clearFields();
            } else {
                // Display an error message if the ID text is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter the activity ID.");
                alert.showAndWait();
               //clearFields();
            }
        } catch (NumberFormatException e) {
            // Handle the case where the ID text cannot be parsed as an integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid integer for the activity ID.");
            alert.showAndWait();
           // clearFields();
        }
    }


    @FXML
    void NextChallenge(ActionEvent event) throws IOException {
        Parent root =FXMLLoader.load(getClass().getResource("/ChallengeManagement.fxml"));
        Nameid.getScene().setRoot(root);

    }

    }









