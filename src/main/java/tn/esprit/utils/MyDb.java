package tn.esprit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDb {


    private final String URL ="jdbc:mysql://localhost:3306/esprit";
    private final String USERNAME = "root";
    private  final String PWD="";
    private Connection con;

    public static MyDb instance;

    private MyDb(){
        try {
            con = DriverManager.getConnection(URL,USERNAME,PWD);
            System.out.println("Connected!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static MyDb getInstance(){

        if (instance == null){
            instance = new MyDb();
        }
        return instance;
    }

    public Connection getCon() {
        return con;
    }

}
