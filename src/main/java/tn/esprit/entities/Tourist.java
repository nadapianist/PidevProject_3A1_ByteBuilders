package tn.esprit.entities;

final public class Tourist extends User {
    private String Fname;
    private String Lname;
    private int phone;
    private String Bio;
    private String Preferences;



    public Tourist(int UserID,String  Email, String pwd, String Fname, String Lname, int phone, String Bio, String Preferences) {
        super(UserID,Email, pwd);

        this.Fname = Fname;
        this.Lname = Lname;
        this.phone = phone;
        this.Bio = Bio;
        this.Preferences=Preferences;

    }

    public Tourist()
    {

    }

    public Tourist(int id, String fname, String lname, String email, String password, int phone, String role, String bio, String preferences) {
    }


    public String getFname(){return Fname;}
    public void setFname(String Fname){this.Fname=Fname;}

    public String getLname(){return Lname;}
    public void setLname(String Lname){this.Lname=Lname;}

    public int getPhone(){return phone;}
    public void setPhone(int phone){this.phone=phone;}


    public String getBio() {
        return Bio;
    }

    public void setBio(String Bio) {
        this.Bio = Bio;
    }





    public String getPreferences() {
        return Preferences;
    }

    public void setPreferences(String Preferences) {
        this.Preferences = Preferences;
    }

    @Override
    public String toString() {
        return "Tourist{" +
                ", Fname='" + Fname + '\'' +
                ", Lname='" + Lname + '\'' +
                ", phone='" + phone + '\'' +
                ", Bio='" + Bio + '\'' +
                ", Preferences='" + Preferences + '\'' +


                "} " + super.toString();
    }
}


