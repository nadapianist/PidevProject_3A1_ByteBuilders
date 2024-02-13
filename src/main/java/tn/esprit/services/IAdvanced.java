package tn.esprit.services;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IAdvanced<T>{

    List<T> SearchByDate(Date d) throws SQLException;
    List<T> Search(String str) throws SQLException;
   // List<T> Sort() throws SQLException; for javafx later on

}
