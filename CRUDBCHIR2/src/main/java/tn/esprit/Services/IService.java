package tn.esprit.Services;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IService<T> {
    void add(T p) throws SQLException;
    void update(int id,String NH,int NS,String INF)throws SQLException;

    void delete(int id)throws SQLException;
    List<T> displayList() throws SQLException;
}
