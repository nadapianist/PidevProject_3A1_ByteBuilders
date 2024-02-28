package tn.esprit.entities;

import java.util.Date;
import java.util.List;
import javafx.beans.property.*;



public class post {
    private String ForumCategory;

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

    public post(String categoryPost, String contentPost, String photoPost) {
        this.categoryPost = categoryPost;
        ContentPost = contentPost;
        PhotoPost = photoPost;
    }
    public post(String ContentPost, String PhotoPost, String categoryPost, int UserID) {
        this.categoryPost = categoryPost;
        this.ContentPost = ContentPost;
        this.PhotoPost = PhotoPost;
        this.UserID=UserID;
    }

    public int getIDPost() {
        return IDPost;
    }
    public String getForumCategory() {
        return ForumCategory;
    }

    public void setForumCategory(String forumCategory) {
        ForumCategory = forumCategory;
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
        return new Date();
    }

    public void setDatePost(Date datePost) {
        DatePost = datePost;
    }

    /*@Override
    public String toString() {
        return "post{" +
                "UserID=" + UserID +
                ", categoryPost='" + categoryPost + '\'' +
                ", ContentPost='" + ContentPost + '\'' +
                ", PhotoPost='" + PhotoPost + '\'' +
                ", DatePost=" + DatePost +
                '}';
    }*/
    @Override
    public String toString() {
        return "post{" +

                ", ContentPost='" + ContentPost + '\'' +
                ", PhotoPost='" + PhotoPost + '\'' +
                ", DatePost=" + DatePost +
                ", UserID=" + UserID +
                ", categoryPost='" + categoryPost + '\'' +
                '}';
    }


    public void setPostData(post p) {
    }
}
