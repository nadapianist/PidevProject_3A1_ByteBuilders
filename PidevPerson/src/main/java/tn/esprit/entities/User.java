package tn.esprit.entities;
import java.util.Objects;

sealed abstract public class User permits Tourist,Admin,LocalCom{
    protected int UserID;
    private String Email;
    private String pwd;


    protected User( String Email, String pwd) {

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



    @Override
    public String toString() {
        return "User{" +
                "userID=" + UserID +
                ", email='" + Email + '\'' +
                ", pwd='" + pwd + '\'' +

                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(UserID, Email, pwd);
    }


}
