package tn.esprit.services;
import tn.esprit.entities.Hostel;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
public interface IAdvancedHostel<T>{
    //List<T> Search(Date d) throws SQLException;
    //List<T> Search(String str) throws SQLException;

    List<Hostel> SearchHostels(String Name) throws SQLException;

    List<Hostel> SortHostels() throws SQLException;
    // List<T> Sort() throws SQLException; for javafx later on
}
