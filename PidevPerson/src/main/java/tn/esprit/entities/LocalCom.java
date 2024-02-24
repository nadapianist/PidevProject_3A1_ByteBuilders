package tn.esprit.entities;
import tn.esprit.entities.User;
final public class LocalCom extends User {
    private String Fname;
    private String Lname;
    private int phone;
    private String Availability;



    public LocalCom(int UserID,String  Email, String pwd, String Fname, String Lname, int phone, String Availability) {
        super(UserID,Email, pwd);

        this.Fname = Fname;
        this.Lname = Lname;
        this.phone = phone;
        this.Availability=Availability;
    }

    public LocalCom()
    {

    }

    public LocalCom(String  Email, String pwd, String Fname, String Lname, int phone, String Availability) {
        super(Email,pwd);
        this.Fname=Fname;
        this.Lname=Lname;
        this.phone=phone;
        this.Availability=Availability;

    }
    public String getFname(){return Fname;}
    public void setFname(String Fname){this.Fname=Fname;}

    public String getLname(){return Lname;}
    public void setLname(String Lname){this.Lname=Lname;}

    public int getPhone(){return phone;}
    public void setPhone(int phone){this.phone=phone;}
    public String getAvailability() {
        return Availability;
    }

    public void setAvailability(String Availability) {
        this.Availability = Availability;
    }

    @Override
    public String toString() {
        return "Local Comittee{" +
                ", Fname='" + Fname + '\'' +
                ", Lname='" + Lname + '\'' +
                ", phone='" + phone + '\'' +
                ", Availability='" + Availability + '\'' +

                "} " + super.toString();

    }
}
