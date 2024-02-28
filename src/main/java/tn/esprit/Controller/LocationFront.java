package tn.esprit.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.apache.poi.ss.usermodel.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tn.esprit.Entity.Location;
import tn.esprit.Services.LocationService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class LocationFront {
    @FXML
    public RadioButton SortButton;
    @FXML
    private ComboBox<String> SortBox;

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
SortBox.getItems().addAll("Name","Info","Category");
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

    public void SearchByName() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `location` WHERE `Name` LIKE ?");
            statement.setString(1,"%"+Search_field.getText()+"%");
            //System.out.println("You're looking for a location with name:"+Search_field.getText());
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

    public void SortByName() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * from `location` ORDER BY `Name` DESC");
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
    public void SortByInfo() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * from `location` ORDER BY `info` DESC");
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
    public void SortByCategory() {
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

    public void AutoSearch(KeyEvent keyEvent) {
        Search_field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                SearchByName();
            }
        });
    }

    private void exportToExcel() {
        try {
            List<Location> locations = ls.displayList();

            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Location Data");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Name");
            headerRow.createCell(1).setCellValue("Info");
            headerRow.createCell(2).setCellValue("Category");

            // Create data rows
            int rowNum = 1;
            for (Location location : locations) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(location.getName());
                row.createCell(1).setCellValue(location.getInfo());
                row.createCell(2).setCellValue(location.getCategory());
            }

            try (FileOutputStream fileOut = new FileOutputStream("locations.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Excel file exported successfully!");
            } catch (IOException e) {
                System.out.println("Error exporting Excel file: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving locations from service: " + e.getMessage());
        }
    }

@FXML
    public void HandleSort(ActionEvent actionEvent) {

        String selectedOption = SortBox.getValue();
        if (selectedOption != null) {
            switch (selectedOption) {
                case "Name":
                    SortByName();
                    break;
                case "Info":
                    SortByInfo();
                    break;
                case "Category":
                    SortByCategory();
                    break;
                default:
                    System.out.println("No option is selected!");
                    // Handle default case or do nothing
                    break;
            }
        }

    }


    public void ExportData(ActionEvent actionEvent) {
        exportToExcel();
    }
}
