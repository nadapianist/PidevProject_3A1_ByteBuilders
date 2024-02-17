package tn.esprit.Services;

import tn.esprit.Entity.Location;
import tn.esprit.Entity.Transport;

import java.sql.SQLException;
import java.util.List;

public interface ITransport {
    void add(Transport t) throws SQLException;
    void update(Transport t)throws SQLException;
    void delete(int id)throws SQLException;
    List<Transport> displayList() throws SQLException;
}
