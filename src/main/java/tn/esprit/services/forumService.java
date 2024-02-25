package tn.esprit.services;
import tn.esprit.entities.forum;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.controller.ForumDao;


public class forumService implements IService<forum>{
    Connection con ;
    Statement stm;

    public forumService(){con= MyDataBase.getInstance().getCon();}
    @Override
    public void add(forum f) throws SQLException {
        String query = "INSERT INTO `forum`(`ContentForum`, `NB_posts`, `Category`) VALUES (?, ?, ?)";


        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, f.getContentForum());
            ps.setInt(2, f.getNB_posts());
            ps.setString(3, f.getCategory());
           // ps.setInt(1, f.getIDForum());
            ps.executeUpdate();
            System.out.println("forum added!");
        }
    }

    public void update(forum f) throws SQLException{
        String query="UPDATE `forum` SET  ContentForum=?,NB_posts=?,Category=? WHERE IDForum=?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1,f.getContentForum());
            ps.setInt(2,f.getNB_posts());

            ps.setString(3,f.getCategory());
            // Assuming there is an 'IDPost' field in your 'post' table
            ps.setInt(4, f.getIDForum());


            ps.executeUpdate();
            System.out.println("updated!");
        }}
    public void delete(int IDForum) throws SQLException {
        String query = "DELETE FROM `forum` WHERE IDForum = ?";
        PreparedStatement ps = this.con.prepareStatement(query);
        ps.setInt(1, IDForum);
        ps.executeUpdate();
        System.out.println("forum deleted!");
    }
    public List<forum> displayList() throws SQLException {
        String query = "SELECT * FROM `forum`";
        PreparedStatement ps= this.con.prepareStatement(query);
        ResultSet res = ps.executeQuery();
        List<forum> forums = new ArrayList<>();

        while(res.next()) {
            forum f = new forum(

                    res.getInt("IDForum"),
                    res.getString("ContentForum"),
                    res.getInt("NB_posts"),
                    res.getString("Category"));

            forums.add(f);
        }

        return forums;
    }

    @Override
    public List<forum> SearchByContent(String ContentForum) throws SQLException {
        String query;
        PreparedStatement ps;

        if (ContentForum.isEmpty()) {
            // If search content is empty, retrieve all forums
            query = "SELECT * FROM `forum`";
            ps = this.con.prepareStatement(query);
        } else {
            // If search content is not empty, perform the search
            query = "SELECT * FROM `forum` WHERE `ContentForum` LIKE ?";
            ps = this.con.prepareStatement(query);
                        ps.setString(1, "%" + ContentForum + "%");

        }
        System.out.println("SQL Query: " + ps.toString());
        ResultSet res = ps.executeQuery();
        List<forum> forums = new ArrayList<>();

        while (res.next()) {
            forum f = new forum(
                    res.getInt("IDForum"),
                    res.getString("ContentForum"),
                    res.getInt("NB_posts"),
                    res.getString("Category"));

            forums.add(f);
        }

        return forums;
    }
    public forum getForumById(int forumId) throws SQLException {
        String query = "SELECT * FROM forum WHERE IDForum = ?";
        try (Connection con = MyDataBase.instance.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, forumId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new forum(
                            rs.getInt("IDForum"),
                            rs.getString("ContentForum"),
                            rs.getInt("NB_posts"),
                            rs.getString("Category")
                    );
                }
            }
        }
        return null; // Return null if no forum with the given ID is found
    }
    public List<String> getAllCategories() throws SQLException {
        List<String> categories = new ArrayList<>();

        // Assuming there is a method in your DAO or somewhere to retrieve unique categories
        // Replace 'yourForumDao' with the actual instance of your DAO class
        // Replace 'getUniqueCategories()' with the actual method to get unique categories
        categories = ForumDao.getUniqueCategories();


        return categories;
    }




}
