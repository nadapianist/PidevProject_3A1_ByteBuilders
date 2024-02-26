package tn.esprit.services;
import javafx.collections.ObservableList;
import tn.esprit.entities.forum;
import tn.esprit.entities.post;
import tn.esprit.utils.MyDataBase;
import tn.esprit.services.forumService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class postService implements IService<post>{
    Connection con ;
    Statement stm;

public postService(){
    con= MyDataBase.getInstance().getCon();
    try {
        if (con.isClosed()) {
            con = MyDataBase.getInstance().reconnect();
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Handle the exception appropriately
    }
}


    @Override
    public void add(post p) throws SQLException {
        String query = "INSERT INTO `post`(`ContentPost`, `PhotoPost`, `DatePost`, `UserID`, `categoryPost`) VALUES (?, ?, ?, ?, ?)";
    try(PreparedStatement ps = con.prepareStatement(query)){
    ps.setString(1,p.getContentPost());
    ps.setString(2,p.getPhotoPost());

    ps.setDate(3,new java.sql.Date(p.getDatePost().getTime()));
    ps.setInt(4,p.getUserID());
    ps.setString(5,p.getCategoryPost());

    ps.executeUpdate();
    System.out.println("post added!");
    }}
    public void update(post p) throws SQLException{
      String query="UPDATE `post` SET ContentPost=?,PhotoPost=?,DatePost=?,categoryPost=?,UserID=? WHERE IDPost=?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1,p.getContentPost());
            ps.setString(2,p.getPhotoPost());
            ps.setDate(3,new java.sql.Date(p.getDatePost().getTime()));
            ps.setInt(4,p.getUserID());
            ps.setString(5,p.getCategoryPost());
            // Assuming there is an 'IDPost' field in your 'post' table
            ps.setInt(6, p.getIDPost());


            ps.executeUpdate();
            System.out.println("updated!");
        }}
    public void delete(int IDPost) throws SQLException {
        String query = "DELETE FROM `post` WHERE IDPost = ?";
        PreparedStatement ps = this.con.prepareStatement(query);
        ps.setInt(1, IDPost);
        ps.executeUpdate();
        System.out.println("post deleted!");
    }
    private post ResultPosts(ResultSet res)throws SQLException{
    return new post(
            res.getInt("IDPost"),
            res.getString("ContentPost"),
            res.getString("PhotoPost"),
            res.getDate("DatePost") ,
            res.getInt("UserID"),
            res.getString("categoryPost"));



    }
    public List<post> displayList() throws SQLException {
        String query = "SELECT  * FROM `post`";
        PreparedStatement ps = this.con.prepareStatement(query);
        ResultSet res = ps.executeQuery();
        List<post> posts = new ArrayList<>();

        while (res.next()) {
            post p=new post(
                    res.getInt("IDPost"),
                    res.getString("ContentPost"),
                    res.getString("PhotoPost"),
                    res.getDate("DatePost") ,
                    res.getInt("UserID"),
                    res.getString("categoryPost"));
            forumService fs = new forumService();
            //forum forumData = fs.getForumById(res.getInt("IDForum"));
            //p.setForumCategory(forumData.getCategory());
           // post p=ResultPosts(res);
           // forumService fs = new forumService();

            posts.add(p);
        }

        return posts;
    }

    @Override
    public List<post> SearchByContent(String t) throws SQLException {
        return null;
    }
    @Override
    public List<post> SortForum(String t) throws SQLException {
        return null;
    }



}
