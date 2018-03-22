package com.example.nicholasrocksvold.falloutliveradio;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Radio {

    //Radio class holds the Name and array of songs
    public String radioName;
    public ArrayList<SoundObject> songs;
    private Context context;

    public Radio(Context current, String radioName, int start, int end)
    {
        int range = end - start;

        this.radioName = radioName;
        this.context = current;

        List<String> nameList = Arrays.asList(context.getResources().getStringArray(R.array.soundNames));

        for(int i = 0; i < range; i++)
        {
            songs.add(new SoundObject(nameList.get(start + 0), R.raw.audio1))
        }
    }

/*
    public int soungAmount(Radio radio)
    {
        return radio.songs.
    }
*/
}
