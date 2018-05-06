package com.example.nicholasrocksvold.falloutliveradio;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Grant Sipes 5/6/2018
 */

public class Wanderer {

    private final double SCALE = 24*128/16; //24hours * scale
    private final long HOUR_TO_MILLI = 3600000;
    private final double VAULT_TO_MEGATON = 9/16;
    private final double MEGATON_TO_GNR = 9/4;
    private final double GNR_TO_MUSEUM = 17/16;
    private final double GNR_TO_MONUMENT = 13/16;
    private final double MUSEUM_TO_MONUMENT = 1/2;

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
        this.isNotDone = false;
        this.radio = radio;
        this.currentPlace = "Vault";

        toBeDoneQuests.add(new Quest(new String[]{"GNR","Museum","Monument","GNR"}, 10800000, -10, 10, null, null, null));

        availableQuests.add(new Quest(new String[]{"Megaton"}, 10800000, -10, 10, null, null, toBeDoneQuests.get(0)));

        System.out.println("Wanderer Instantiated");
    }

    public void start()
    {
        System.out.println("Wanderer Started");
        long waitTime = -1;

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
                            waitTime = (long) (HOUR_TO_MILLI / SCALE);
                            waitTime = (long) (waitTime * VAULT_TO_MEGATON);
                        }
                        else
                            System.out.println("ERROR IN NEXT DESTINATION!");
                        System.out.println("Wait Time: "+waitTime);
                        break;
                    case "Megaton":
                        System.out.println("Case Megaton");
                        if(currentQuest.getDestination()[i].equals("GNR"))
                            waitTime = (long)(HOUR_TO_MILLI*MEGATON_TO_GNR/SCALE);
                        else if(currentQuest.getDestination()[i].equals("Megaton"))
                            waitTime = (long)(HOUR_TO_MILLI*VAULT_TO_MEGATON/SCALE);
                        else
                            System.out.println("ERROR IN NEXT DESTINATION!");
                        break;
                    case "GNR":
                        System.out.println("Case GNR");
                        if(currentQuest.getDestination()[i].equals("Megaton"))
                            waitTime = (long)(HOUR_TO_MILLI*MEGATON_TO_GNR/SCALE);
                        else if(currentQuest.getDestination()[i].equals("Museum"))
                            waitTime = (long)(HOUR_TO_MILLI*GNR_TO_MUSEUM/SCALE);
                        else if(currentQuest.getDestination()[i].equals("Monument"))
                            waitTime = (long)(HOUR_TO_MILLI*GNR_TO_MONUMENT/SCALE);
                        else
                            System.out.println("ERROR IN NEXT DESTINATION!");
                        break;
                    case "Museum":
                        System.out.println("Case Museum");
                        if(currentQuest.getDestination()[i].equals("GNR"))
                            waitTime = (long)(HOUR_TO_MILLI*GNR_TO_MUSEUM/SCALE);
                        else if(currentQuest.getDestination()[i].equals("Monument"))
                            waitTime = (long)(HOUR_TO_MILLI*MUSEUM_TO_MONUMENT/SCALE);
                        else
                            System.out.println("ERROR IN NEXT DESTINATION!");
                        break;
                    case "Monument":
                        System.out.println("Case Monument");
                        if(currentQuest.getDestination()[i].equals("GNR"))
                            waitTime = (long)(HOUR_TO_MILLI*GNR_TO_MONUMENT/SCALE);
                        else if(currentQuest.getDestination()[i].equals("Museum"))
                            waitTime = (long)(HOUR_TO_MILLI*MUSEUM_TO_MONUMENT/SCALE);
                        else
                            System.out.println("ERROR IN NEXT DESTINATION!");
                        break;
                    default:
                        System.out.println("ERROR IN NEXT DESTINATION!");
                }

                long startTime = System.currentTimeMillis();
                long timeElapsed = 0;
                while(timeElapsed <= waitTime)
                {
                    timeElapsed = System.currentTimeMillis() - startTime;

                    try{ Thread.sleep(10000);}catch(InterruptedException e){}
                    System.out.println("Wanderer is traveling from: "+currentPlace);
                    System.out.println("Wanderer is traveling to  : "+currentQuest.getDestination()[i]);
                    System.out.println("Wanderer's progress is    :"+timeElapsed+" out of "+waitTime);
                }
            }

        }
    }

}

/*
try{ Thread.sleep(10000);}catch(InterruptedException e){}
            System.out.println("Wanderer 10 seconds has passed");
 */
