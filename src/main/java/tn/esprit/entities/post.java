package tn.esprit.entities;

import java.util.Date;

public class post {
   private int  IDPost, UserID;
   private String TitlePost,ContentPost	,PhotoPost;
    private Date DatePost;

    public post(int IDPost, String titlePost, String contentPost, String photoPost, Date datePost, int userID) {
        this.IDPost = IDPost;

        this.TitlePost = titlePost;
        this.ContentPost = contentPost;
        this.PhotoPost = photoPost;
        this.DatePost = datePost;
        this.UserID = userID;
    }

    public post(String titlePost, String contentPost, String photoPost, Date datePost,int userID) {

        TitlePost = titlePost;
        ContentPost = contentPost;
        PhotoPost = photoPost;
        DatePost = datePost;
        UserID = userID;
    }

    public int getIDPost() {
        return IDPost;
    }

    public void setIDPost(int IDPost) {
        this.IDPost = IDPost;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getTitlePost() {
        return TitlePost;
    }

    public void setTitlePost(String titlePost) {
        TitlePost = titlePost;
    }

    public String getContentPost() {
        return ContentPost;
    }

    public void setContentPost(String contentPost) {
        ContentPost = contentPost;
    }

    public String getPhotoPost() {
        return PhotoPost;
    }

    public void setPhotoPost(String photoPost) {
        PhotoPost = photoPost;
    }

    public Date getDatePost() {
        return DatePost;
    }

    public void setDatePost(Date datePost) {
        DatePost = datePost;
    }

    @Override
    public String toString() {
        return "post{" +
                "UserID=" + UserID +
                ", TitlePost='" + TitlePost + '\'' +
                ", ContentPost='" + ContentPost + '\'' +
                ", PhotoPost='" + PhotoPost + '\'' +
                ", DatePost=" + DatePost +
                '}';
    }
}
