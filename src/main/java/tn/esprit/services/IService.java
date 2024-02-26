package tn.esprit.services;

import tn.esprit.entities.forum;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void add(T t) throws SQLException;


    void update(T t) throws SQLException;

    void delete(int t) throws SQLException;
List<T> SortForum(String t) throws SQLException;
   List<T> displayList() throws SQLException;
    List<T> SearchByContent(String t) throws SQLException;
}
