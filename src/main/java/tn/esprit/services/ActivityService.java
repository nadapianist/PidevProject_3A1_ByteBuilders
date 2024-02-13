package tn.esprit.services;

import tn.esprit.entities.Activity;
import tn.esprit.entities.Challenge;
import tn.esprit.utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public  class ActivityService implements IService<Activity> ,IAdvanced<Activity> {

    Connection con;
    Statement stm;
    PreparedStatement pste;


    public ActivityService() {
        con = MyDb.getInstance().getCon();

    }



    }


