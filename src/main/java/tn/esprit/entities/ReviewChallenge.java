package tn.esprit.entities;

import java.util.Date;

public class ReviewChallenge {
    private int IDActivity;
    private int ID_challenge;
    private String Info;
    private Date Date_pub;
    private String Title_rev;
    private String nameActivity;
    private String nameChallenge;
    public ReviewChallenge() {

    }

    public ReviewChallenge(int IDActivity, int ID_challenge, String info, Date date_pub, String title_rev) {
        this.IDActivity = IDActivity;
        this.ID_challenge = ID_challenge;
        Info = info;
        Date_pub = date_pub;
        Title_rev = title_rev;
    }

    public ReviewChallenge(String info, Date date_pub, String title_rev) {
        Info = info;
        Date_pub = date_pub;
        Title_rev = title_rev;
    }

    // Constructor with nameActivity and nameChallenge
    public ReviewChallenge(int IDActivity, int ID_challenge, String Info, java.util.Date Date_pub, String Title_rev, String nameActivity, String nameChallenge) {
        this.IDActivity = IDActivity;
        this.ID_challenge = ID_challenge;
        this.Info = Info;
        this.Date_pub = Date_pub;
        this.Title_rev = Title_rev;
        this.nameActivity = nameActivity;
        this.nameChallenge = nameChallenge;
    }

    public int getIDActivity() {
        return IDActivity;
    }

    public void setIDActivity(int IDActivity) {
        this.IDActivity = IDActivity;
    }

    public int getID_challenge() {
        return ID_challenge;
    }

    public void setID_challenge(int ID_challenge) {
        this.ID_challenge = ID_challenge;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public Date getDate_pub() {
        return Date_pub;
    }

    public void setDate_pub(Date date_pub) {
        Date_pub = date_pub;
    }

    public String getTitle_rev() {
        return Title_rev;
    }

    public void setTitle_rev(String title_rev) {
        Title_rev = title_rev;
    }

    // Getters and setters for nameActivity and nameChallenge
    public String getNameActivity() {
        return nameActivity;
    }

    public void setNameActivity(String nameActivity) {
        this.nameActivity = nameActivity;
    }

    public String getNameChallenge() {
        return nameChallenge;
    }

    public void setNameChallenge(String nameChallenge) {
        this.nameChallenge = nameChallenge;
    }
    @Override
    public String toString() {
        return "ReviewChallenge{" +
                "IDActivity=" + IDActivity +
                ", ID_challenge=" + ID_challenge +
                ", Info='" + Info + '\'' +
                ", Date_pub=" + Date_pub +
                ", Title_rev='" + Title_rev + '\'' +
                '}';
    }
}
