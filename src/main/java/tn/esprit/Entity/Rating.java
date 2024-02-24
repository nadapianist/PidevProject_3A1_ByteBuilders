package tn.esprit.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Rating {
    private int idR;
    private int userId;//foreign key
    private int idLoc;//foreign key
    private Date ratingDate;
    private int nbStars;

    public Rating(int userId, int idLoc, Date ratingDate, int nbStars) {
        this.userId = userId;
        this.idLoc = idLoc;
        this.ratingDate = ratingDate;
        this.nbStars = nbStars;
    }

    public int getIdR() {
        return idR;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIdLoc() {
        return idLoc;
    }

    public void setIdLoc(int idLoc) {
        this.idLoc = idLoc;
    }

    public Date getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(Date ratingDate) {
        this.ratingDate = ratingDate;
    }

    public int getNbStars() {
        return nbStars;
    }

    public void setNbStars(int nbStars) {
        this.nbStars = nbStars;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }
}
