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

public class ReviewChallengeService implements IService<ReviewChallenge>{

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
        System.out.println("ReviewChallenge added!");

    }

    @Override
    public List<ReviewChallenge> diplayList() throws SQLException {
       /* String query = "SELECT * FROM `reviewchallenge`";
        stm = con.createStatement();
        ResultSet res = stm.executeQuery(query);
        List<ReviewChallenge> reviews = new ArrayList<>();
        while (res.next()) {
            int idact = res.getInt("IDActivity");
            int idchall = res.getInt("ID_challenge");
            String Info = res.getString("Info");
            java.util.Date Date_pub = res.getDate("Date_pub");
            String Title_rev = res.getString("Title_rev");

            ReviewChallenge reviewChallenge=new ReviewChallenge( idact,idchall,Info,Date_pub,Title_rev);

            reviews .add(reviewChallenge);*/
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


}
