package com.example.administrator.music;

import android.net.Uri;

import java.net.URI;

/**
 * Created by Administrator on 2016/5/22.
 */
public class Song {
private String song_name,song_author,song_album;
    private int num;
private Uri song_location;

    public Song() {
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getSong_author() {
        return song_author;
    }

    public void setSong_author(String song_author) {
        this.song_author = song_author;
    }

    public String getSong_album() {
        return song_album;
    }

    public void setSong_album(String song_album) {
        this.song_album = song_album;
    }

    public Uri getSong_location() {
        return song_location;
    }

    public void setSong_location(Uri song_location) {
        this.song_location = song_location;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
