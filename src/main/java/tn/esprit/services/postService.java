package tn.esprit.services;
import javafx.collections.ObservableList;
import tn.esprit.entities.*;
import tn.esprit.utils.MyDataBase;
import tn.esprit.services.forumService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class postService implements IService<post> {
    Connection con;
    Statement stm;

    public postService() {
        con = MyDataBase.getInstance().getCon();
    }

        public List<postlike> islikedbyuser(int postId, int userId) throws SQLException{
            List<postlike> likes = new ArrayList<>();

            try (PreparedStatement statement = con.prepareStatement("SELECT * FROM post_like WHERE post_id = ? AND user_id = ?")) {

                statement.setInt(1, postId);
                statement.setInt(2, userId);

                try (ResultSet resultSet =statement.executeQuery()) {
                    while (resultSet.next()) {
                        // Assuming you have a PostLike class to represent the data
                        postlike like = new postlike();
                        like.setId(resultSet.getInt("id"));
                        like.setPost_idd(resultSet.getInt("post_idd"));
                        like.setUser_idd(resultSet.getInt("user_idd"));

                        likes.add(like);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately, log it, or throw a custom exception
            }

            return likes;
        }
    public void addLike(int postId, int userId) throws SQLException {
        String query = "INSERT INTO post_like (post_idd, user_idd) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, 123);
            preparedStatement.setInt(2, 123);

            preparedStatement.executeUpdate();
        }
    }

    public List<String> getImagePaths() throws SQLException {
        List<String> imagePaths = new ArrayList<>();

        try (
             PreparedStatement statement = con.prepareStatement("SELECT PhotoPost FROM post")) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String imagePath = resultSet.getString("PhotoPost");
                    if (imagePath != null && !imagePath.isEmpty()) {
                        imagePaths.add(imagePath);
                    }
                }
            }
        }

        return imagePaths;
    }
    @Override
    public void add(post p) throws SQLException {
        String query = "INSERT INTO `post`(`ContentPost`, `PhotoPost`, `DatePost`, `UserID`, `categoryPost`) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, p.getContentPost());
            ps.setString(2, p.getPhotoPost());

            ps.setDate(3, new java.sql.Date(p.getDatePost().getTime()));
            ps.setInt(4, p.getUserID());
            ps.setString(5, p.getCategoryPost());

            ps.executeUpdate();
            System.out.println("post added!");
        }
    }

    public void update(post p) throws SQLException {
        String query = "UPDATE `post` SET ContentPost=?,PhotoPost=?,DatePost=?,categoryPost=?,UserID=? WHERE IDPost=?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, p.getContentPost());
            ps.setString(2, p.getPhotoPost());
            ps.setDate(3, new java.sql.Date(p.getDatePost().getTime()));
            ps.setInt(4, p.getUserID());
            ps.setString(5, p.getCategoryPost());
            // Assuming there is an 'IDPost' field in your 'post' table
            ps.setInt(6, p.getIDPost());


            ps.executeUpdate();
            System.out.println("updated!");
        }
    }

    public void delete(int IDPost) throws SQLException {
        String query = "DELETE FROM `post` WHERE IDPost = ?";
        PreparedStatement ps = this.con.prepareStatement(query);
        ps.setInt(1, IDPost);
        ps.executeUpdate();
        System.out.println("post deleted!");
    }



    private post ResultPosts(ResultSet res) throws SQLException {
        return new post(
                res.getInt("IDPost"),
                res.getString("ContentPost"),
                res.getString("PhotoPost"),
                res.getDate("DatePost"),
                res.getInt("UserID"),
                res.getString("categoryPost"));


    }

    public List<post> displayList() throws SQLException {
        String query = "SELECT  * FROM `post`";
        PreparedStatement ps = this.con.prepareStatement(query);
        ResultSet res = ps.executeQuery();
        List<post> posts = new ArrayList<>();

        while (res.next()) {
            post p = new post(
                    res.getInt("IDPost"),
                    res.getString("ContentPost"),
                    res.getString("PhotoPost"),
                    res.getDate("DatePost"),
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
    public List<post> updated() {
        List<post> posts = new ArrayList<>();
        try {
            String req = "SELECT  * FROM `post` ORDER BY DatePost DESC";
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
                post p = new post(

                res.getInt("IDPost"),
                        res.getString("ContentPost"),
                        res.getString("PhotoPost"),
                        res.getDate("DatePost"),
                        res.getInt("UserID"),
                        res.getString("categoryPost"));
                forumService fs = new forumService();
                //forum forumData = fs.getForumById(res.getInt("IDForum"));
                //p.setForumCategory(forumData.getCategory());
                // post p=ResultPosts(res);
                // forumService fs = new forumService();

                posts.add(p);
            }
            System.out.print(posts);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return posts;
    }
    public Tourist OneUser(int idu) {
        Tourist u = new Tourist();
        try {
            String req = "select * from user where id= "+idu;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                u.setFname(rs.getString("Fname"));
                u.setLname(rs.getString("Lname"));
                u.setUserID(idu);
                System.out.println(u);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u ;
    }

    @Override
    public List<post> SearchByContent(String t) throws SQLException {
        return null;
    }

    @Override
    public List<post> SortForum(String t) throws SQLException {
        return null;
    }

    @Override
    public List<String> getAllCategories() throws SQLException {
        String query = "SELECT DISTINCT Category FROM forum";
        try (Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            // rest of your code...


            //PreparedStatement ps = con.prepareStatement(query);
            List<String> categories = new ArrayList<>();
            //ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                categories.add(rs.getString("Category"));
            }

            return categories;
        }

    }



    public List<post> getPostsByCategory(String category) {
        try {
            String query = "SELECT * FROM post WHERE categoryPost = ?";
            List<post> posts = new ArrayList<>();

            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, category);

                try (ResultSet res = ps.executeQuery()) {
                    while (res.next()) {
                        post p = new post(
                                res.getInt("IDPost"),
                                res.getString("ContentPost"),
                                res.getString("PhotoPost"),
                                res.getDate("DatePost"),
                                res.getInt("UserID"),
                                res.getString("categoryPost"));

                        // Assuming you have a 'forumCategory' attribute in your 'post' class
                        p.setForumCategory(category);

                        posts.add(p);
                    }
                }
            }

            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}



