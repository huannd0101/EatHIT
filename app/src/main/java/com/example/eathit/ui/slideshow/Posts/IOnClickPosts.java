package com.example.eathit.ui.slideshow.Posts;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;

public interface IOnClickPosts {
    void IOnClickImage(Posts1 posts1) throws IOException;
    void IOnClickSent(Posts1 posts1, String cont, RecyclerView revComment);
}
