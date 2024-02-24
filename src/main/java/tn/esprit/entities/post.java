package tn.esprit.entities;

import java.util.Date;
import java.util.List;


public class post {

    private int  IDPost, UserID;
   private String categoryPost,ContentPost	,PhotoPost;
    private Date DatePost;


    public post(int IDPost, String contentPost,String photoPost,   Date datePost,int userID, String categoryPost) {
        this.IDPost = IDPost;
        this.ContentPost =contentPost;
        this.PhotoPost = photoPost;
        this.DatePost = datePost;
        this.UserID = userID;
        this.categoryPost = categoryPost;

    }

    public post(String contentPost,String photoPost,   Date datePost,int userID, String categoryPost) {
        this.ContentPost =contentPost;

        this.PhotoPost = photoPost;
        this.DatePost = datePost;
        this.UserID = userID;
        this.categoryPost = categoryPost;
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

    public String getCategoryPost() {
        return categoryPost;
    }

    public void setCategoryPost(String categoryPost) {
        this.categoryPost = categoryPost;
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
                ", categoryPost='" + categoryPost + '\'' +
                ", ContentPost='" + ContentPost + '\'' +
                ", PhotoPost='" + PhotoPost + '\'' +
                ", DatePost=" + DatePost +
                '}';
    }

}
