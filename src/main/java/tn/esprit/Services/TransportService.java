package tn.esprit.Services;

import tn.esprit.Entity.Location;
import tn.esprit.Entity.Transport;
import tn.esprit.Utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransportService implements ITransport {
    Connection con;
    Statement stm;
    public TransportService(){
        con = MyDatabase.getInstance().getCon();
    }
    @Override
    public void add(Transport t) throws SQLException {
        String query = "INSERT INTO `transport`(`IDTr`,`Brand`,`Type`,`Distance`,`ChargingTime`,`IDtourist`) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1,t.getIDTr());
        ps.setString(2,t.getBrand());
        ps.setString(3, t.getType());
        ps.setInt(4, t.getDistance());
        ps.setInt(5,t.getChargingTime());
        ps.setInt(6,t.getTouristID());



        ps.executeUpdate();


    }

    @Override
    public void update(int id,String brand,int distance,int chargeTime) throws SQLException {
        String query = "UPDATE `transport` SET `Brand` = ?, `Distance` = ?,`ChargingTime`=?  WHERE `IDTr`= ?";
        PreparedStatement preparedStmt = con.prepareStatement(query);

// Set the parameters
        preparedStmt.setString(1,brand);
        preparedStmt.setInt(2,distance);
        preparedStmt.setInt(3,chargeTime);
        preparedStmt.setInt(4,id);
// Execute the PreparedStatement
        int row_updated=preparedStmt.executeUpdate();
        if (row_updated > 0) {
            System.out.println("transport successfully updated");
        } else {
            System.out.println("transport not found");
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM `transport` WHERE `IDTr` = ?";
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setInt(1, id);
        int rowsDeleted = preparedStmt.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Transport  deleted");

        } else {
            System.out.println("Transport  not found");

        }
    }

    @Override
    public List<Transport> displayList() throws SQLException {
        String query="SELECT * from transport";
        stm=con.createStatement();
        ResultSet res=stm.executeQuery(query);
        List<Transport> transports=new ArrayList<>();
        while(res.next()){
            int idTourist = res.getInt("IDtourist");
            Transport t=new Transport(
                    res.getString("Brand")
                    ,res.getString("Type")
                    ,res.getInt("Distance")
                    ,res.getInt("ChargingTime")
                    ,res.getInt("IDtourist")
            );
            transports.add(t);
        }

        return transports;
    }
}
