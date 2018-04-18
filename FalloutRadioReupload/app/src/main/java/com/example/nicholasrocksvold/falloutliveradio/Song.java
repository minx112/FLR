package com.example.nicholasrocksvold.falloutliveradio;

import android.net.Uri;

public class Song {
    private Uri song;
    private int priority;

    Song(Uri song)
    {
        this.song = song;
        this.priority = 1;
    }

    public Uri getSong()
    {
        return this.song;
    }

    public int getPriority()
    {
        return this.priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }
}
