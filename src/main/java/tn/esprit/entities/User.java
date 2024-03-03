package tn.esprit.entities;
import tn.esprit.controller.SessionManager;

import java.util.Objects;

sealed  public class User permits Tourist,Admin,LocalCom{
    protected int UserID;
    private String Email;
    private String pwd;
    private String verifcode;

    public User(int userID, String email, String pwd, String verifcode) {
        UserID = userID;
        Email = email;
        this.pwd = pwd;
        this.verifcode = verifcode;
    }

    public User(int userID) {
        UserID = userID;
    }

    public User(String email, String pwd, String verifcode) {
        Email = email;
        this.pwd = pwd;
        this.verifcode = verifcode;
    }

    public String getVerifcode() {
        return verifcode;
    }

    public void setVerification_code(String verificationCode) {
        this.verifcode = verificationCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserID=" + UserID +
                ", Email='" + Email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", verifcode='" + verifcode + '\'' +
                '}';
    }

    protected User(String Email, String pwd) {

        this.Email = Email;
        this.pwd = pwd;
    }

    protected  User(int UserID, String Email, String pwd) {
        this(Email,pwd);
        this.UserID = UserID;
    }
    public User()
    {}

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


    @Override
    public int hashCode() {
        return Objects.hash(UserID, Email, pwd);
    }
    public static User getCurrentUser() {
        // This is a simplified example assuming you have a SessionManager class
        // responsible for managing user sessions and returning the current user
        return SessionManager.getCurrentUser();
    }
    private static User loggedInUser; // Variable to store logged-in user information

    // Method to set the logged-in user
    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    // Method to get the logged-in user
    public static User getLoggedInUser() {
        return loggedInUser;
    }

}
