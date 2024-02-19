package tn.esprit.Controllers;
import tn.esprit.entities.Activity;
import tn.esprit.utils.MyDb;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Stat implements Initializable{
    @FXML
    private BarChart<String , Integer> Chart;

    @FXML
    private CategoryAxis s_axis;

    @FXML
    private NumberAxis y_axis;
    private final ObservableList<String> data = FXCollections.observableArrayList();
    private final ObservableList<Activity> data1 = FXCollections.observableArrayList();
    private ObservableList<String> names = FXCollections.observableArrayList();

    private Statement ste;
    private Connection con;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int n=0;
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        try {
            con = MyDb.getInstance().getCon();
            ste = con.createStatement();
            data.clear();

            ResultSet rs = ste.executeQuery("SELECT * FROM activity");

            while(rs.next()) {
                try {
                    con = MyDb.getInstance().getCon();
                    ste = con.createStatement();
                    data.clear();

                    ResultSet rs1 = ste.executeQuery("SELECT Activity, Name FROM location"); // Fetch id_ activity and location names
                    while(rs1.next()) {
                        if(rs1.getInt("Activity") == rs.getInt("id_act")) { // Match activity and location IDs
                            n++;
                            series.getData().add(new XYChart.Data(rs1.getString("Name"), n)); // Add location name as X-axis value
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                n=0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Chart.getData().addAll(series);



    }
}
