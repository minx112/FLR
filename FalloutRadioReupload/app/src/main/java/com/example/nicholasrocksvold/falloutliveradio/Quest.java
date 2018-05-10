package com.example.nicholasrocksvold.falloutliveradio;

import android.net.Uri;
import java.util.Random;

/**
 * Created by Grant Sipes on 5/5/18.
 */
//still needs get and store all these values from radio class

public class Quest {
<<<<<<< HEAD
    private Date timeClosed;
    private ArrayList<Integer> questsDone;
    private long currentQuestTime;
    private static ArrayList<Float> distances;
    private String destination;
    private int duration;
    private int karmaChange;

    public Quest() {
        timeClosed = new Date();
        questsDone = new ArrayList<>();
        currentQuestTime = 0;
        distances = new ArrayList<>();
        destination = new String();
        duration = 0;
        karmaChange = 0;

    }


    public void setKarmaChange(int karmaChange) {
        this.karmaChange = karmaChange;
    }

    public void setDuration(int duration) {

        this.duration = duration;
    }

    public void setDestination(String destination) {

        this.destination = destination;
    }

    public int getKarmaChange() {

        return karmaChange;
    }

    public int getDuration() {

        return duration;
    }

    public String getDestination() {

        return destination;
    }



    public Quest(Date tc, ArrayList<Integer> qd, long cqt, ArrayList<Float> d) {

        //transmission = new Transmission();
        timeClosed = tc;
        questsDone = qd;
        currentQuestTime = cqt;
        distances = d;
=======
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
>>>>>>> aa49f8587d8359c914161dad0e8dd54f54b40b86
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
