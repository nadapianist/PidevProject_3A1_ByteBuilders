package tn.esprit;


import tn.esprit.Services.HostelService;
import tn.esprit.Utils.myDB;
import tn.esprit.Entities.Hostel;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        myDB instance1=myDB.getInstance();
        myDB instance2=myDB.getInstance();

        Hostel p=new Hostel("dar aziz",10,"fhrbfjh");
        HostelService ps=new HostelService();
        Hostel p1=new Hostel("dar bchir",20,"bhjfbh");

        /*
        try{
            ps.add(p1);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        try{
            System.out.println(ps.displayList());
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        try{
            ps.update(p,2,"Bchir","Aziz");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        */
        try{
            ps.delete(3);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        try{
            System.out.println(ps.displayList());
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }
}