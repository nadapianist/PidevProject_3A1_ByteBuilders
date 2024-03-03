package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Activity;
import tn.esprit.entities.Challenge;
import tn.esprit.utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChallengeService implements IService<Challenge>,IAdvanced<Challenge> {

    Connection con;
    Statement stm;
    PreparedStatement pste;
    // Assuming you have a list of challenges
    private ObservableList<Challenge> challenges;
    public ChallengeService(){
        con = MyDb.getInstance().getCon();
        challenges = FXCollections.observableArrayList();
    }

    @Override
    public void add(Challenge challenge) throws SQLException {

    }
    @Override
    public void addd(Challenge challenge) throws SQLException {
        String query = "INSERT INTO `challenge`(`name_ch`,`desc_ch`,`points` ) VALUES (?,?,?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1,challenge.getName_ch());
        ps.setString(2,challenge.getDesc_ch());
        ps.setInt(3,challenge.getPoints());


        ps.executeUpdate();
        System.out.println("Challenge added!");
    }

    @Override
    public void update(Challenge challenge) throws SQLException {
        String query = "UPDATE `challenge` SET `name_ch`=?, `desc_ch`=?, `points`=?  WHERE `id_chall`=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, challenge.getName_ch());
        ps.setString(2, challenge.getDesc_ch());
        ps.setInt(3,challenge.getPoints());
        ps.setInt(4,challenge.getId_chall());

        ps.executeUpdate();
        System.out.println("Challenge updated!");
    }


    @Override
    public List<Challenge> diplayList() throws SQLException {
        String query = "SELECT * FROM `challenge`";
        stm = con.createStatement();
        ResultSet res = stm.executeQuery(query);
        List<Challenge> challenges = new ArrayList<>();
        while (res.next()) {
            int id = res.getInt("id_chall");
            String name = res.getString("name_ch");
            String desc_ch = res.getString("desc_ch");
            int points= res.getInt("points");
            Challenge challenge = new Challenge(id, name, desc_ch, points);
            challenges.add(challenge);
        }
        return challenges;
    }

    @Override
    public void delete(Challenge challenge) throws SQLException {
        String query = "DELETE FROM `challenge` WHERE `id_chall`=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, challenge.getId_chall());

        ps.executeUpdate();
        System.out.println("Challenge removed!");
    }

    @Override
    public List<Challenge> SearchByDate(Date d) throws SQLException {
        return null;
    }

    public List<Challenge> Search(String str) throws SQLException {

        List<Challenge> challenges = new ArrayList<>();
        String req = "SELECT * FROM challenge WHERE name_ch = ?";
        try {
            pste = con.prepareStatement(req);
            pste.setString(1, str);
            ResultSet res = pste.executeQuery();
            System.out.println("Challenge search Results:");
            while (res.next()) {
                Challenge c = new Challenge();
                c.setId_chall(res.getInt(1));
                c.setName_ch(res.getString(2));
                c.setDesc_ch(res.getString(3));
                c.setPoints(res.getInt(4));

                challenges.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChallengeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return challenges;
    }


    @Override
    public ObservableList<Challenge> Sort() {

        ObservableList<Challenge> challenges = FXCollections.observableArrayList();
        String req = "SELECT * FROM `challenge` ORDER BY points";
        try {
            pste = con.prepareStatement(req);
            ResultSet res = pste.executeQuery();

            while (res.next()) {
                Challenge c = new Challenge();
                c.setId_chall(res.getInt(1));
                c.setName_ch(res.getString(2));
                c.setDesc_ch(res.getString(3));
                c.setPoints(res.getInt(4));


                challenges.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return challenges;

    }


    public ObservableList<Challenge> sortByDescription() {
        ObservableList<Challenge> challenges = FXCollections.observableArrayList();
        String req = "SELECT * FROM `challenge` ORDER BY desc_ch";
        try {
            pste = con.prepareStatement(req);
            ResultSet res = pste.executeQuery();

            while (res.next()) {
                Challenge c = new Challenge();
                c.setId_chall(res.getInt(1));
                c.setName_ch(res.getString(2));
                c.setDesc_ch(res.getString(3));
                c.setPoints(res.getInt(4));

                challenges.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return challenges;
    }
    // Method to sort challenges by points
    public ObservableList<Challenge> sortByPoints() {
        ObservableList<Challenge> challenges = FXCollections.observableArrayList();
        String req = "SELECT * FROM `challenge` ORDER BY points";
        try {
            pste = con.prepareStatement(req);
            ResultSet res = pste.executeQuery();

            while (res.next()) {
                Challenge c = new Challenge();
                c.setId_chall(res.getInt(1));
                c.setName_ch(res.getString(2));
                c.setDesc_ch(res.getString(3));
                c.setPoints(res.getInt(4));

                challenges.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return challenges;
    }
    public ObservableList<Challenge> Sorte(String criteria) throws SQLException {
        ObservableList<Challenge> sortedChallenges = FXCollections.observableArrayList();

        String query = "";
        switch (criteria) {
            case "Name":
                query = "SELECT * FROM challenge ORDER BY name_ch";
                break;
            case "Description":
                query = "SELECT * FROM challenge ORDER BY desc_ch";
                break;
            case "Points":
                query = "SELECT * FROM challenge ORDER BY points";
                break;
            default:
                throw new IllegalArgumentException("Invalid sorting criteria.");
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "");
             PreparedStatement statement = con.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id_chall");
                String name = resultSet.getString("name_ch");
                String description = resultSet.getString("desc_ch");
                int points = resultSet.getInt("points");

                Challenge challenge = new Challenge(id, name, description, points);
                sortedChallenges.add(challenge);
            }
        }

        return sortedChallenges;
    }

    public List<String> getAllChallengeNames() {
        List<String> activityNames = new ArrayList<>();
        try {
            PreparedStatement pt = con.prepareStatement("SELECT name_ch FROM challenge");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                String activityName = rs.getString("name_ch");
                activityNames.add(activityName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return activityNames;
    }
    public int getChallengeIDByName(String challengeName) throws SQLException {
        int challengeID = 0; // Default value if challenge is not found
        try {
            PreparedStatement pt = con.prepareStatement("SELECT id_chall FROM challenge WHERE name_ch = ?");
            pt.setString(1, challengeName);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                challengeID = rs.getInt("id_chall");
            }
        } catch (SQLException ex) {
            // Handle SQLException
            throw ex;
        }
        return challengeID;
    }
}
