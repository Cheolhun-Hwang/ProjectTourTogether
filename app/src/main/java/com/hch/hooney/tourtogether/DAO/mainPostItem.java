package com.hch.hooney.tourtogether.DAO;

/**
 * Created by qewqs on 2018-01-23.
 */

public class mainPostItem {
    private String postID;

    private String UPROFILEIMAGE;
    private String UNAME;

    private String PostImage;
    private String PostContext;

    private double mapy;
    private double mapx;
    private String Location;

    private boolean isPushLike;
    private String likeCount;
    private String commentCount;

    public mainPostItem() {
        this.postID = "";
        this.UPROFILEIMAGE = "";
        this.UNAME = "";
        this.PostImage = "";
        this.PostContext = "";
        this.mapy = 0.0;
        this.mapx = 0.0;
        this.Location = "";
        this.isPushLike = false;
        this.likeCount = "";
        this.commentCount = "";
    }

    public mainPostItem(String postID, String UPROFILEIMAGE, String UNAME, String postImage,
                        String postContext, double mapy, double mapx, String laction,
                        boolean isPushLike, String likeCount, String commentCount) {
        this.postID = postID;
        this.UPROFILEIMAGE = UPROFILEIMAGE;
        this.UNAME = UNAME;
        this.PostImage = postImage;
        this.PostContext = postContext;
        this.mapy = mapy;
        this.mapx = mapx;
        this.Location = laction;
        this.isPushLike = isPushLike;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getUPROFILEIMAGE() {
        return UPROFILEIMAGE;
    }

    public void setUPROFILEIMAGE(String UPROFILEIMAGE) {
        this.UPROFILEIMAGE = UPROFILEIMAGE;
    }

    public String getUNAME() {
        return UNAME;
    }

    public void setUNAME(String UNAME) {
        this.UNAME = UNAME;
    }

    public String getPostImage() {
        return PostImage;
    }

    public void setPostImage(String postImage) {
        PostImage = postImage;
    }

    public String getPostContext() {
        return PostContext;
    }

    public void setPostContext(String postContext) {
        PostContext = postContext;
    }

    public double getMapy() {
        return mapy;
    }

    public void setMapy(double mapy) {
        this.mapy = mapy;
    }

    public double getMapx() {
        return mapx;
    }

    public void setMapx(double mapx) {
        this.mapx = mapx;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String laction) {
        this.Location = laction;
    }

    public boolean isPushLike() {
        return isPushLike;
    }

    public void setPushLike(boolean pushLike) {
        isPushLike = pushLike;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }
}
