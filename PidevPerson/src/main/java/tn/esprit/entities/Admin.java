package tn.esprit.entities;

final public class Admin extends User {


    public Admin(String Email, String pwd) {
       super(Email,pwd);
    }


    public Admin(int UserID, String Email,String pwd) {
       super(Email,pwd);
       this.UserID=UserID;
    }

    public Admin() {

    }


    @Override
    public String toString() {
        return "Admin{" +
                "userID=" + UserID +
                ", email='" + getEmail() + '\'' +
                ", pwd='" + getPwd() + '\'' +

                '}';
    }

}
