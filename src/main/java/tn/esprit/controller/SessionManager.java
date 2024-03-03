package tn.esprit.controller;

import tn.esprit.entities.User;

public class SessionManager {
    private static User currentUser;

    // Method to set the currently logged-in user
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    // Method to retrieve the currently logged-in user
    public static User getCurrentUser() {
        return currentUser;
    }

    // Method to clear the current session (logout)
    public static void clearSession() {
        currentUser = null;
    }
}
