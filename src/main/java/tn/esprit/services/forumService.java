package tn.esprit.services;
import jdk.jfr.Category;
import tn.esprit.entities.forum;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



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
    @Override
    public List<forum> SortForum(String selectedCategory) throws SQLException {
        String query = "SELECT * FROM `forum` WHERE `Category`=?";
        try (
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, selectedCategory);
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
            return forums;}}

    @Override
    public List<String> getAllCategories() throws SQLException {
        String query;
        PreparedStatement ps;
        query = "SELECT DISTINCT Category FROM forum";
        ps = this.con.prepareStatement(query);
        List<String> categories = new ArrayList<>();
        ResultSet rs = ps.executeQuery();


        // PreparedStatement ps = con.prepareStatement("SELECT DISTINCT Category FROM forum")

        while (rs.next()) {
            categories.add(rs.getString("Category"));
        }


        return categories;
    }
    public boolean isCategoryExists(String category) throws SQLException {
        String query = "SELECT COUNT(*) FROM forum WHERE Category = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, category);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        }
    }
    public int getIdForum(String category) throws SQLException {
       int id=0;
        String query = "SELECT IDForum FROM forum WHERE Category = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, category);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
           id = resultSet.getInt(1);

            return id;
        }
    }

}
