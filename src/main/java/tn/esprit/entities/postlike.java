package tn.esprit.entities;

public class postlike {
    int id,	post_idd	,user_idd;
    public postlike(){}


    public postlike(int id, int post_idd, int user_idd) {
        this.id = id;
        this.post_idd = post_idd;
        this.user_idd = user_idd;
    }

    public postlike(int id, int post_idd) {
        this.id = id;
        this.post_idd = post_idd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPost_idd() {
        return post_idd;
    }

    public void setPost_idd(int post_idd) {
        this.post_idd = post_idd;
    }

    public int getUser_idd() {
        return user_idd;
    }

    public void setUser_idd(int user_idd) {
        this.user_idd = user_idd;
    }
}
