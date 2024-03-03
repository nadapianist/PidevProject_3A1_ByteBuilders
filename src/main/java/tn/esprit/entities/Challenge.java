package tn.esprit.entities;

import java.util.Date;

public class Challenge {

    private int id_chall;
    private String name_ch;
    private String desc_ch;
    private int points;



    public Challenge(String name_ch, String desc_ch, int points) {
        this.name_ch = name_ch;
        this.desc_ch = desc_ch;
        this.points = points;

    }

    public Challenge() {

    }

    public Challenge(int id_chall,String name_ch, String desc_ch, int points) {
        this.id_chall = id_chall;
        this.name_ch = name_ch;
        this.desc_ch = desc_ch;
        this.points = points;

    }


    public int getId_chall() {
        return id_chall;
    }

    public void setId_chall(int id_chall) {
        this.id_chall = id_chall;
    }

    public String getName_ch() {
        return name_ch;
    }

    public void setName_ch(String name_ch) {
        this.name_ch = name_ch;
    }

    public String getDesc_ch() {
        return desc_ch;
    }

    public void setDesc_ch(String desc_ch) {
        this.desc_ch = desc_ch;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                ", id_chall=" + id_chall +
                ", name_ch='" + name_ch + '\'' +
                ", desc_ch='" + desc_ch + '\'' +
                ", points=" + points +
                '}';
    }
}
