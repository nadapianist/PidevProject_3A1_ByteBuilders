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
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth() {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        calendar.setStyle("-fx-border-radius: 10;");
        calendar.setStyle("-fx-background-radius: 20;");

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "");
            pste = connection.prepareStatement("SELECT * FROM activity WHERE YEAR(date_act) = ? AND MONTH(date_act) = ?");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle connection failure gracefully in your application
        }

        drawCalendar();
    }

    // Method to fetch activities from the database for the given year and month
    private List<Activity> fetchActivitiesForMonth(int year, int month) {
        List<Activity> activities = new ArrayList<>();
        try {
            pste.setInt(1, year);
            pste.setInt(2, month);
            try (ResultSet resultSet = pste.executeQuery()) {
                while (resultSet.next()) {
                    Activity activity = new Activity(resultSet);
                    activities.add(activity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle potential exceptions more gracefully in your actual application
        }

        return activities;
    }

    private void drawCalendar() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        int monthMaxDate = dateFocus.getMonth().length(true);
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek().getValue();

        List<Activity> activities = fetchActivitiesForMonth(dateFocus.getYear(), dateFocus.getMonthValue());

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.LIGHTBLUE);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = -(rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        // Fetch activities for the current date
                        List<Activity> activitiesForDate = activities.stream()
                                .filter(activity -> activity.getDate_act().getDayOfMonth() == currentDate)
                                .collect(Collectors.toList());

                        // Create labels for each activity and add them to the cell
                        for (Activity activity : activitiesForDate) {
                            Text activityName = new Text(activity.getName());
                            stackPane.getChildren().add(activityName);
                            rectangle.setFill(Color.YELLOW); // Change cell color to yellow
                        }
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }
}
