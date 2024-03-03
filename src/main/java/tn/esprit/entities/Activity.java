package tn.esprit.entities;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class Activity {
    private int id_act;
    private String name;
    private LocalDate date_act;
    private String description;
    private String image;


    public Activity(String name, LocalDate date_act, String description) {
        this.name = name;
        this.date_act = date_act;
        this.description = description;


    }
    public Activity(String name, LocalDate date_act, String description,String image) {
        this.name = name;
        this.date_act = date_act;
        this.description = description;
        this.image=image;


    }


    public Activity(int id_act, String name, LocalDate date_act, String description) {
        this.id_act = id_act;
        this.name = name;
        this.date_act = date_act;
        this.description = description;


    }

    public Activity(int id_act, String name, LocalDate date_act, String description,String image) {
        this.id_act = id_act;
        this.name = name;
        this.date_act = date_act;
        this.description = description;
        this.image=image;


    }
    public Activity() {

    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public LocalDate getDate_act() {
        return date_act;
    }

    public void setDate_act(LocalDate date_act) {
        this.date_act = date_act;
    }

    public String getDescription() {
        return   description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Constructor with ResultSet argument
    public Activity(ResultSet resultSet) throws SQLException {
        this.id_act = resultSet.getInt("id_act");  // Replace "id" with the actual column name
        this.name = resultSet.getString("name");  // Replace "name" with the actual column name
        this.date_act = resultSet.getDate("date_act").toLocalDate();  // Replace "date" with the actual column name
        this.description = resultSet.getString("description");  // Replace "description" with the actual column name
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id_act=" + id_act +
                ", name='" + name + '\'' +
                ", date_act=" + date_act +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
