package tn.esprit.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
import tn.esprit.entities.*;
import tn.esprit.services.LocationService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class LocationFront {
    @FXML
    private TextField BrandSearchField;
    @FXML
    private VBox vbox_Transport;
    @FXML
    private ComboBox<String> TransportSortBox;
    @FXML
    private ComboBox<String> LocationSortBox;
    @FXML
    private VBox vbox_Location;
    @FXML
    private TextField Search_field;
    @FXML
    private Button dashboard;
    private final LocationService ls = new LocationService();

    @FXML
    void Dashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ShowLocations.fxml"));
        dashboard.getScene().setRoot(root);

    }

    @FXML
    public void initialize() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM location");
            ResultSet resultSet = statement.executeQuery();
            LocationSortBox.getItems().addAll("Name","Info","Category");
            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                String name = resultSet.getString("Name");
                String info = resultSet.getString("info");
                String category = resultSet.getString("category");

                // Create UI elements

                Label nameLabel = new Label(" Name: " + name);
                Label InfoLabel = new Label("Info: " + info);
                Label categoryLabel = new Label("Category: " + category);



                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(nameLabel, InfoLabel,categoryLabel);

                // Create HBox to hold VBox
                HBox LocationInfo = new HBox(20, vbox);

                // Add HBox to the VBox
                vbox_Location.getChildren().add(LocationInfo);
            }
            PreparedStatement statementT = connection.prepareStatement("SELECT * FROM transport");
            ResultSet resultSetT = statementT.executeQuery();
            TransportSortBox.getItems().addAll("Brand","Distance","Charging Time");
            // Process each row of the result set
            while (resultSetT.next()) {
                // Retrieve data from the result set
                String brand = resultSetT.getString("Brand");
                String type = resultSetT.getString("Type");
                String distance = resultSetT.getString("Distance");
                int chargeTime = resultSetT.getInt("ChargingTime");
                // Create UI elements

                Label BrandLabel = new Label(" Brand: " + brand);
                Label TypeLabel = new Label("Type: " + type);
                Label RangeLabel = new Label("Distance: " + distance);
                Label TimeToChargeLabel = new Label("Charging Time: " + chargeTime);
                Button NavButton=new Button("scan now!");
                NavButton.setOnAction(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/QrCodeView.fxml"));
                        Parent root = loader.load();

                        // Pass transport data to the QR code view controller
                        QrCodeView qrCodeViewController = loader.getController();
                        String transportData = brand + " " + type + " " + distance + " " + chargeTime;
                        qrCodeViewController.setTransportData(transportData);

                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(BrandLabel, TypeLabel,RangeLabel,TimeToChargeLabel,NavButton);

                // Create HBox to hold VBox
                HBox TransportInfo = new HBox(10, vbox);

                // Add HBox to the VBox
                vbox_Transport.getChildren().add(TransportInfo);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }
    //we initiated both transport and location list
    public void SearchByName() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `location` WHERE `Name` LIKE ?");
            statement.setString(1,"%"+Search_field.getText()+"%");
            //System.out.println("You're looking for a location with name:"+Search_field.getText());
            vbox_Location.getChildren().clear();

            ResultSet resultSet = statement.executeQuery();

            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                String name = resultSet.getString("Name");
                String info = resultSet.getString("info");
                String category = resultSet.getString("category");

                // Create UI elements

                Label nameLabel = new Label(" Name: " + name);
                Label InfoLabel = new Label("Info: " + info);
                Label categoryLabel = new Label("Category: " + category);



                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(nameLabel, InfoLabel,categoryLabel);

                // Create HBox to hold VBox
                HBox LocationInfo = new HBox(10, vbox);

                // Add HBox to the VBox
                vbox_Location.getChildren().add(LocationInfo);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }

    public void SortByName() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * from `location` ORDER BY `Name` DESC");
            vbox_Location.getChildren().clear();

            ResultSet resultSet = statement.executeQuery();

            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                String name = resultSet.getString("Name");
                String info = resultSet.getString("info");
                String category = resultSet.getString("category");

                // Create UI elements

                Label nameLabel = new Label(" Name: " + name);
                Label InfoLabel = new Label("Info: " + info);
                Label categoryLabel = new Label("Category: " + category);




                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(nameLabel, InfoLabel,categoryLabel);

                // Create HBox to hold VBox
                HBox LocationInfo = new HBox(10, vbox);

                // Add HBox to the VBox
                vbox_Location.getChildren().add(LocationInfo);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }
    public void SortByInfo() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * from `location` ORDER BY `info` DESC");
            vbox_Location.getChildren().clear();

            ResultSet resultSet = statement.executeQuery();

            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                String name = resultSet.getString("Name");
                String info = resultSet.getString("info");
                String category = resultSet.getString("category");

                // Create UI elements

                Label nameLabel = new Label(" Name: " + name);
                Label InfoLabel = new Label("Info: " + info);
                Label categoryLabel = new Label("Category: " + category);



                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(nameLabel, InfoLabel,categoryLabel);

                // Create HBox to hold VBox
                HBox LocationInfo = new HBox(10, vbox);

                // Add HBox to the VBox
                vbox_Location.getChildren().add(LocationInfo);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }
    public void SortByCategory() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * from `location` ORDER BY `category` DESC");
            vbox_Location.getChildren().clear();

            ResultSet resultSet = statement.executeQuery();

            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                String name = resultSet.getString("Name");
                String info = resultSet.getString("info");
                String category = resultSet.getString("category");

                // Create UI elements

                Label nameLabel = new Label(" Name: " + name);
                Label InfoLabel = new Label("Info: " + info);
                Label categoryLabel = new Label("Category: " + category);



                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(nameLabel, InfoLabel,categoryLabel);

                // Create HBox to hold VBox
                HBox LocationInfo = new HBox(10, vbox);

                // Add HBox to the VBox
                vbox_Location.getChildren().add(LocationInfo);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }

    public void AutoSearchLocation(KeyEvent keyEvent) {
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

    public void ExportData(ActionEvent actionEvent) {
        exportToExcel();
    }
    @FXML
    public void HandleLocationSort(ActionEvent actionEvent) {
        String selectedOption = LocationSortBox.getValue();
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
    @FXML
    public void HandleTransportSort(ActionEvent actionEvent) {
        String selectedOption = TransportSortBox.getValue();
        if (selectedOption != null) {
            switch (selectedOption) {
                case "Brand":
                    SortByBrand();
                    break;
                case "Distance":
                    SortByDistance();
                    break;
                case "Charging Time":
                    SortByChargingTime();
                    break;
                default:
                    System.out.println("No option is selected!");
                    // Handle default case or do nothing
                    break;
            }
        }
    }

    public void AutoSearchTransport(KeyEvent keyEvent) {
        BrandSearchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                SearchByBrand();
            }
        });
    }

    public void SearchByBrand() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `transport` WHERE `Brand` LIKE ?");
            statement.setString(1,"%"+BrandSearchField.getText()+"%");
            vbox_Transport.getChildren().clear();
            ResultSet resultSet = statement.executeQuery();
            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                String brand = resultSet.getString("Brand");
                String type = resultSet.getString("Type");
                String distance = resultSet.getString("Distance");
                int chargeTime = resultSet.getInt("ChargingTime");
                // Create UI elements

                Label BrandLabel = new Label(" Brand: " + brand);
                Label TypeLabel = new Label("Type: " + type);
                Label RangeLabel = new Label("Distance: " + distance);
                Label TimeToChargeLabel = new Label("Charging Time: " + chargeTime);


                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(BrandLabel, TypeLabel,RangeLabel,TimeToChargeLabel);

                // Create HBox to hold VBox
                HBox TransportInfo = new HBox(10, vbox);

                // Add HBox to the VBox
                vbox_Transport.getChildren().add(TransportInfo);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }
    public void SortByBrand() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `transport` ORDER BY `Brand` DESC");
            ResultSet resultSet = statement.executeQuery();
            vbox_Transport.getChildren().clear();
            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                String brand = resultSet.getString("Brand");
                String type = resultSet.getString("Type");
                String distance = resultSet.getString("Distance");
                int chargeTime = resultSet.getInt("ChargingTime");
                // Create UI elements

                Label BrandLabel = new Label(" Brand: " + brand);
                Label TypeLabel = new Label("Type: " + type);
                Label RangeLabel = new Label("Distance: " + distance);
                Label TimeToChargeLabel = new Label("Charging Time: " + chargeTime);


                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(BrandLabel, TypeLabel,RangeLabel,TimeToChargeLabel);

                // Create HBox to hold VBox
                HBox TransportInfo = new HBox(10, vbox);

                // Add HBox to the VBox
                vbox_Transport.getChildren().add(TransportInfo);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }

    public void SortByDistance() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `transport` ORDER BY `Distance` DESC");
            ResultSet resultSet = statement.executeQuery();
            vbox_Transport.getChildren().clear();
            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                String brand = resultSet.getString("Brand");
                String type = resultSet.getString("Type");
                String distance = resultSet.getString("Distance");
                int chargeTime = resultSet.getInt("ChargingTime");
                // Create UI elements

                Label BrandLabel = new Label(" Brand: " + brand);
                Label TypeLabel = new Label("Type: " + type);
                Label RangeLabel = new Label("Distance: " + distance);
                Label TimeToChargeLabel = new Label("Charging Time: " + chargeTime);


                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(BrandLabel, TypeLabel,RangeLabel,TimeToChargeLabel);

                // Create HBox to hold VBox
                HBox TransportInfo = new HBox(10, vbox);

                // Add HBox to the VBox
                vbox_Transport.getChildren().add(TransportInfo);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }

    public void SortByChargingTime() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `transport` ORDER BY `ChargingTime` DESC");
            ResultSet resultSet = statement.executeQuery();
            vbox_Transport.getChildren().clear();
            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                String brand = resultSet.getString("Brand");
                String type = resultSet.getString("Type");
                String distance = resultSet.getString("Distance");
                int chargeTime = resultSet.getInt("ChargingTime");
                // Create UI elements

                Label BrandLabel = new Label(" Brand: " + brand);
                Label TypeLabel = new Label("Type: " + type);
                Label RangeLabel = new Label("Distance: " + distance);
                Label TimeToChargeLabel = new Label("Charging Time: " + chargeTime);


                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(BrandLabel, TypeLabel,RangeLabel,TimeToChargeLabel);

                // Create HBox to hold VBox
                HBox TransportInfo = new HBox(10, vbox);

                // Add HBox to the VBox
                vbox_Transport.getChildren().add(TransportInfo);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }
    //////////////////////////////////////////////////////////////////////////////////
    public void home(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
        Search_field.getScene().setRoot(root);
    }
    @FXML
    void achievements(ActionEvent event)  throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ConsultActivities.fxml"));
        Search_field.getScene().setRoot(root);
    }

    @FXML
    void forum(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Forumuser.fxml"));
        Search_field.getScene().setRoot(root);
    }

    @FXML
    void locations(ActionEvent event) throws IOException {
       /* Parent root = FXMLLoader.load(getClass().getResource("/LocationFront.fxml"));
        Search_field.getScene().setRoot(root);*/
    }


    @FXML
    void services(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/HostelFront.fxml"));
        Search_field.getScene().setRoot(root);

    }

    public void account(ActionEvent actionEvent) {
        // Retrieve the authenticated user from the Login class
        User user = Login.getAuthenticatedUser();

        if (user == null) {
            // Handle the case where the user is not authenticated
            showAlert("Error", "You are not authenticated. Please sign in.");
            return;
        }

        // Redirect based on user type
        if (user instanceof Admin) {
            // Handle the case where the user is logged in as Admin
            // You can redirect to a different page or display a message
            showAlert("Information", "You are logged in as an Admin.");
        } else if (user instanceof Tourist) {
            // Redirect to TouristAccount screen
            loadTouristAccountScreen(actionEvent, (Tourist) user);
        } else if (user instanceof LocalCom) {
            // Redirect to LocalComAccount screen
            loadLocalComAccountScreen(actionEvent, (LocalCom) user);
        } else {
            // Handle other cases or show a login page
            // You may want to implement a login functionality here
        }
    }
    ///
    private void loadTouristAccountScreen(ActionEvent event, Tourist tourist) {
        try {
            // Load the TouristAccount.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TouristAccount.fxml"));
            Parent root = loader.load();
            TouristAccount touristAccountController = loader.getController();
            touristAccountController.initialize();  // Pass the Tourist instance
            showStage(event, root, "Tourist Account");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLocalComAccountScreen(ActionEvent event, LocalCom localcom) {
        try {
            // Load the LocalComAccount.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LocalComAccount.fxml"));
            Parent root = loader.load();
            showStage(event, root, "LocalCom Account");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showStage(ActionEvent event, Parent root, String title) {
        // Your existing showStage method
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
