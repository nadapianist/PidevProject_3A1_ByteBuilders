package tn.esprit.Services;

import tn.esprit.Entities.Hostel;
import tn.esprit.Entities.Reservation;
import tn.esprit.Utils.myDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationService {
    Connection con;
    Statement stm;
    public ReservationService() {
        // myDB db=new myDB();
        //con=myDB.getInstance().getCon();
        con = myDB.getInstance().getCon();
    }

    public void add(Reservation p) throws SQLException {
        String query = " INSERT INTO `reservation`(`Idtourist`,`IDh`,`date_reserv`,`payment_method`) VALUES (?,?,?,?) ";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1,p.getIdtourist());
        ps.setInt(2,p.getIDh());
        ps.setDate(3, p.getDate_reserv());
        ps.setString(4,p.getPayment_method());
        ps.executeUpdate();
        System.out.println("Hostel added!");
    }


    public void update2(int Idtourist, int IDh, Date date_reserv, String payment_method) throws SQLException {
        String query = "UPDATE `hostel` SET `date_reserv` = ?, `payment_method` = ? WHERE `Idtourist`= ? and `IDh`= ?";
        PreparedStatement preparedStmt = con.prepareStatement(query);

// Set the parameters
        preparedStmt.setInt(1, Idtourist);
        preparedStmt.setInt(2,IDh);
        preparedStmt.setDate(3, (java.sql.Date) date_reserv);
        preparedStmt.setString(4,payment_method);
// Execute the PreparedStatement
        int row_updated=preparedStmt.executeUpdate();
        if (row_updated > 0) {
            System.out.println("Person updated");
        } else {
            System.out.println("Person not found");
        }
    }

    public void delete(Reservation p, int id) throws SQLException {
        String query = "DELETE FROM `reservation` WHERE `Idtourist` = ? AND `IDh` = ?";
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setInt(1, p.getIdtourist());
        preparedStmt.setInt(2, p.getIDh());
        int rowsDeleted = preparedStmt.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Reservation for tourist with ID " + p.getIdtourist() + " and hostel ID " + p.getIDh() + " deleted");
        } else {
            System.out.println("Reservation for tourist with ID " + p.getIdtourist() + " and hostel ID " + p.getIDh() + " not found");
        }
    }



    public List<Reservation> displayList() throws SQLException {
        String query="SELECT * from reservation ";
        stm=con.createStatement();
        ResultSet res=stm.executeQuery(query);
        List<Reservation> reservations=new ArrayList<>();
        while(res.next()){
            Reservation p=new Reservation(res.getInt("Idtourist")
                    ,res.getInt("IDh")
                    ,res.getDate("date_reserv")
                    ,res.getString("payment_method")
            );
            reservations.add(p);
        }

        return reservations;
    }
}

