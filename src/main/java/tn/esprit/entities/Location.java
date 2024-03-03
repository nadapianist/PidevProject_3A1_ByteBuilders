package tn.esprit.entities;

public class Location {
    private int ID;
    private String Name;
    private String info;
    private String category;

    private int hostelID;


    public Location(String name, String info, String category, int id_hostel) {

        this.Name = name;
        this.info = info;
        this.category = category;

        this.hostelID = id_hostel;

    }

    public Location(int ID, String name, String info, String category, int hostelID) {
        this.ID = ID;
        Name = name;
        this.info = info;
        this.category = category;

        this.hostelID = hostelID;
    }

    @Override
    public String toString() {
        return "Location{" +
                " Name='" + Name + '\'' +
                ", info='" + info + '\'' +
                ", category='" + category + '\'' +
                ", hostel=" + hostelID +
                '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getHostelID() {
        return hostelID;
    }

    public void setHostelID(int val) {
        this.hostelID = val;
    }
}