package es.quizit.chezlui.instagramphotoviewer;


import java.util.ArrayList;

/**
 * Created by chezlui on 05/02/16.
 */
public class InstagramPhoto {
    public String userName;
    public String caption;
    public String imageUrl;
    public String videoUrl;
    public String userProfileImageUrl;
    public int imageHeight;
    public int likesCount;
    public long creationTime;
    public ArrayList<Comment> comments;
    public int type; // 0: image, 1: video
}
