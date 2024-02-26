package tn.esprit.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.post;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;

public class MyDataBase {
    private final String URL = "jdbc:mysql://localhost:3306/esprit";
    private final String USERNAME = "root";
    private final String PWD = "";
    private Connection con;
    public static MyDataBase instance;
    private MyDataBase() {
        try {
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "");
            //con= DriverManager.getConnection(URL,USERNAME,PWD);

            System.out.println("Connected!");
        } catch (SQLException var2) {
            System.out.println(var2.getMessage());
        }

    }
    public static MyDataBase getInstance() {
        if (instance == null) {
            instance = new MyDataBase();
        }

        return instance;
    }

    public Connection getCon() {
        return this.con;
    }

    public Connection reconnect() throws SQLException {
        if (con != null && con.isClosed()) {
            con = DriverManager.getConnection(URL, USERNAME, PWD);
        }
        return con;
    }

    /*public static ObservableList<post> getDataposts(){
        Connection conn = instance.getCon();
        ObservableList<post> list= FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from post");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                list.add(new post(rs.getString("ContentPost"),rs.getString("PhotoPost"),rs.getDate("DatePost"),rs.getInt("UserID"),rs.getString("contentPost")));


            }
        }catch(Exception e)
        {

        }
        return list;
    }*/
}
