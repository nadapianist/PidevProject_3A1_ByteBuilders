package tn.esprit.services;

import tn.esprit.entities.Activity;
import tn.esprit.entities.Challenge;
import tn.esprit.utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public  class ActivityService implements IService<Activity> ,IAdvanced<Activity> {

    Connection con;
    Statement stm;
    PreparedStatement pste;


    public ActivityService() {
        con = MyDb.getInstance().getCon();

    }
    @Override
    public void add(Activity activity) throws SQLException {
        String query = "INSERT INTO `activity`(`name`, `date_act`,` description`) VALUES ('" + activity.getName() + "','" + activity.getDate_act() + "','" + activity.getDescription() + "','" + "')";
        stm = con.createStatement();
        stm.executeUpdate(query);
        System.out.println("Activity added!");
    }

    @Override
    public void addd(Activity activity) throws SQLException {

        String query = "INSERT INTO `activity`(`name`, `date_act`,`description` ) VALUES (?,?,?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, activity.getName());
        ps.setDate(2, new java.sql.Date(activity.getDate_act().getTime()));
        ps.setString(3, activity.getDescription());


        ps.executeUpdate();
        System.out.println("Activity added!");


    }


    @Override
    public void update(Activity activity) throws SQLException {
        String query = "UPDATE `activity` SET `name`=?, `date_act`=?, `Description`=? WHERE `id_act`=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, activity.getName());
        ps.setDate(2, new java.sql.Date(activity.getDate_act().getTime()));
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
            Date date_act = res.getDate("date_act");
            String description = res.getString("description");

            Activity activity = new Activity(id, name, date_act, description);
            activities.add(activity);
        }
        return activities;

    }
    }


