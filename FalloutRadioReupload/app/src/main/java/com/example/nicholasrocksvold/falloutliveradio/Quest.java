package com.example.nicholasrocksvold.falloutliveradio;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mitchelldennen on 3/28/18.
 */

public class Quest {
    private Quest next;
    private Quest prev;
    //private Transmission transmission;
    private Date date;
    //private Quest unlock_quests;
    private ArrayList<Integer> unlock_quests;
    private int quest_number;


    public Quest() {
        next = null;
        prev = null;
        //transmission = new Transmission();
        date = null;
        //unlock_quests = new Quest();
        unlock_quests = new ArrayList<>();
    }

    public Quest(Date d, ArrayList uq, int qn) {
        next = null;
        prev = null;
        //transmission = new Transmission();
        date = d;
        unlock_quests = uq;
        quest_number = qn;
    }

    public void setQuest_number(int quest_number) {
        this.quest_number = quest_number;
    }

    public void setUnlock_quests(ArrayList<Integer> unlock_quests) {

        this.unlock_quests = unlock_quests;
    }

    public void setDate(Date date) {

        this.date = date;
    }



    public int getQuest_number() {

        return quest_number;
    }

    public ArrayList<Integer> getUnlock_quests() {

        return unlock_quests;
    }

    public Date getDate() {

        return date;
    }



}
