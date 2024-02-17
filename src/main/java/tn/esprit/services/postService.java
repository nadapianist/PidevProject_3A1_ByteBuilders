package tn.esprit.services;
import tn.esprit.entities.post;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class postService implements IService<post>{
    Connection con ;
    Statement stm;

public postService(){con= MyDataBase.getInstance().getCon();}
    @Override
    public void add(post p) throws SQLException {
        String query = "INSERT INTO `post`(`TitlePost`, `ContentPost`, `PhotoPost`, `DatePost`, `UserID`) VALUES (?, ?, ?, ?, ?)";
    try(PreparedStatement ps = con.prepareStatement(query)){
    ps.setString(1,p.getTitlePost());
    ps.setString(2,p.getContentPost());
    ps.setString(3,p.getPhotoPost());
    ps.setDate(4,new java.sql.Date(p.getDatePost().getTime()));
    ps.setInt(5,p.getUserID());

    ps.executeUpdate();
    System.out.println("post added!");
    }}
    public void update(post p) throws SQLException{
      String query="UPDATE `post` SET TitlePost=?,ContentPost=?,PhotoPost=?,DatePost=?,UserID=? WHERE IDPost=?";
        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1,p.getTitlePost());
            ps.setString(2,p.getContentPost());
            ps.setString(3,p.getPhotoPost());
            ps.setDate(4,new java.sql.Date(p.getDatePost().getTime()));
            ps.setInt(5,p.getUserID());
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
    public List<post> displayList() throws SQLException {
        String query = "SELECT  `TitlePost`, `ContentPost`, `PhotoPost`, `DatePost`,`UserID` FROM `post`";
        PreparedStatement ps= this.con.prepareStatement(query);
        ResultSet res = ps.executeQuery();
        List<post> posts = new ArrayList<>();

        while(res.next()) {
            post p = new post(res.getString("TitlePost"),
                    res.getString("ContentPost"),
                    res.getString("PhotoPost"),
                    res.getDate("DatePost"),
            res.getInt("UserID"));
            posts.add(p);
        }

        return posts;
    }


}
