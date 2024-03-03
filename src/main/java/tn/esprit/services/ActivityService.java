package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Activity;
import tn.esprit.entities.Challenge;
import tn.esprit.utils.MyDb;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.time.LocalDate;

public  class ActivityService implements IService<Activity> ,IAdvanced<Activity> {

    Connection con;
    Statement stm;
    PreparedStatement pste;


    public ActivityService() {
        con = MyDb.getInstance().getCon();

    }



    @Override
    public void add(Activity activity) throws SQLException {

        LocalDate localDate = activity.getDate_act();
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

            String query = "INSERT INTO `activity`(`name`, `date_act`, `description`, `image`) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, activity.getName());
                ps.setDate(2, sqlDate);
                ps.setString(3, activity.getDescription());
                ps.setString(4, activity.getImage());


                ps.executeUpdate();
                System.out.println("Activity added!");
            }

    }

    @Override
    public void addd(Activity activity) throws SQLException {

        LocalDate localDate = activity.getDate_act();
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        String query = "INSERT INTO `activity`(`name`, `date_act`, `description`, `image`) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, activity.getName());
        ps.setDate(2,sqlDate);
        ps.setString(3, activity.getDescription());
        ps.setString(4, activity.getImage()); // Set image path

        ps.executeUpdate();
        System.out.println("Activity added!");
    }


    @Override
    public void update(Activity activity) throws SQLException {
        LocalDate localDate = activity.getDate_act();
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        String query = "UPDATE `activity` SET `name`=?, `date_act`=?, `Description`=?WHERE `id_act`=? ";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, activity.getName());
        ps.setDate(2, sqlDate);
        ps.setString(3, activity.getDescription());
        ps.setInt(4, activity.getId_act());

        ps.executeUpdate();
        System.out.println("Activity updated!");
    }
    @Override
    public void delete(Activity activity) throws SQLException {
        String query = "DELETE FROM `activity` WHERE `id_act`=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, activity.getId_act());

        ps.executeUpdate();
        System.out.println("Activity removed!");

    }


    @Override
    public List<Activity> diplayList() throws SQLException {
        String query = "SELECT * FROM `activity`";
        stm = con.createStatement();
        ResultSet res = stm.executeQuery(query);
        List<Activity> activities = new ArrayList<>();
        while (res.next()) {
            int id = res.getInt("id_act");
            String name = res.getString("name");
            LocalDate date_act = res.getDate("date_act").toLocalDate();
            String description = res.getString("description");

            Activity activity = new Activity(id, name, date_act, description);
            activities.add(activity);
        }
        return activities;

    }



    //search by name of the activity

    public List<Activity> Search(String str) throws SQLException {
        List<Activity> activities = new ArrayList<>();
        String req = "SELECT * FROM activity WHERE name = ?";
        try {
            pste = con.prepareStatement(req);
            pste.setString(1, str);
            ResultSet res = pste.executeQuery();

            while (res.next()) {
                Activity a = new Activity();
                a.setId_act(res.getInt(1));
                a.setName(res.getString(2));
                a.setDate_act(res.getDate(3).toLocalDate());
                a.setDescription(res.getString(4));
                a.setImage(res.getString(5));


                activities.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return activities;
    }


    //search the activity by date

    public List<Activity> SearchByDate(Date d) throws SQLException {

        List<Activity> activities = new ArrayList<>();
        String req = "SELECT * FROM activity WHERE date_act = ?";
        try {
            pste = con.prepareStatement(req);
            pste.setDate(1, (java.sql.Date) d);
            ResultSet res = pste.executeQuery();
            System.out.println("Activity Search Results:");
            while (res.next()) {
                Activity a = new Activity();
                a.setId_act(res.getInt(1));
                a.setName(res.getString(2));
                a.setDate_act(res.getDate(3).toLocalDate());
                a.setDescription(res.getString(4));
                a.setImage(res.getString(5));

                activities.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return activities;
    }
   /* private float getNBpointsFor1Activity(Activity act) {

    }*/

    @Override
    public ObservableList<Activity> Sort() {

        ObservableList<Activity> Act = FXCollections.observableArrayList();
        String req = "SELECT * FROM `activity` ORDER BY date_act";
        try {
            pste = con.prepareStatement(req);
            ResultSet res = pste.executeQuery();

            while (res.next()) {
                Activity a = new Activity();
                a.setId_act(res.getInt(1));
                a.setName(res.getString(2));
                a.setDate_act(res.getDate(3).toLocalDate());
                a.setDescription(res.getString(4));
                a.setImage(res.getString(5));


                Act.add(a);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Act;

    }


    public ObservableList<Activity> sortByName() {
        ObservableList<Activity> Act = FXCollections.observableArrayList();
        String req = "SELECT * FROM `activity` ORDER BY name";
        try {
            pste = con.prepareStatement(req);
            ResultSet res = pste.executeQuery();

            while (res.next()) {
                Activity a = new Activity();
                a.setId_act(res.getInt(1));
                a.setName(res.getString(2));
                a.setDate_act(res.getDate(3).toLocalDate());
                a.setDescription(res.getString(4));
                a.setImage(res.getString(5));


                Act.add(a);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Act;


    }

    public ObservableList<Activity> sortByDescription() {
        ObservableList<Activity> Act = FXCollections.observableArrayList();
        String req = "SELECT * FROM `activity` ORDER BY Description";
        try {
            pste = con.prepareStatement(req);
            ResultSet res = pste.executeQuery();

            while (res.next()) {
                Activity a = new Activity();
                a.setId_act(res.getInt(1));
                a.setName(res.getString(2));
                a.setDate_act(res.getDate(3).toLocalDate());
                a.setDescription(res.getString(4));
                a.setImage(res.getString(5));


                Act.add(a);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Act;


    }
    public List<String> getAllActivityNames() {
        List<String> activityNames = new ArrayList<>();
        try {
            PreparedStatement pt = con.prepareStatement("SELECT name FROM activity");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                String activityName = rs.getString("name");
                activityNames.add(activityName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return activityNames;
    }
    public int getActivityIDByName(String activityName) throws SQLException {
        int activityID = 0; // Default value if activity is not found
        try {
            PreparedStatement pt = con.prepareStatement("SELECT id_act FROM activity WHERE name = ?");
            pt.setString(1, activityName);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                activityID = rs.getInt("id_act");
            }
        } catch (SQLException ex) {
            // Handle SQLException
            throw ex;
        }
        return activityID;
    }
}




