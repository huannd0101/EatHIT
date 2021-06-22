package com.example.eathit.ui.slideshow.Comment;

import com.example.eathit.ui.slideshow.Friend;
import com.example.eathit.ui.slideshow.Posts.Posts;
import com.example.eathit.ui.slideshow.Posts.Posts1;

public class Comment {
    private Integer idCmt;
    private String content;
    private String linkImgCmt;
    private String timeCmt;
    private String updateAt;
    private String restaurant;

    private Friend account;

    private Posts1 posts;

    private String food;

    public Comment(Integer idCmt, String content, String linkImgCmt, String timeCmt, String updateAt, String restaurant, Friend account, Posts1 posts, String food) {
        this.idCmt = idCmt;
        this.content = content;
        this.linkImgCmt = linkImgCmt;
        this.timeCmt = timeCmt;
        this.updateAt = updateAt;
        this.restaurant = restaurant;
        this.account = account;
        this.posts = posts;
        this.food = food;
    }

    public Integer getIdCmt() {
        return idCmt;
    }

    public void setIdCmt(Integer idCmt) {
        this.idCmt = idCmt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLinkImgCmt() {
        return linkImgCmt;
    }

    public void setLinkImgCmt(String linkImgCmt) {
        this.linkImgCmt = linkImgCmt;
    }

    public String getTimeCmt() {
        return timeCmt;
    }

    public void setTimeCmt(String timeCmt) {
        this.timeCmt = timeCmt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public Friend getAccount() {
        return account;
    }

    public void setAccount(Friend account) {
        this.account = account;
    }

    public Posts1 getPosts() {
        return posts;
    }

    public void setPosts(Posts1 posts) {
        this.posts = posts;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public Comment() {
    }
}
