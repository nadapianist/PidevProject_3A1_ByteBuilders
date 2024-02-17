package tn.esprit.Services;

import tn.esprit.Entity.Transport;
import tn.esprit.Utils.MyDatabase;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import java.sql.Statement;

public class TransportService implements ITransport {
    Connection con;
    Statement stm;
    public TransportService(){
        con = MyDatabase.getInstance().getCon();
    }
    @Override
    public void add(Transport p) throws SQLException {

    }

    @Override
    public void update(Transport p) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public List<Transport> displayList() throws SQLException {
        return null;
    }
}
