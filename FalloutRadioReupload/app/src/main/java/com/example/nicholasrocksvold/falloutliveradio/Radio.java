package com.example.nicholasrocksvold.falloutliveradio;

import java.util.ArrayList;

public class Radio {

    //Radio class holds the Name and array of songs
    public String radioName;
    public ArrayList<SoundObject> songs;

    public Radio(String radioName, int start, int end)
    {
        int range = end - start;

        this.radioName = radioName;

        for(int i = 0; i < range; i++)
        {
            songs.add(new SoundObject(nameList))
        }
    }

/*
    public int soungAmount(Radio radio)
    {
        return radio.songs.
    }
*/
}
