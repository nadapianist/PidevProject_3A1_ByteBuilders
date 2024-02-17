package tn.esprit.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class myDB {
    private final String URL="jdbc:mysql://localhost:3306/projetpi2";
    private final String USERNAME="root";
    private final String PWD="";
    private Connection con;
    public static myDB instance;
    private myDB() {
        try{
            con= DriverManager.getConnection(URL,USERNAME,PWD);
            System.out.println("Connected!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
    public static myDB getInstance(){
        if(instance==null){
            instance=new myDB();
        }
        return instance;
    }
    //if we're still connected continue,otherwise create new connection instance
//to enable Singleton Design Pattern
    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
}
