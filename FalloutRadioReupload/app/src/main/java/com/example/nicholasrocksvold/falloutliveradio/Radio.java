package com.example.nicholasrocksvold.falloutliveradio;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;

import java.util.ArrayList;

public class Radio {

    //Radio class holds the Name and array of songs
    public String radioName;
    public ArrayList<MediaPlayer> songs = new ArrayList<>();
    //Used to grab resources from main
    private Context context;

    public Radio(Context current, String radioName, int start, int end)
    {
        this.context = current;                 //sets context to main
        Resources res = current.getResources(); //grabs resources from main
        int range = end - start;                //creates range of songs
        String songName = "song";               //used to grab song[variable] id

        this.radioName = radioName; //establishes radio name

        for(int i = 0; i < range; i++)
        {
            int modifier = start + i; //modifier used because of range
            //grabs song[variable] id
            int soundId = res.getIdentifier(songName + modifier, "raw", context.getPackageName());
            songs.add(MediaPlayer.create(current, soundId)); //adds to ArrayList
        }
    }
}
