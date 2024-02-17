package tn.esprit.Entities;

import java.util.Date;

public class Reservation {
    private int Idtourist;
    private int IDh;
    private Date date_reserv;
    private String payment_method;

    //
    public Reservation(int Idtourist, int IDh, Date date_reserv, String payment_method) {
        this.Idtourist = Idtourist;
        this.IDh = IDh;
        this.date_reserv = date_reserv;
        this.payment_method = payment_method;
    }

    // Getter and Setter methods for each attribute
    public int getIdtourist() {
        return Idtourist;
    }

    public void setIdtourist(int Idtourist) {
        this.Idtourist = Idtourist;
    }

    public int getIDh() {
        return IDh;
    }

    public void setIDh(int IDh) {
        this.IDh = IDh;
    }

    public java.sql.Date getDate_reserv() {
        return (java.sql.Date) date_reserv;
    }

    public void setDate_reserv(Date date_reserv) {
        this.date_reserv = date_reserv;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
}
