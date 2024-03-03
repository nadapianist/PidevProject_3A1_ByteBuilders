package tn.esprit.entities;

public class forum {
    private int IDForum	,NB_posts;
    private String ContentForum,Category;

    public forum(int IDForum , String ContentForum,int NB_posts,  String Category) {
        this.IDForum = IDForum;
        this.ContentForum = ContentForum;
        this.NB_posts = NB_posts;
        this.Category = Category;
    }

    public forum() {

    }


    public int getIDForum() {
        return IDForum;
    }

    public void setIDForum(int IDForum) {
        this.IDForum = IDForum;
    }

    public int getNB_posts() {
        return NB_posts;
    }

    public void setNB_posts(int NB_posts) {
        this.NB_posts = NB_posts;
    }



    public String getContentForum() {
        return ContentForum;
    }

    public void setContentForum(String contentForum) {
        ContentForum = contentForum;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }


    @Override
    public String toString() {
        return "forum{" +
                ", ContentForum='" +ContentForum + '\''
                +"NB_posts=" + NB_posts + '\''
                + ", Category='" + Category + '\'' +
                '}';
    }
}
