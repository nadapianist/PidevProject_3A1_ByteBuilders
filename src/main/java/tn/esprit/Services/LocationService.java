package tn.esprit.Services;

import tn.esprit.Entity.Location;
import tn.esprit.Entity.Rating;
import tn.esprit.Utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationService implements ILocation {
    Connection con;
    Statement stm;
    public LocationService(){
        con = MyDatabase.getInstance().getCon();
    }
    @Override
    public void add(Location l) throws SQLException {
        String query = "INSERT INTO `location`(`ID`,`Name`, `info`,`category`,`activity`,`hostel`) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1,l.getID());
        ps.setString(2,l.getName());
        ps.setString(3, l.getInfo());
        ps.setString(4, l.getCategory());
        ps.setInt(5,l.getActivityID());
        ps.setInt(6,l.getHostelID());


        ps.executeUpdate();
        System.out.println("New  Location!");
    }

    @Override
    public void update(int ID,String name,String info) throws SQLException {
        String query = "UPDATE `location` SET `Name` = ?, `info` = ?  WHERE `ID`= ?";
        PreparedStatement preparedStmt = con.prepareStatement(query);

// Set the parameters
        preparedStmt.setString(1, name);
        preparedStmt.setString(2,info);
        preparedStmt.setInt(3,ID);
// Execute the PreparedStatement
        int row_updated=preparedStmt.executeUpdate();
        if (row_updated > 0) {
            System.out.println("Location successfully updated");
        } else {
            System.out.println("Location not found");
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM `location` WHERE `ID` = ?";
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setInt(1, id);
        int rowsDeleted = preparedStmt.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("location  deleted");

        } else {
            System.out.println("location  not found");

        }
    }

    @Override
    public List<Location> displayList() throws SQLException {
        String query="SELECT * from location";
        stm=con.createStatement();
        ResultSet res=stm.executeQuery(query);
        List<Location> locations=new ArrayList<>();
        while(res.next()){
            Location l=new Location(
                    res.getString("Name")
                    ,res.getString("info")
                    ,res.getString("category")
                    ,res.getInt("activity")
                    ,res.getInt("hostel")

            );
            locations.add(l);
        }

        return locations;

    }

    @Override
    public List<Location> SearchLocations(String Name) throws SQLException {
        String query="SELECT * FROM `location` WHERE `Name`=?";
        PreparedStatement ps= this.con.prepareStatement(query);
        ps.setString(1,Name);
        ResultSet res = ps.executeQuery();
        List<Location> locations = new ArrayList<>();
        while(res.next()){
            Location l=new Location(
                    res.getString("Name")
                    ,res.getString("info")
                    ,res.getString("category")
                    ,res.getInt("activity")
                    ,res.getInt("hostel")

            );
            locations.add(l);
        }
        return locations;
    }

    @Override
    public List<Location> SortLocations() throws SQLException {
        String query="SELECT * from `location` ORDER BY `category` ASC";
        stm=con.createStatement();
        ResultSet res=stm.executeQuery(query);
        List<Location> locations=new ArrayList<>();
        while(res.next()){
            Location l=new Location(
                    res.getString("Name")
                    ,res.getString("info")
                    ,res.getString("category")
                    ,res.getInt("activity")
                    ,res.getInt("hostel")

            );
            locations.add(l);
        }

        return locations;
    }

    @Override
    public List<Rating> getRatingsForLocation(int idlocation) throws SQLException {
        List<Rating> ratings = new ArrayList<>();
        String query = "SELECT * FROM `rating` WHERE `IdLoc` = ?";

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
