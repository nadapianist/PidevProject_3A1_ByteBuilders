package tn.esprit.entities;

import java.util.Date;
import java.util.List;
import javafx.beans.property.*;



public class post {
    private String ForumCategory;

    private int  IDPost, UserID,IDForum;
    private String categoryPost,ContentPost	,PhotoPost;
    private Date DatePost;
    private String photoPath;


    public post(String contentPost, String categoryPost,int userID){
        this.ContentPost =contentPost;
        this.categoryPost = categoryPost;
        this.UserID = userID;
    }
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

    public post(  String contentPost,String photoPost,String categoryPost, int userID, int IDForum) {
        UserID = userID;
        this.IDForum = IDForum;
        this.categoryPost = categoryPost;
        ContentPost = contentPost;
        PhotoPost = photoPost;
    }

    public post(int IDPost, int userID, String categoryPost, String contentPost, String photoPost, Date datePost, int IDForum) {
        this.IDPost = IDPost;
        UserID = userID;
        this.IDForum = IDForum;
        this.categoryPost = categoryPost;
        ContentPost = contentPost;
        PhotoPost = photoPost;
        DatePost = datePost;
    }

    public post() {

    }

    public int getIDForum() {
        return IDForum;
    }

    public void setIDForum(int IDForum) {
        this.IDForum = IDForum;
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public void setPostData(post p) {
    }
}
