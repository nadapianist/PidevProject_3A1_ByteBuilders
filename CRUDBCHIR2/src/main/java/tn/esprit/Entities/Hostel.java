package tn.esprit.Entities;

public class Hostel {
    private int IDHostel;
    private String Name_hostel;
    private int NBstars;
    private String Info;

    // Constructor
    public Hostel(int IDHostel, String Name_hostel, int NBstars, String Info) {
        this.IDHostel = IDHostel;
        this.Name_hostel = Name_hostel;
        this.NBstars = NBstars;
        this.Info = Info;
    }
    public Hostel(String Name_hostel, int NBstars, String Info) {
        this.Name_hostel = Name_hostel;
        this.NBstars = NBstars;
        this.Info = Info;
    }
    // Getter and Setter methods for each attribute
    public int getIDHostel() {
        return IDHostel;
    }

    public void setIDHostel(int IDHostel) {
        this.IDHostel = IDHostel;
    }

    public String getName_hostel() {
        return Name_hostel;
    }

    public void setName_hostel(String Name_hostel) {
        this.Name_hostel = Name_hostel;
    }

    public int getNBstars() {
        return NBstars;
    }

    public void setNBstars(int NBstars) {
        this.NBstars = NBstars;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String Info) {
        this.Info = Info;
    }
}
