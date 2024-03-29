package tn.esprit.services;

import tn.esprit.entities.Transport;

import java.sql.SQLException;
import java.util.List;

public interface ITransport {
    void add(Transport t) throws SQLException;
    void update(int id,String brand,int distance,int chargeTime)throws SQLException;
    void delete(int id)throws SQLException;
    List<Transport> displayList() throws SQLException;
    List<Transport> SearchTransports(String Brand) throws SQLException;
    List<Transport> SortTransports() throws SQLException;
}
