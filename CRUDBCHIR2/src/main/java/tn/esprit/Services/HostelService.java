package tn.esprit.Services;

import tn.esprit.Entities.Hostel;
import tn.esprit.Utils.myDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HostelService implements IService<Hostel>{
    Connection con;
    Statement stm;
    public HostelService() {
        // myDB db=new myDB();
        //con=myDB.getInstance().getCon();
        con = myDB.getInstance().getCon();
    }

    @Override
    public void add(Hostel p) throws SQLException {
        String query = "INSERT INTO `hostel`(`IDHostel`,`Name_Hostel`,`NBstars`,`info`) VALUES (?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1,p.getIDHostel());
        ps.setString(2,p.getName_hostel());
        ps.setInt(3, p.getNBstars());
        ps.setString(4,p.getInfo());
        ps.executeUpdate();
        System.out.println("Hostel added!");
    }

    @Override
    public void update(int id,String NH,int NS,String INF) throws SQLException {
        String query = "UPDATE `hostel` SET `Name_Hostel` = ?, `NBstars` = ?,`info` = ? WHERE `IDHostel`= ?";
        PreparedStatement preparedStmt = con.prepareStatement(query);

// Set the parameters
        preparedStmt.setString(1, NH);
        preparedStmt.setInt(2,NS);
        preparedStmt.setString(3,INF);
        preparedStmt.setInt(4,id);
// Execute the PreparedStatement
        int row_updated=preparedStmt.executeUpdate();
        if (row_updated > 0) {
            System.out.println("Hostel updated");
        } else {
            System.out.println("Hostel not found");
        }
    }


    @Override
    public void delete(int IDHostel) throws SQLException {
        String query = "DELETE FROM `hostel` WHERE `IDHostel` = ?";
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setInt(1, IDHostel);
        int rowsDeleted = preparedStmt.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Hostel with IDHostel " + IDHostel + " deleted");

        } else {
            System.out.println("Hostel with IDHostel " + IDHostel + " not found");

        }
    }

    @Override
    public List<Hostel> displayList() throws SQLException {
        String query="SELECT * from hostel ";
        stm=con.createStatement();
        ResultSet res=stm.executeQuery(query);
        List<Hostel> hostels=new ArrayList<>();
        while(res.next()){
            Hostel p=new Hostel(res.getInt("IDHostel")
                    ,res.getString("Name_Hostel")
                    ,res.getInt("NBstars")
                    ,res.getString("info")
            );
            hostels.add(p);
        }

        return hostels;
    }
}
