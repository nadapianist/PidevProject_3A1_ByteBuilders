package tn.esprit.services;

import javafx.collections.ObservableList;
import tn.esprit.entities.Activity;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IAdvanced<T>{

    List<T> SearchByDate(Date d) throws SQLException;
    List<T> Search(String str) throws SQLException;

    ObservableList<T> Sort() ;

}
