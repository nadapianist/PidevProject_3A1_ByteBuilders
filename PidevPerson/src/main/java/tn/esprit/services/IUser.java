package tn.esprit.services;
import  tn.esprit.entities.User;
import java.sql.SQLException;
import java.util.List;

public interface IUser<T> {
    void add(T t) throws SQLException;

    void update(T t) throws SQLException;

    List<T> listAll() throws SQLException;
    void delete(int t) throws SQLException;

    //void deleteidUser(int userid) throws SQLException;

}
