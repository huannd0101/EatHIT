package com.example.eathit.ui.slideshow.Posts;

import com.example.eathit.ui.slideshow.Comment.Comment;
import com.example.eathit.ui.slideshow.Friend;

import java.util.List;

public class Posts {

    private Posts1 posts1;
    private Friend friend;

    private List<Comment> listComment;

    public Posts() {
    }

    public Posts(Posts1 posts1, Friend friend, List<Comment> listComment) {
        this.posts1 = posts1;
        this.friend = friend;
        this.listComment = listComment;
    }

    public Posts1 getPosts1() {
        return posts1;
    }

    public void setPosts1(Posts1 posts1) {
        this.posts1 = posts1;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public List<Comment> getListComment() {
        return listComment;
    }

    public void setListComment(List<Comment> listComment) {
        this.listComment = listComment;
    }
}
