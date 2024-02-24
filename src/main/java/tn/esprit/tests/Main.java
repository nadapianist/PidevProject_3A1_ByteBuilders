package tn.esprit.tests;



import java.sql.SQLException;
import java.util.List;

import tn.esprit.entities.post;
import tn.esprit.services.postService;


import java.util.Date;


public class Main {

    public static void main(String[] args) {
        try {
            testPostService();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void testPostService() throws SQLException {
        postService postService = new postService();

        // Test adding a post
        post newPost = new post("This is a test post", "test.jpg", new Date(), 1, "Test Category");
        postService.add(newPost);

        // Test displaying the list
        List<post> posts = postService.displayList();
        System.out.println("List of Posts:");
        for (post p : posts) {
            System.out.println(p);
        }

        // Test updating a post
        if (!posts.isEmpty()) {
            post postToUpdate = posts.get(0);
            postToUpdate.setContentPost("Updated content");
            postService.update(postToUpdate);
        }

        // Test displaying the updated list
        List<post> updatedPosts = postService.displayList();
        System.out.println("Updated List of Posts:");
        for (post p : updatedPosts) {
            System.out.println(p);
        }

        // Test deleting a post
        if (!updatedPosts.isEmpty()) {
            post postToDelete = updatedPosts.get(0);
            postService.delete(postToDelete.getIDPost());
        }

        // Test displaying the final list
        List<post> finalPosts = postService.displayList();
        System.out.println("Final List of Posts:");
        for (post p : finalPosts) {
            System.out.println(p);
        }
    }
}
