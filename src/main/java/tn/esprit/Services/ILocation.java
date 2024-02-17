package tn.esprit.Services;

import tn.esprit.Entity.Location;

import java.sql.SQLException;
import java.util.List;

public interface ILocation {
    void add(Location l) throws SQLException;
    void update(int ID,String name,String info)throws SQLException;
    void delete(int id)throws SQLException;
    List<Location> displayList() throws SQLException;
}