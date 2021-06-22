package com.example.eathit.ui.slideshow.Posts;

import com.example.eathit.ui.slideshow.Friend;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Posts1 {

    private int idPosts;

    private String content;

    private String imgLink;

    private String creaetAt;

    private String updateAt;

    private int like ;

    private Friend account;

    public Posts1(int idPosts, String content, String imgLink, String createAt, String updateAt, int like, Friend ac) {
        this.idPosts = idPosts;
        this.content = content;
        this.imgLink = imgLink;
        this.creaetAt = createAt;
        this.updateAt = updateAt;
        this.like = like;
        this.account= ac;
    }

    public String getCreaetAt() {
        return creaetAt;
    }

    public void setCreaetAt(String creaetAt) {
        this.creaetAt = creaetAt;
    }

    public Friend getAccount() {
        return account;
    }

    public void setAccount(Friend account) {
        this.account = account;
    }

    public int getIdPosts() {
        return idPosts;
    }

    public void setIdPosts(int idPosts) {
        this.idPosts = idPosts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getCreateAt() {
        return creaetAt;
    }

    public void setCreateAt(String createAt) {
        this.creaetAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public Posts1() {
    }

}
