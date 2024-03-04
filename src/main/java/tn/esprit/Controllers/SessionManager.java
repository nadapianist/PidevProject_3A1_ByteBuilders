package tn.esprit.Controllers;

import tn.esprit.entities.User;

public class SessionManager {
    private static SessionManager instance;
    private SessionManager() {
        // Private constructor to prevent external instantiation
    }
    private int authenticatedUserId = -1;
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setAuthenticatedUserId(int userId) {
        this.authenticatedUserId = userId;
    }
    public int getAuthenticatedUserId() {
        return authenticatedUserId;
    }

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
