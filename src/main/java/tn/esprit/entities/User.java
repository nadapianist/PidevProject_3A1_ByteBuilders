package tn.esprit.entities;
import tn.esprit.Controllers.SessionManager;

import java.util.Objects;

sealed  public class User permits Tourist,Admin,LocalCom{
    protected int UserID;
    private String Email;
    private String pwd;
    private String verifcode;
    private boolean status;

    public User(int userID, String email, String pwd, String verifcode,boolean status) {
        UserID = userID;
        Email = email;
        this.pwd = pwd;
        this.verifcode = verifcode;
        this.status=status;
    }

    public User(String email, String pwd, String verifcode,boolean status) {
        Email = email;
        this.pwd = pwd;
        this.verifcode = verifcode;
        this.status=status;
    }

    public User(int i) {
        this.UserID=i;
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
    protected User()
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    @Override
    public int hashCode() {
        return Objects.hash(UserID, Email, pwd);
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
