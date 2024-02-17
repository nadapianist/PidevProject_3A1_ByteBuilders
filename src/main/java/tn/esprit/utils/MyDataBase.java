package tn.esprit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
    private final String URL = "jdbc:mysql://localhost:3306/esprit";
    private final String USERNAME = "root";
    private final String PWD = "";
    private Connection con;
    public static MyDataBase instance;
    private MyDataBase() {
        try {
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "");
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
}
