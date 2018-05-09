package com.example.nicholasrocksvold.falloutliveradio;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mitchelldennen on 3/28/18.
 */
//still needs get and store all these values from radio class

public class Quest {
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
    }

    public void setDistances(ArrayList<Float> distances) {
        this.distances = distances;
    }

    public static ArrayList<Float> getDistances() {
        return distances;
    }

    public long getCurrentQuestTime() {

        return currentQuestTime;
    }

    public ArrayList<Integer> getQuestsDone() {

        return questsDone;
    }

    public Date getTimeClosed() {

        return timeClosed;
    }

    public void setCurrentQuestTime(long currentQuestTime) {

        this.currentQuestTime = currentQuestTime;
    }

    public void setQuestsDone(ArrayList<Integer> questsDone) {

        this.questsDone = questsDone;
    }

    public void setTimeClosed(Date timeClosed) {

        this.timeClosed = timeClosed;
    }
}
