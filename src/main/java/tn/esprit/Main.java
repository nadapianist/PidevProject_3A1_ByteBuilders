package tn.esprit;

import tn.esprit.entities.Activity;
import tn.esprit.entities.Challenge;
import tn.esprit.services.ActivityService;
import tn.esprit.services.ChallengeService;

import java.sql.Date;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
/////////////////////////////////////////////////////////CRUD *********************************************************
        Activity a1 = new Activity(4, "hiking", Date.valueOf("2023-01-01"), "good to try");
        ActivityService as = new ActivityService();
/*
        try {
            as.addd(a1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
*/
/*
        try {
            System.out.println(as.diplayList());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        */
        /*
        Activity updatedActivity = new Activity(4, "swimming", Date.valueOf("2023-01-02"), "new description");
        try {
            as.update(updatedActivity);
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
*/
        /*
        try {
            as.delete(a1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }*/


        Challenge c1 = new Challenge(1, "hike and dance", "quickly", 100);
        ChallengeService cs = new ChallengeService();
       /* try {
            cs.addd(c1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

/*
        try {
            System.out.println(cs.diplayList());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
/*
         try {
            cs.delete(c1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        */



        Challenge updatedChallenge2 = new Challenge(7,"pas ", "pas",50);
         try {
        cs.update(updatedChallenge2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }



        /////////////////////////////////////////////////////////////////SIMPLE ADVANCED FEATURES //////////////////////////

/////search BY date
        try {
            System.out.println(as.SearchByDate(Date.valueOf("2024-02-06")));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}