package com.example.nicholasrocksvold.falloutliveradio;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;

import java.util.Random;
import java.util.ArrayList;

public class Radio {

    Random r = new Random();
    MediaPlayer mp;
    WhackAMoleRandom wamR = new WhackAMoleRandom();

    private int[] radioSongs = new int[20];
    private int[] radioSongsPriority;
    private int[] musicExtroSpecific;
    private int[] musicIntroSpecific;
    private int[] musicIntroGeneric = new int[2];
    private int[] radioHello = new int[13];
    private int[] radioHelloPriority;
    private int[] newsLink = new int[6];
    private int[] newsLinkPriority;
    private int[] newsPost;
    private int[] psaIntro;
    private int[] psaIntroPriority;
    public ArrayList<int[]> newsStories = new ArrayList<>();
    public ArrayList<Integer> newsStoriesPriority = new ArrayList<>();
    private ArrayList<int[]> psaInfos = new ArrayList<>();
    private int[] psaInfosPriority; //TODO: add all PSAs

    private int prevSong;
    private int nextSong;
    public String radioName;
    private Context context;

    Radio(Context current, String radioName)
    {
        this.context = current;                     //sets context to main
        Resources res = this.context.getResources(); //grabs resources from main

        this.radioName = radioName; //establishes radio name

        if(radioName.toUpperCase().matches("GNR")) {

            for(int i = 1; i < 21; i++)
                radioSongs[i - 1] = res.getIdentifier("gnrsong"+i, "raw", context.getPackageName());

            radioSongsPriority = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};

            musicExtroSpecific = new int[]{
                    res.getIdentifier("anything_goes_extro", "raw", context.getPackageName()),    //0
                    res.getIdentifier("butcher_pete_extro", "raw", context.getPackageName()),     //1
                    res.getIdentifier("civilization_extro", "raw", context.getPackageName()),     //2
                    res.getIdentifier("crazy_he_calls_me_extro", "raw", context.getPackageName()),//3
                    res.getIdentifier("easy_livin_extro", "raw", context.getPackageName()),       //4
                    res.getIdentifier("happy_times_extro", "raw", context.getPackageName()),      //5
                    res.getIdentifier("maybe_extro", "raw", context.getPackageName()),            //6
                    res.getIdentifier("mighty_mighty_man_extro", "raw", context.getPackageName()),//7
                    res.getIdentifier("rain_must_fall_extro", "raw", context.getPackageName()),   //8
                    res.getIdentifier("way_back_home_extro", "raw", context.getPackageName()),    //9
                    res.getIdentifier("wonderful_guy_extro", "raw", context.getPackageName()),    //10
                    res.getIdentifier("world_on_fire_extro", "raw", context.getPackageName()),};  //11

            musicIntroSpecific = new int[]{
                    res.getIdentifier("anything_goes_intro", "raw", context.getPackageName()),    //0
                    res.getIdentifier("butcher_pete_intro", "raw", context.getPackageName()),     //1
                    res.getIdentifier("civilization_intro", "raw", context.getPackageName()),     //2
                    res.getIdentifier("crazy_he_calls_me_intro", "raw", context.getPackageName()),//3
                    res.getIdentifier("easy_livin_intro", "raw", context.getPackageName()),       //4
                    res.getIdentifier("happy_times_intro", "raw", context.getPackageName()),      //5
                    res.getIdentifier("maybe_intro", "raw", context.getPackageName()),            //6
                    res.getIdentifier("mighty_mighty_man_intro", "raw", context.getPackageName()),//7
                    res.getIdentifier("rain_must_fall_intro", "raw", context.getPackageName()),   //8
                    res.getIdentifier("way_back_home_intro", "raw", context.getPackageName()),    //9
                    res.getIdentifier("wonderful_guy_intro", "raw", context.getPackageName()),    //10
                    res.getIdentifier("world_on_fire_intro", "raw", context.getPackageName()),};  //11

            for(int i = 1; i < 3; i++)
                musicIntroGeneric[i - 1] = res.getIdentifier("intro_music"+i, "raw", context.getPackageName());

            for(int i = 1; i < 14; i++)
                radioHello[i - 1] = res.getIdentifier("hello"+i, "raw", context.getPackageName());

            radioHelloPriority = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1};

            for(int i = 1; i < 7; i++)
                newsLink[i - 1] = res.getIdentifier("news_link"+i, "raw", context.getPackageName());

            newsLinkPriority = new int[]{1,1,1,1,1,1};

            newsStories.add(new int[]{
                    res.getIdentifier("evergreen_raiders1", "raw", context.getPackageName()),
                    res.getIdentifier("evergreen_raiders2", "raw", context.getPackageName())});

            newsStories.add(new int[]{
                    res.getIdentifier("escape1", "raw", context.getPackageName()),
                    res.getIdentifier("escape2", "raw", context.getPackageName()),
                    res.getIdentifier("escape3", "raw", context.getPackageName())});

            newsStories.add(new int[]{
                    res.getIdentifier("forecast", "raw", context.getPackageName())});

            for(int i = 0; i < newsStories.size(); i++)
                newsStoriesPriority.add(i, 1);

            newsPost = new int[]{
                    res.getIdentifier("post_generic1", "raw", context.getPackageName()),
                    res.getIdentifier("post_generic2", "raw", context.getPackageName())
            };

            psaIntro = new int[]{
                    res.getIdentifier("psa_intro1", "raw", context.getPackageName()),
                    res.getIdentifier("psa_intro2", "raw", context.getPackageName()),
                    res.getIdentifier("psa_intro3", "raw", context.getPackageName()),
                    res.getIdentifier("psa_intro4", "raw", context.getPackageName())};

            psaIntroPriority = new int[]{1,1,1,1};

            psaInfos.add(new int[]{
                    res.getIdentifier("ghouls1", "raw", context.getPackageName()),
                    res.getIdentifier("ghouls2", "raw", context.getPackageName()),
                    res.getIdentifier("ghouls3", "raw", context.getPackageName()),
                    res.getIdentifier("ghouls4", "raw", context.getPackageName()),
                    res.getIdentifier("ghouls5", "raw", context.getPackageName()),
                    res.getIdentifier("ghouls6", "raw", context.getPackageName())});

            psaInfos.add(new int[]{
                    res.getIdentifier("mutants1", "raw", context.getPackageName()),
                    res.getIdentifier("mutants2", "raw", context.getPackageName()),
                    res.getIdentifier("mutants3", "raw", context.getPackageName()),
                    res.getIdentifier("mutants4", "raw", context.getPackageName())});

            psaInfos.add(new int[]{
                    res.getIdentifier("radiation1", "raw", context.getPackageName()),
                    res.getIdentifier("radiation2", "raw", context.getPackageName()),
                    res.getIdentifier("radiation3", "raw", context.getPackageName())});

            psaInfosPriority = new int[]{1,1,1};
        }
        else
            System.out.println("Input a correct radio name in code, please.");
    }

    public void playRadio(int flag)
    {
        System.out.println("flag: "+flag);
        nextSong = wamR.WAMR(radioSongsPriority);
        radioSongsPriority = wamR.alterPriority(radioSongsPriority, nextSong);

        mp = MediaPlayer.create(context, radioSongs[nextSong]);


        if (flag == 2)
            playGNRNews();
        else
        {
            mp = MediaPlayer.create(context, radioSongs[nextSong]);
            prevSong = nextSong;
            nextSong = wamR.WAMR(radioSongsPriority);
            radioSongsPriority = wamR.alterPriority(radioSongsPriority, nextSong);
        }

        mp.start();


    }

    private void playGNRNews()
    {
        MusicExtro();
        if(true)
        {
            GNRNews();
            PSA();
        }
        else
        {
            //TODO:Add possibility of radio theater
        }
        MusicIntro();
    }

    private void MusicExtro() {
        switch (prevSong){
            case 0: mp = MediaPlayer.create(context, musicExtroSpecific[3]);
                    break; //crazy he calls me
            case 1: mp = MediaPlayer.create(context, musicExtroSpecific[4]);
                    break; //easy livin
            case 2: mp = MediaPlayer.create(context, musicExtroSpecific[5]);
                    break; //happy times
            case 3: mp = MediaPlayer.create(context, musicExtroSpecific[9]);
                    break; //way back home
            case 4: mp = MediaPlayer.create(context, musicExtroSpecific[0]);
                    break; //anything goes
            case 5: mp = MediaPlayer.create(context, musicExtroSpecific[2]);
                    break; //civilization
            case 6: mp = MediaPlayer.create(context, musicExtroSpecific[8]);
                    break; //rain must fall
            case 15:mp = MediaPlayer.create(context, musicExtroSpecific[11]);
                    break; //world on fire
            case 16:mp = MediaPlayer.create(context, musicExtroSpecific[6]);
                    break; //maybe
            case 17:mp = MediaPlayer.create(context, musicExtroSpecific[1]);
                    break; //butcher pete
            case 18:mp = MediaPlayer.create(context, musicExtroSpecific[7]);
                    break; //mighty mighty man
            case 19:mp = MediaPlayer.create(context, musicExtroSpecific[10]);
                    break; //wonderful guy
            default:int chosen = wamR.WAMR(radioHelloPriority);
                    radioHelloPriority = wamR.alterPriority(radioHelloPriority, chosen);
                    mp = MediaPlayer.create(context, radioHello[chosen]);
        }

        mp.start();
    }

    private void GNRNews() {
        if(true)
        {
            int chosen = wamR.WAMR(newsLinkPriority);
            radioHelloPriority = wamR.alterPriority(newsLinkPriority, chosen);
            mp = MediaPlayer.create(context, newsLink[chosen]);
            mp.start();
            while(mp.isPlaying()){}

            chosen = wamR.WAMR(newsStoriesPriority);
            newsStoriesPriority = wamR.alterPriority(newsStoriesPriority, chosen);

            for(int i = 0; i < newsStories.get(chosen).length; i++)
            {
                mp = MediaPlayer.create(context, newsStories.get(chosen)[i]);
                mp.start();
                while(mp.isPlaying()){}
            }

            mp = MediaPlayer.create(context, newsPost[r.nextInt(newsPost.length)]);
            mp.start();
        }
        else
        {
            //TODO: implement karma news
        }
    }

    private void PSA() {
        int chosen = wamR.WAMR(psaIntroPriority);
        psaIntroPriority = wamR.alterPriority(psaIntroPriority, chosen);

        mp = MediaPlayer.create(context, psaIntro[chosen]);
        mp.start();

        chosen = wamR.WAMR(psaInfosPriority);
        psaIntroPriority = wamR.alterPriority(psaInfosPriority, chosen);

        for(int i = 0; i < psaInfos.get(chosen).length; i++)
        {
            mp = MediaPlayer.create(context, psaInfos.get(chosen)[i]);
            mp.start();
        }
    }

    private void MusicIntro() {
        switch (nextSong){
            case 0: mp = MediaPlayer.create(context, musicIntroSpecific[3]);
                break; //crazy he calls me
            case 1: mp = MediaPlayer.create(context, musicIntroSpecific[4]);
                break; //easy livin
            case 2: mp = MediaPlayer.create(context, musicIntroSpecific[5]);
                break; //happy times
            case 3: mp = MediaPlayer.create(context, musicIntroSpecific[9]);
                break; //way back home
            case 4: mp = MediaPlayer.create(context, musicIntroSpecific[0]);
                break; //anything goes
            case 5: mp = MediaPlayer.create(context, musicIntroSpecific[2]);
                break; //civilization
            case 6: mp = MediaPlayer.create(context, musicIntroSpecific[8]);
                break; //rain must fall
            case 15:mp = MediaPlayer.create(context, musicIntroSpecific[11]);
                break; //world on fire
            case 16:mp = MediaPlayer.create(context, musicIntroSpecific[6]);
                break; //maybe
            case 17:mp = MediaPlayer.create(context, musicIntroSpecific[1]);
                break; //butcher pete
            case 18:mp = MediaPlayer.create(context, musicIntroSpecific[7]);
                break; //mighty mighty man
            case 19:mp = MediaPlayer.create(context, musicIntroSpecific[10]);
                break; //wonderful guy
            default: mp = MediaPlayer.create(context, musicIntroGeneric[r.nextInt(musicIntroGeneric.length)]);
        }
    }
}