package tn.esprit.entities;
import java.util.Date;

public class Activity {
    private int id_act;
    private String name;
    private Date date_act;
    private String description;
    private int id_chall;

    public Activity(String name, Date date_act, String description, int id_chall) {
        this.name = name;
        this.date_act = date_act;
        this.description = description;
        this.id_chall = id_chall;
    }

    public Activity(int id_act, String name, Date date_act, String description, int id_chall) {
        this.id_act = id_act;
        this.name = name;
        this.date_act = date_act;
        this.description = description;
        this.id_chall = id_chall;
    }

    public Activity() {

    }

    public int getId_act() {
        return id_act;
    }

    public void setId_act(int id_act) {
        this.id_act = id_act;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate_act() {
        return date_act;
    }

    public void setDate_act(Date date_act) {
        this.date_act = date_act;
    }

    public String getDescription() {
        return   description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_chall() {
        return id_chall;
    }

    public void setId_chall(int id_chall) {
        this.id_chall = id_chall;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id_act=" + id_act +
                ", name='" + name + '\'' +
                ", date_act=" + date_act +
                ", Description='" +  description+ '\'' +
                ", id_chall=" + id_chall +
                '}';
    }
}
