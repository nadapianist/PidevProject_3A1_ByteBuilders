package tn.esprit.services;

import tn.esprit.entities.Location;
import tn.esprit.entities.Rating;

import java.sql.SQLException;
import java.util.List;

public interface ILocation {
    void add(Location l) throws SQLException;
    void update(int ID,String name,String info)throws SQLException;
    void delete(int id)throws SQLException;
    List<Location> displayList() throws SQLException;
    List<Location> SearchLocations(String Name) throws SQLException;
    List<Location> SortLocations() throws SQLException;

}
