package com.example.nicholasrocksvold.falloutliveradio;

import android.net.Uri;
import java.util.Random;

/**
 * Created by Grant Sipes on 5/5/18.
 */
//still needs get and store all these values from radio class

public class Quest {
    private String[] destination;
    private long duration;
    private int karmaFloor;
    private int karmaCieling;
    private Uri[] radioAdd;
    private Uri[] radioRemove;
    private Quest nextQuest;

    private Random random = new Random();

    Quest(String[] destination, long duration, int karmaFloor, int karmaCieling, Uri[] radioAdd, Uri[] radioRemove, Quest nextQuest)
    {
        this.destination = new String[destination.length];
        System.arraycopy(destination, 0, this.destination, 0, destination.length);
        this.duration = duration;
        this.karmaFloor = karmaFloor;
        this.karmaCieling = karmaCieling;
        if (radioAdd != null)
            System.arraycopy(radioAdd, 0, this.radioAdd, 0, radioAdd.length);
        if (radioRemove != null)
            System.arraycopy(radioRemove, 0, this.radioRemove, 0, radioRemove.length);
        if (nextQuest != null)
            this.nextQuest = nextQuest;
    }

    public String[] getDestination()
    {
        return this.destination;
    }

    public long getDuration()
    {
        return this.duration;
    }

    public int getKarma()
    {
        return random.nextInt(karmaCieling - karmaFloor) + karmaFloor;
    }

    public Uri[] addToRadio(){
        return radioAdd;
    }

    public Uri[] removeFromRadio() {
        return radioRemove;
    }

    public Quest getNextQuest()
    {
        return this.nextQuest;
    }

    public void printQuest()
    {
        System.out.println("First Destination: "+this.destination[0]);
    }

}
