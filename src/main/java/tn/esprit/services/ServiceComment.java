package tn.esprit.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import tn.esprit.entities.Comment;
import tn.esprit.entities.User;
import tn.esprit.utils.MyDataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;


public class ServiceComment {
    Connection con ;
    Statement stm;
    public ServiceComment(){con= MyDataBase.getInstance().getCon();}

    public void addComment(Comment comment) throws SQLException {
        String query = "INSERT INTO comment (user_id, post_id, commentt) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, comment.getUser_id());
            stmt.setInt(2, comment.getPost_id());
            stmt.setString(3, comment.getCommentt());
            stmt.executeUpdate();
            System.out.println("comment added!");
        }
    }
    public void update(Comment t) {
        try {

            String req = "UPDATE `comment` SET `commentt` where id= ?";
            PreparedStatement ps = con.prepareStatement(req);

            ps.setString(1, t.getCommentt());
            ps.setInt(2, (int) t.getId());
            ps.executeUpdate();
            System.out.println("Comment update");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void delete(int id) {
        try
        {
            Statement st = con.createStatement();
            String req = "DELETE FROM `comment` WHERE id = "+id+"";
            st.executeUpdate(req);
            System.out.println("Comment deleted...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Comment> getCommentsByPostId(int post_id) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM comment WHERE post_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, post_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment(
                            rs.getInt("user_id"),
                            rs.getInt("post_id"),
                            rs.getString("commentt"),
                            rs.getString("date_c")
                    );
                    comments.add(comment);
                }
            }
        }
        return comments;
    }
    public List<Comment> updated(int idp) {
        List<Comment> comments = new ArrayList<>();
        try {
            String req = "SELECT * FROM `comment` WHERE post_id= "+idp;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Comment p = new Comment();
                p.setId(rs.getInt(1));

                p.setCommentt(rs.getString("commentt"));
                p.setDate_c(rs.getString("date_c"));
                p.setPost_id(idp);
                comments.add(p);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return comments;
    }

    public Comment detail(int id) {
        Comment p = new Comment();
        try {
            String req = "SELECT * FROM `comment` WHERE id ="+id;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(req);

            p.setId(id);

            p.setCommentt(rs.getString("commentt"));


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return p;
    }
/*
        public boolean UserPost(int id ,int idc) {
            List<Comment> posts = new ArrayList<>();
            try {
                String req = "SELECT * FROM `comment` WHERE user_id = " + idc +" and id = "+id;
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(req);

                while (rs.next()) {
                    Comment p = new Comment();
                    p.setId(rs.getInt("id"));

                    posts.add(p);
                }
                // System.out.print(!posts.isEmpty());

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            if (posts.size()==0)
                return false ;
            else
                return true;
        }*/

}


