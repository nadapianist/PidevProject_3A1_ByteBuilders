package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Activity;
import tn.esprit.entities.Challenge;
import tn.esprit.entities.ReviewChallenge;
import tn.esprit.utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReviewChallengeService implements IService<ReviewChallenge>,IAdvanced<ReviewChallenge>{

    Connection con;
    Statement stm;
    PreparedStatement pste;

    public ReviewChallengeService() {
        con = MyDb.getInstance().getCon();

    }
    @Override
    public void add(ReviewChallenge reviewChallenge) throws SQLException {
        String query = "INSERT INTO `reviewchallenge`(`Info`,`Date_pub`,`Title_rev` ) VALUES ('" + reviewChallenge.getInfo() + "','" + reviewChallenge.getDate_pub() + "','" + reviewChallenge.getTitle_rev()+ "','" + "')";
        stm = con.createStatement();
        stm.executeUpdate(query);
        System.out.println("Review Challenge added!");
    }

    @Override
    public void addd(ReviewChallenge reviewChallenge) throws SQLException {
        String query = "INSERT INTO `reviewchallenge`(`IDActivity`, `ID_challenge`, `Info`, `Date_pub`, `Title_rev`) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, reviewChallenge.getIDActivity());
        ps.setInt(2, reviewChallenge.getID_challenge());
        ps.setString(3, reviewChallenge.getInfo());
        ps.setDate(4, new java.sql.Date(reviewChallenge.getDate_pub().getTime()));
        ps.setString(5, reviewChallenge.getTitle_rev());


        ps.executeUpdate();
        System.out.println("ReviewChallenge added!");
    }

    @Override
    public void update(ReviewChallenge reviewChallenge) throws SQLException {
        String query = "UPDATE `reviewchallenge` SET `Info`=?, `Date_pub`=?, `Title_rev`=? WHERE `IDActivity`=? AND `ID_challenge`=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, reviewChallenge.getInfo());
        ps.setDate(2, new java.sql.Date(reviewChallenge.getDate_pub().getTime()));
        ps.setString(3, reviewChallenge.getTitle_rev());
        ps.setInt(4, reviewChallenge.getIDActivity());
        ps.setInt(5, reviewChallenge.getID_challenge());

        ps.executeUpdate();
        System.out.println("ReviewChallenge added!");

    }

    @Override
    public void delete(ReviewChallenge reviewChallenge) throws SQLException {
        String query = "DELETE FROM `reviewchallenge` WHERE `IDActivity`=? AND `ID_challenge`=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, reviewChallenge.getIDActivity());
        ps.setInt(2, reviewChallenge.getID_challenge());
        ps.executeUpdate();
        System.out.println("ReviewChallenge deleted!");

    }

    @Override
    public List<ReviewChallenge> diplayList() throws SQLException {

        String query = "SELECT rc.*, c.name_ch AS challengeName, a.name AS activityName " +
                "FROM reviewchallenge rc " +
                "INNER JOIN challenge c ON rc.ID_challenge = c.id_chall " +
                "INNER JOIN activity a ON rc.IDActivity = a.id_act";


        stm = con.createStatement();
        ResultSet res = stm.executeQuery(query);
        List<ReviewChallenge> reviews = new ArrayList<>();
        while (res.next()) {
            int idact = res.getInt("IDActivity");
            int idchall = res.getInt("ID_challenge");
            String Info = res.getString("Info");
            java.util.Date Date_pub = res.getDate("Date_pub");
            String Title_rev = res.getString("Title_rev");
            String activityName = res.getString("activityName");
            String challengeName = res.getString("challengeName");

            ReviewChallenge reviewChallenge = new ReviewChallenge(idact, idchall, Info, Date_pub, Title_rev, activityName, challengeName);

            reviews.add(reviewChallenge);
        }
        return reviews;

    }

    @Override
    public List<ReviewChallenge> SearchByDate(java.util.Date d) throws SQLException {
        return null;
    }

    public List<ReviewChallenge> Search(String str) throws SQLException {
        List<ReviewChallenge> reviews = new ArrayList<>();
        String req = "SELECT * FROM reviewchallenge WHERE Title_rev = ?";
        try {
            pste = con.prepareStatement(req);
            pste.setString(1, str);
            ResultSet res = pste.executeQuery();

            while (res.next()) {
                ReviewChallenge r = new ReviewChallenge();
                r.setIDActivity(res.getInt(1));
                r.setID_challenge(res.getInt(2));
                r.setInfo(res.getString(3));
                r.setDate_pub(res.getDate(4));
                r.setTitle_rev(res.getString(5));

                reviews.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return reviews;
    }

    @Override
    public ObservableList<ReviewChallenge> Sort() {

        ObservableList<ReviewChallenge> reviews= FXCollections.observableArrayList();
        String req = "SELECT rc.*, c.name_ch AS challengeName, a.name AS activityName " +
                "FROM reviewchallenge rc " +
                "INNER JOIN challenge c ON rc.ID_challenge = c.id_chall " +
                "INNER JOIN activity a ON rc.IDActivity = a.id_act " +
                "ORDER BY rc.Date_pub";
        try {
            pste = con.prepareStatement(req);
            ResultSet res = pste.executeQuery();

            while (res.next()) {
                ReviewChallenge r = new ReviewChallenge();
                r.setIDActivity(res.getInt("IDActivity"));
                r.setID_challenge(res.getInt("ID_challenge"));
                r.setInfo(res.getString("Info"));
                r.setDate_pub(res.getDate("Date_pub"));
                r.setTitle_rev(res.getString("Title_rev"));
                r.setNameActivity(res.getString("activityName")); // Set activity name
                r.setNameChallenge(res.getString("challengeName")); // Set challenge name

                reviews.add(r);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return reviews;

    }

    public ObservableList<ReviewChallenge> SortTitle() {

        ObservableList<ReviewChallenge> reviews= FXCollections.observableArrayList();
        String req = "SELECT rc.*, c.name_ch AS challengeName, a.name AS activityName " +
                "FROM reviewchallenge rc " +
                "INNER JOIN challenge c ON rc.ID_challenge = c.id_chall " +
                "INNER JOIN activity a ON rc.IDActivity = a.id_act " +
                "ORDER BY rc.Title_rev";
        try {
            pste = con.prepareStatement(req);
            ResultSet res = pste.executeQuery();

            while (res.next()) {
                ReviewChallenge r = new ReviewChallenge();
                r.setIDActivity(res.getInt("IDActivity"));
                r.setID_challenge(res.getInt("ID_challenge"));
                r.setInfo(res.getString("Info"));
                r.setDate_pub(res.getDate("Date_pub"));
                r.setTitle_rev(res.getString("Title_rev"));
                r.setNameActivity(res.getString("activityName")); // Set activity name
                r.setNameChallenge(res.getString("challengeName")); // Set challenge name

                reviews.add(r);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return reviews;

    }

    public ObservableList<ReviewChallenge> SortInfo() {

        ObservableList<ReviewChallenge> reviews= FXCollections.observableArrayList();
        String req = "SELECT rc.*, c.name_ch AS challengeName, a.name AS activityName " +
                "FROM reviewchallenge rc " +
                "INNER JOIN challenge c ON rc.ID_challenge = c.id_chall " +
                "INNER JOIN activity a ON rc.IDActivity = a.id_act " +
                "ORDER BY rc.Info";
        try {
            pste = con.prepareStatement(req);
            ResultSet res = pste.executeQuery();

            while (res.next()) {
                ReviewChallenge r = new ReviewChallenge();
                r.setIDActivity(res.getInt("IDActivity"));
                r.setID_challenge(res.getInt("ID_challenge"));
                r.setInfo(res.getString("Info"));
                r.setDate_pub(res.getDate("Date_pub"));
                r.setTitle_rev(res.getString("Title_rev"));
                r.setNameActivity(res.getString("activityName")); // Set activity name
                r.setNameChallenge(res.getString("challengeName")); // Set challenge name

                reviews.add(r);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return reviews;

    }
/*
    public String bad_words(String badWord) {

        List<String> badListW = Arrays.asList("zebi", "sorem","nayek","asba lik","zabour","9ahba","khahba");
        String badNew = "";
        List<String> newList = new ArrayList<>();
        for (String str : badListW) {
            if (badWord.contains(str)) {
                badNew += "" + str;
                if (str.length() >= 1) {
                    StringBuilder result = new StringBuilder();
                    result.append(str.charAt(0));
                    for (int i = 0; i < str.length() - 2; ++i) {
                        result.append("*");
                    }
                    result.append(str.charAt(str.length() - 1));
                    str = result.toString();
                    if (!str.isEmpty()) {
                        System.out.println("ATTENTION !! Vous avez écrit un gros mot  : " + result + " .C'est un avertissement ! Priére d'avoir un peu de respect ! Votre description sera envoyée comme suit :");
                        System.out.println(badWord.replace(badNew, "") + " ");
                    }
                }
            }
        }
        return (badWord.replace(badNew, "") + " ");
    }
    */

    public List<String> affichagecomboActivity()  {
        List<String> arr=new ArrayList<>();
        try {
            PreparedStatement pt = con.prepareStatement("SELECT a.name AS activityName " +
                    "FROM reviewchallenge rc " +
                    "INNER JOIN challenge c ON rc.ID_challenge = c.id_chall " +
                    "INNER JOIN activity a ON rc.IDActivity = a.id_act");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                String nom=rs.getString("activityName");

                arr.add(nom);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReviewChallengeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }


}
