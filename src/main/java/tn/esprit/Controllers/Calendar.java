package tn.esprit.Controllers;
/*
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class Calendar  {

    @FXML
    private GridPane grid;
    private GridPane calendarPane;

    public void displayCalendar() {
        // Create a new GridPane to hold the calendar
        calendarPane = new GridPane();

        // Populate the calendar with dates
        populateCalendar(LocalDate.now());

        // Add the populated calendar to the grid
        grid.getChildren().add(calendarPane);
    }

    private void populateCalendar(LocalDate currentDate) {
        // Create labels for each day of the week and add them to the calendarPane
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            dayLabel.setStyle("-fx-padding: 5px; -fx-border-color: black; -fx-border-width: 0 0 1 0;");
            grid.add(dayLabel, i, 0);
        }

        // Get the year and month of the current date
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        // Get the number of days in the current month
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        // Get the day of the week for the first day of the month
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        // Populate the calendar grid with dates
        int row = 1;
        int col = startDayOfWeek;
        for (int day = 1; day <= daysInMonth; day++) {
            Label dateLabel = new Label(String.valueOf(day));
            dateLabel.setStyle("-fx-padding: 5px; -fx-border-color: black; -fx-border-width: 1 0 0 0;");
            grid.add(dateLabel, col, row);
            col++;
            if (col == 7) {
                col = 0;
                row++;
            }
        }

        // Add activity dates to the calendar (you would replace this with your own data)
        addActivityDate(2024, 2, 14); // Example: Valentine's Day
        addActivityDate(2024, 2, 24); // Example: Today
    }

    private void addActivityDate(int year, int month, int day) {
        int row = day / 7 + 1;
        int col = (day % 7 == 0) ? 6 : (day % 7) - 1;
        Label activityLabel = new Label("Activity");
        activityLabel.setStyle("-fx-background-color: yellow; -fx-padding: 2px;");
        grid.add(activityLabel, col, row);
    }


}
*/
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import tn.esprit.entities.Activity;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Calendar implements Initializable {
    Connection con;
    Statement stm;
    PreparedStatement pste;
    ZonedDateTime dateFocus;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    // Database connection instance, adjust it according to your setup
    private Connection connection;

    @FXML
    void backOneMonth() {

    }

    @FXML
    void forwardOneMonth() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // Method to fetch activities from the database for the given year and month
    private List<Activity> fetchActivitiesForMonth(int year, int month) {
        List<Activity> activities = new ArrayList<>();


        return activities;
    }

    private void drawCalendar() {

        }
    }

