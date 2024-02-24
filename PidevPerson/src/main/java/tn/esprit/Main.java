package tn.esprit;
import tn.esprit.entities.*;
import java.sql.SQLException;

import tn.esprit.entities.User;
import tn.esprit.services.UserService;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService(); // Instantiate your AdminService

        // Add admin
        Admin admin = new Admin("admin@example.com", "admin123");
        try {
            userService.add(admin);
            System.out.println("Admin added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding admin: " + e.getMessage());
        }

        // List all users
        try {
            System.out.println("Listing all users:");
            for (User user : userService.listAll()) {
                System.out.println(user);
            }
        } catch (SQLException e) {
            System.err.println("Error listing users: " + e.getMessage());
        }

        // Update admin
        admin.setEmail("newadmin@example.com");
        admin.setPwd("newadmin123");
        try {
            userService.update(admin);
            System.out.println("Admin updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating admin: " + e.getMessage());
        }

        // Delete admin
        try {
            userService.delete(admin.getUserID());
            System.out.println("Admin deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting admin: " + e.getMessage());
        }
    }

}











