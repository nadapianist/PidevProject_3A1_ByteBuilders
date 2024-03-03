package tn.esprit.Controllers;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import tn.esprit.entities.Hostel;
import tn.esprit.services.HostelService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HostelFront {

    private Hostel selectedHostel;

    @FXML
    private Label hostelNameLabel;
    @FXML
    private Label nbstarLabel;
    @FXML
    private Button dashboard;


    @FXML
    private Label infoLabel;
    @FXML
    private VBox hostelBox;
    @FXML
    private VBox hostelBox1;

    @FXML
    private TextField Search_field;
    @FXML
    private ComboBox<String> SortBox;
    private final HostelService hs = new HostelService();
    private Stage primaryStage;
    @FXML
    void Dashboard(ActionEvent event) throws IOException {
        /*Parent root = FXMLLoader.load(getClass().getResource("/HostelManagement.fxml"));
        dashboard.getScene().setRoot(root);
*/
    }
    public void initialize() {
        SortBox.getItems().addAll("Name","NBStars","Info");
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM hostel");
            ResultSet resultSet = statement.executeQuery();
            hostelListView = new ListView<>();

            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                int id = resultSet.getInt("IDHostel");
                String name = resultSet.getString("Name_hostel");
                int nbstars = resultSet.getInt("NBstars");
                String info = resultSet.getString("Info");
                // Create UI elements
                Label hostelnameLabel = new Label("Hostel Name: " + name);
                Label NbstarsLabel = new Label("Nb° stars: " + nbstars);
                Label InfoLabel = new Label("Info: " + info);
                Button bookButton = new Button("Book Now!");




                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(hostelnameLabel, NbstarsLabel, InfoLabel, bookButton);

                // Create HBox to hold VBox
                HBox hostelInfo = new HBox(10, vbox);

                // Add HBox to the VBox
                hostelBox.getChildren().add(hostelInfo);

                bookButton.setOnAction(event -> {
                    selectedHostel = new Hostel(id,name, nbstars, info);
                    System.out.println(selectedHostel);
                    if (selectedHostel != null) {
                        loadSecondPage(selectedHostel);
                    } else {
                        // Handle the case when selectedHostel is null
                        System.out.println("Selected hostel is null.");
                    }
                });
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
    @FXML private ListView<Hostel> hostelListView;
    public void initData(Hostel selectedHostel) {
        // Populate UI elements with selected hostel information
        hostelNameLabel.setText("Hostel Name: " + selectedHostel.getName_hostel());
        nbstarLabel.setText("Nb° stars: " + selectedHostel.getNBstars());
        infoLabel.setText("Info: " + selectedHostel.getInfo());
    }

    //hethi il kdima (info tji fil page loula)
    private void loadSecondPage(Hostel selectedHostel) {
        try {
            if (selectedHostel != null) {
                // Load the FXML file for the second page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/book.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                // Get the controller for book.fxml
                book bookController = loader.getController();

                // Set the hostel information in the controller
                bookController.setHostel(selectedHostel);

                // Set the primary stage scene
                Stage stage = (Stage) dashboard.getScene().getWindow(); // Get the current stage
                stage.setScene(scene);
                stage.show();
            } else {
                showAlert("Error", "Hostel Not Selected", "Please select a hostel before booking.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Load Error", e.getMessage());
        }
    }

  //  private WebEngine webengine;

    @FXML
    void Map(ActionEvent event) throws IOException {


        WebView webView = new WebView();
        String absolutePath = "file://" + System.getProperty("user.dir") + "/src/main/java/tn/esprit/gui//index.html";
        webView.getEngine().load(absolutePath);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(webView));

    }

    /*private void selectHostelAndLoadSecondPage() {
        bookButton.setOnAction(event -> {
            if (selectedHostel != null) {
                loadSecondPage(selectedHostel);
            } else {
                // Handle the case when selectedHostel is null
                System.out.println("Selected hostel is null.");
            }
        });
    }*/



    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Method to set the primary stage reference
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void SearchByName() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM hostel WHERE `Name_hostel` LIKE ?");
            statement.setString(1,"%"+Search_field.getText()+"%");
            System.out.println("You're looking for a hostel with name:"+Search_field.getText());
            hostelBox.getChildren().clear();

            ResultSet resultSet = statement.executeQuery();

            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                String name = resultSet.getString("Name_hostel");
                int nbstars = resultSet.getInt("NBstars");
                String info = resultSet.getString("Info");

                // Create UI elements

                // Create UI elements
                Label hostelnameLabel = new Label("Hostel Name: " + name);
                Label NbstarsLabel = new Label("Nb° stars: " + nbstars);
                Label InfoLabel = new Label("Info: " + info);
                Button bookButton = new Button("Book Now!");
                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(hostelnameLabel, NbstarsLabel, InfoLabel,bookButton);

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
            PreparedStatement statement = connection.prepareStatement("SELECT * from hostel ORDER BY Name_hostel DESC");
            hostelBox.getChildren().clear();

            ResultSet resultSet = statement.executeQuery();

            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                String name = resultSet.getString("Name_hostel");
                int nbstars = resultSet.getInt("NBstars");
                String info = resultSet.getString("Info");

                // Create UI elements

                // Create UI elements
                Label hostelnameLabel = new Label("Hostel Name: " + name);
                Label NbstarsLabel = new Label("Nb° stars: " + nbstars);
                Label InfoLabel = new Label("Info: " + info);
                Button bookButton = new Button("Book Now!");

                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(hostelnameLabel, NbstarsLabel, InfoLabel,bookButton);

                // Create HBox to hold VBox
                HBox LocationInfo = new HBox(10, vbox);

                // Add HBox to the VBox
                hostelBox.getChildren().add(LocationInfo);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
    }
    public void SortByNBStars() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "")) {
            // Prepare statement to retrieve data
            PreparedStatement statement = connection.prepareStatement("SELECT * from hostel ORDER BY NBstars DESC");
            hostelBox.getChildren().clear();

            ResultSet resultSet = statement.executeQuery();

            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                String name = resultSet.getString("Name_hostel");
                int nbstars = resultSet.getInt("NBstars");
                String info = resultSet.getString("Info");

                // Create UI elements

                // Create UI elements
                Label hostelnameLabel = new Label("Hostel Name: " + name);
                Label NbstarsLabel = new Label("Nb° stars: " + nbstars);
                Label InfoLabel = new Label("Info: " + info);
                Button bookButton = new Button("Book Now!");

                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(hostelnameLabel, NbstarsLabel, InfoLabel,bookButton);

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
            PreparedStatement statement = connection.prepareStatement("SELECT * from hostel ORDER BY Info DESC");
            hostelBox.getChildren().clear();

            ResultSet resultSet = statement.executeQuery();

            // Process each row of the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                String name = resultSet.getString("Name_hostel");
                int nbstars = resultSet.getInt("NBstars");
                String info = resultSet.getString("Info");

                // Create UI elements

                // Create UI elements
                Label hostelnameLabel = new Label("Hostel Name: " + name);
                Label NbstarsLabel = new Label("Nb° stars: " + nbstars);
                Label InfoLabel = new Label("Info: " + info);
                Button bookButton = new Button("Book Now!");

                // Create VBox to hold UI elements
                VBox vbox = new VBox();
                vbox.getChildren().addAll(hostelnameLabel, NbstarsLabel, InfoLabel,bookButton);

                // Create HBox to hold VBox
                HBox LocationInfo = new HBox(10, vbox);

                // Add HBox to the VBox
                hostelBox.getChildren().add(LocationInfo);
            }
        } catch ( SQLException e) {
            e.printStackTrace();
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
                case "NBStars":
                    SortByNBStars();
                    break;
                case "Info":
                    SortByInfo();
                    break;
                default:
                    System.out.println("No option is selected!");
                    // Handle default case or do nothing
                    break;
            }
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
    @FXML
    private Button goback;
    @FXML
    void Goback(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/HostelFront.fxml"));
        goback.getScene().setRoot(root);

    }
    @FXML
    void achievements(ActionEvent event)  throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ConsultActivities.fxml"));
        hostelBox.getScene().setRoot(root);
    }

    @FXML
    void forum(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Forumuser.fxml"));
        hostelBox.getScene().setRoot(root);
    }

    @FXML
    void home(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Home.fxml"));
        hostelBox.getScene().setRoot(root);
    }

    @FXML
    void locations(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LocationFront.fxml"));
        hostelBox.getScene().setRoot(root);
    }


    @FXML
    void services(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/HostelFront.fxml"));
        hostelBox.getScene().setRoot(root);

    }


}
