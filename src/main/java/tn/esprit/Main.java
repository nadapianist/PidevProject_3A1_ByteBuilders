package tn.esprit;

import tn.esprit.services.forumService;
import tn.esprit.entities.forum;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        forumService forumService = new forumService();

        // Adding a new forum
        try {
            forumService.add(new forum("Forum Title", 5, 12, "General"));
            System.out.println("Added a new forum successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Displaying forums
        try {
            List<forum> forums = forumService.displayList();

            for (forum f : forums) {
                System.out.println(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
