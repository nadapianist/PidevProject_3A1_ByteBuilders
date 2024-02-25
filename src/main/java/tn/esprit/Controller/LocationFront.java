package tn.esprit.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.Services.LocationService;

import java.io.IOException;
import java.sql.*;

public class LocationFront {
    @FXML
    public RadioButton SortButton;
    @FXML
    public Button SearchButton;
    @FXML
    private TextField Search_field;
    @FXML
    private VBox hostelBox;
    @FXML
    private Button dashboard;
    private final LocationService ls = new LocationService();
    @FXML
    void Dashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ShowLocations.fxml"));
        dashboard.getScene().setRoot(root);

    }
    public void initialize() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM location");
            ResultSet resultSet = statement.executeQuery();

            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                String name = resultSet.getString("Name");
                String info = resultSet.getString("info");
                String category = resultSet.getString("category");
                int activity = resultSet.getInt("activity");
                int hostel = resultSet.getInt("hostel");
                // Create UI elements

                Label nameLabel = new Label(" Name: " + name);
                Label InfoLabel = new Label("Info: " + info);
                Label categoryLabel = new Label("Category: " + category);
                Label activityLabel = new Label("ActivityId: " + activity);
                Label hostelLabel = new Label("HostelId: " + hostel);
                Button MoreInfoButton = new Button("See Hostels!");

                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(nameLabel, InfoLabel,categoryLabel,activityLabel,hostelLabel, MoreInfoButton);

                // Create HBox to hold VBox
                HBox LocationInfo = new HBox(10, vbox);

                // Add HBox to the VBox
                hostelBox.getChildren().add(LocationInfo);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }

    public void SearchByName(ActionEvent actionEvent) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `location` WHERE `Name`=?");
            statement.setString(1,Search_field.getText().trim());
            System.out.println("You're looking for a location with name:"+Search_field.getText());
            hostelBox.getChildren().clear();

            ResultSet resultSet = statement.executeQuery();

            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                String name = resultSet.getString("Name");
                String info = resultSet.getString("info");
                String category = resultSet.getString("category");
                int activity = resultSet.getInt("activity");
                int hostel = resultSet.getInt("hostel");
                // Create UI elements

                Label nameLabel = new Label(" Name: " + name);
                Label InfoLabel = new Label("Info: " + info);
                Label categoryLabel = new Label("Category: " + category);
                Label activityLabel = new Label("ActivityId: " + activity);
                Label hostelLabel = new Label("HostelId: " + hostel);
                Button MoreInfoButton = new Button("See Hostels!");

                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(nameLabel, InfoLabel,categoryLabel,activityLabel,hostelLabel, MoreInfoButton);

                // Create HBox to hold VBox
                HBox LocationInfo = new HBox(10, vbox);

                // Add HBox to the VBox
                hostelBox.getChildren().add(LocationInfo);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }

    public void SortByCategory(ActionEvent actionEvent) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * from `location` ORDER BY `category` DESC");
            hostelBox.getChildren().clear();

            ResultSet resultSet = statement.executeQuery();

            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                String name = resultSet.getString("Name");
                String info = resultSet.getString("info");
                String category = resultSet.getString("category");
                int activity = resultSet.getInt("activity");
                int hostel = resultSet.getInt("hostel");
                // Create UI elements

                Label nameLabel = new Label(" Name: " + name);
                Label InfoLabel = new Label("Info: " + info);
                Label categoryLabel = new Label("Category: " + category);
                Label activityLabel = new Label("ActivityId: " + activity);
                Label hostelLabel = new Label("HostelId: " + hostel);
                Button MoreInfoButton = new Button("See Hostels!");

                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(nameLabel, InfoLabel,categoryLabel,activityLabel,hostelLabel, MoreInfoButton);

                // Create HBox to hold VBox
                HBox LocationInfo = new HBox(10, vbox);

                // Add HBox to the VBox
                hostelBox.getChildren().add(LocationInfo);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }
}
