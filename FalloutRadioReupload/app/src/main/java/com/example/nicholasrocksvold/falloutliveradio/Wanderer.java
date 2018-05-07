package com.example.nicholasrocksvold.falloutliveradio;

import java.util.ArrayList;
import java.util.Random;
import java.util.Calendar;

/**
 * Created by Grant Sipes 5/6/2018
 */

public class Wanderer {

    private final double SCALE = 24.0*128.0/16.0; //24hours * scale
    private final long HOUR_TO_MILLI = 3600000;
    private final double VAULT_TO_MEGATON = 9.0/16.0;
    private final double MEGATON_TO_GNR = 9.0/4.0;
    private final double GNR_TO_MUSEUM = 17.0/16.0;
    private final double GNR_TO_MONUMENT = 13.0/16.0;
    private final double MUSEUM_TO_MONUMENT = 1.0/2.0;

    private int karma;
    private boolean gender; //true=male false=female
    private boolean isNotDone;
    private String currentPlace;
    Radio radio;
    private Random random = new Random();

    private ArrayList<Quest> completedQuests = new ArrayList<>();
    private ArrayList<Quest> availableQuests = new ArrayList<>();
    private ArrayList<Quest> toBeDoneQuests = new ArrayList<>();
    private Quest currentQuest;


    public Wanderer(Radio radio){
        this.karma = 0;
        this.gender = true;
        this.isNotDone = true;
        this.radio = radio;
        this.currentPlace = "Vault";

        toBeDoneQuests.add(new Quest(new String[]{"GNR","Museum","Monument","GNR"}, 10800000, -10, 10, null, null, null));

        availableQuests.add(new Quest(new String[]{"Megaton"}, 10800000, -10, 10, null, null, toBeDoneQuests.get(0)));

        System.out.println("Wanderer Instantiated");
    }

    public void start()
    {
        System.out.println("Wanderer Started");
        Calendar waitTime = Calendar.getInstance();
        waitTime.setTimeInMillis(-1);

        while(true) {
            if(currentQuest == null) {
                currentQuest = availableQuests.get(random.nextInt(availableQuests.size()));
            }

            currentQuest.printQuest();
            for(int i = 0; i < currentQuest.getDestination().length; i++)
            {
                switch (currentPlace)
                {
                    case "Vault":
                        System.out.println("Case: vault");
                        if(currentQuest.getDestination()[i].equals("Megaton")) {
                            waitTime.setTimeInMillis((long)((HOUR_TO_MILLI / SCALE) * VAULT_TO_MEGATON));
                        }
                        else
                            System.out.println("ERROR IN NEXT DESTINATION!");
                        System.out.println("Wait Time: "+waitTime.getTimeInMillis());
                        break;
                    case "Megaton":
                        System.out.println("Case Megaton");
                        if(currentQuest.getDestination()[i].equals("GNR"))
                            waitTime.setTimeInMillis((long)(HOUR_TO_MILLI*MEGATON_TO_GNR/SCALE));
                        else if(currentQuest.getDestination()[i].equals("Megaton"))
                            waitTime.setTimeInMillis((long)(HOUR_TO_MILLI*VAULT_TO_MEGATON/SCALE));
                        else
                            System.out.println("ERROR IN NEXT DESTINATION!");
                        break;
                    case "GNR":
                        System.out.println("Case GNR");
                        if(currentQuest.getDestination()[i].equals("Megaton"))
                            waitTime.setTimeInMillis((long)(HOUR_TO_MILLI*MEGATON_TO_GNR/SCALE));
                        else if(currentQuest.getDestination()[i].equals("Museum"))
                            waitTime.setTimeInMillis((long)(HOUR_TO_MILLI*GNR_TO_MUSEUM/SCALE));
                        else if(currentQuest.getDestination()[i].equals("Monument"))
                            waitTime.setTimeInMillis((long)(HOUR_TO_MILLI*GNR_TO_MONUMENT/SCALE));
                        else
                            System.out.println("ERROR IN NEXT DESTINATION!");
                        break;
                    case "Museum":
                        System.out.println("Case Museum");
                        if(currentQuest.getDestination()[i].equals("GNR"))
                            waitTime.setTimeInMillis((long)(HOUR_TO_MILLI*GNR_TO_MUSEUM/SCALE));
                        else if(currentQuest.getDestination()[i].equals("Monument"))
                            waitTime.setTimeInMillis((long)(HOUR_TO_MILLI*MUSEUM_TO_MONUMENT/SCALE));
                        else
                            System.out.println("ERROR IN NEXT DESTINATION!");
                        break;
                    case "Monument":
                        System.out.println("Case Monument");
                        if(currentQuest.getDestination()[i].equals("GNR"))
                            waitTime.setTimeInMillis((long)(HOUR_TO_MILLI*GNR_TO_MONUMENT/SCALE));
                        else if(currentQuest.getDestination()[i].equals("Museum"))
                            waitTime.setTimeInMillis((long)(HOUR_TO_MILLI*GNR_TO_MONUMENT/SCALE));
                        else
                            System.out.println("ERROR IN NEXT DESTINATION!");
                        break;
                    default:
                        System.out.println("ERROR IN NEXT DESTINATION!");
                }

                Calendar startTime = Calendar.getInstance();
                Calendar timeElapsed = Calendar.getInstance();// = 0;
                timeElapsed.setTimeInMillis(0);
                while(timeElapsed.getTimeInMillis() <= waitTime.getTimeInMillis())
                {
                    timeElapsed.setTimeInMillis(Calendar.getInstance().getTimeInMillis() - startTime.getTimeInMillis());

                    try{ Thread.sleep(10000);}catch(InterruptedException e){}
                    System.out.println("Wanderer is traveling from: "+currentPlace);
                    System.out.println("Wanderer is traveling to  : "+currentQuest.getDestination()[i]);
                    System.out.println("Wanderer's progress is    :"+timeElapsed.getTimeInMillis()+" out of "+waitTime.getTimeInMillis());
                }
            }

            //completedQuests.add(currentQuest);
            //if(availableQuests.get(0).getNextQuest() != null)availableQuests.add(availableQuests.get(0).getNextQuest());
            //if(availableQuests != null)availableQuests.remove(0);
            //I've been trying to switch quests but this code crashes the app.
        }
    }

    public int getKarma(){
        return this.karma;
    }

    public void setKarma(int karma){
        this.karma = karma;
    }

    public boolean getGender(){
        return this.gender;
    }

    public void setGender(boolean gender){
        this.gender = gender;
    }

    public boolean isNotDone(){
        return this.isNotDone;
    }

    public void setNotDone(boolean notDone){
        this.isNotDone = notDone;
    }
}

/*
try{ Thread.sleep(10000);}catch(InterruptedException e){}
            System.out.println("Wanderer 10 seconds has passed");
 */

