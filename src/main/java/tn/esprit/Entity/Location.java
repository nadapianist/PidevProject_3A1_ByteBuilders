package tn.esprit.Entity;

public class Location {
    private int ID;
    private String Name;
    private String info;
    private String category;
    private int ActivityID;
    private int hostelID;
    private int rating;
    private boolean popular;


    public Location(String name, String info, String category,int id_activity,int id_hostel, int rating) {

        this.Name = name;
        this.info = info;
        this.category = category;
        this.ActivityID=id_activity;
        this.hostelID=id_hostel;
        this.rating = rating;

    }

    @Override
    public String toString() {
        return "Location{" +
                " Name='" + Name + '\'' +
                ", info='" + info + '\'' +
                ", category='" + category + '\'' +
                ", Activity=" + ActivityID +
                ", hostel=" + hostelID +
                ", rating=" + rating +
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

    public int getActivityID() {
        return ActivityID;
    }
    public void setActivityID(int val) {
        this.ActivityID=val;
    }


    public int getHostelID() {
        return hostelID;
    }

    public void setHostelID(int val) {
        this.hostelID=val;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isPopular() {
        return popular;
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
    }
}
