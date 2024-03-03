package tn.esprit.services;

import tn.esprit.entities.Rating;
import tn.esprit.utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingService {
    Connection con;
    //Statement stm;
    public RatingService(){con = MyDb.getInstance().getCon();}
    public List<Rating> displayList(int idlocation) throws SQLException {

        List<Rating> ratings=new ArrayList<>();
        String query = "SELECT * FROM rating WHERE IdLoc = ?";

        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, idlocation);
        ResultSet res = preparedStatement.executeQuery();
        while (res.next()) {
            Rating rating = new Rating(

                    res.getInt("userId")
                    ,res.getInt("IdLoc")
                    , res.getDate("ratingDate")
                    ,res.getInt("nbStars")

            );
            ratings.add(rating);
        }
        return ratings;
    }
}