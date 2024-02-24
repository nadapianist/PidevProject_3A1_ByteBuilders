package tn.esprit.controller;

import tn.esprit.entities.post;
import tn.esprit.utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDao {

    // Method to fetch all posts from the database
    public List<post> getAllPosts() {
        List<post> posts = new ArrayList<>();

        // Implement database retrieval logic here using JDBC
        try (Connection connection = MyDataBase.getInstance().getCon();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM post");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Create post objects based on the retrieved data
                int id = resultSet.getInt("IDPost");
                int userId = resultSet.getInt("UserID");
                String category = resultSet.getString("categoryPost");
                String content = resultSet.getString("ContentPost");
                String photo = resultSet.getString("PhotoPost");
                Date date = resultSet.getDate("DatePost");

                post currentPost = new post(id, content, photo, date, userId, category);

                // Add the post to the list
                posts.add(currentPost);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        return posts;
    }

    // Add other database-related methods as needed
}
