package tn.esprit.services;

import tn.esprit.entities.Activity;
import tn.esprit.entities.Challenge;
import tn.esprit.utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChallengeService implements IService<Challenge> {

    Connection con;
    Statement stm;
    public ChallengeService(){
        con = MyDb.getInstance().getCon();

    }
git
}
