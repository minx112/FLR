package com.example.nicholasrocksvold.falloutliveradio;

/**
 * Created by nicholasrocksvold on 4/27/18.
 */

public class Wanderer {

    private int karma;
    private boolean gender;
    private boolean isNotDone;


    public Wanderer(){

    }

    public int getKarma() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean isNotDone() {
        return isNotDone;
    }

    public void setNotDone(boolean notDone) {
        isNotDone = notDone;
    }
}
