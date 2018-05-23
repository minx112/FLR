package com.minx112.flr;

import android.net.Uri;
import java.util.Random;

public class Quest {
    private int identifier;
    private String[] destination;
    private long duration;
    private int karmaFloor;
    private int karmaCieling;
    private Uri[] radioAddBefore;
    private Uri[] radioRemoveBefore;
    private Uri[] radioAddAfter;
    private Uri[] radioRemoveAfter;
    private int nextQuest;

    private Random random = new Random();

    Quest(int identifier, String[] destination, long duration, int karmaFloor, int karmaCieling, Uri[] radioAddBefore, Uri[] radioRemoveBefore, Uri[] radioAddAfter, Uri[] radioRemoveAfter, int nextQuest)
    {
        this.identifier = identifier;
        this.destination = new String[destination.length];
        System.arraycopy(destination, 0, this.destination, 0, destination.length);
        this.duration = duration;
        this.karmaFloor = karmaFloor;
        this.karmaCieling = karmaCieling;
        if(radioAddBefore != null)
            this.radioAddBefore = radioAddBefore.clone();
        if(radioRemoveBefore != null)
            this.radioRemoveBefore = radioRemoveBefore.clone();
        if (radioAddAfter != null)
            this.radioAddAfter = radioAddAfter.clone();
        if (radioRemoveAfter != null)
            this.radioRemoveAfter = radioRemoveAfter.clone();
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

    public Uri[] addToRadio(boolean timing){
        if(timing)
            return this.radioAddBefore;
        else
            return this.radioAddAfter;
    }

    public Uri[] removeFromRadio(boolean timing) {
        if(timing)
            return this.radioRemoveBefore;
        else
            return this.radioRemoveAfter;
    }

    public int getNextQuest()
    {
        return this.nextQuest;
    }

    public void printQuest()
    {
        System.out.println("First Destination: "+this.destination[0]);
    }

    public int getIdentifier()
    {
        return this.identifier;
    }
}
