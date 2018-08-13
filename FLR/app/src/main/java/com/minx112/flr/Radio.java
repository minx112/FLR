package com.minx112.flr;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import java.util.Calendar;
import java.util.Random;
import java.util.ArrayList;

public class Radio {
    Random r = new Random();
    private WhackAMoleRandom wamR = new WhackAMoleRandom();

    private Song[] radioSongs = new Song[20];
    private Uri[] musicExtroSpecific;
    private Uri[] musicIntroSpecific;
    private Uri[] musicIntroGeneric = new Uri[2];
    private Song[] radioHello = new Song[13];
    private Song[] newsLink = new Song[6];
    private Uri[] newsPost;
    private Song[] psaIntro;
    private ArrayList<Song[]> newsStories = new ArrayList<>();
    private ArrayList<Song[]> psaInfos = new ArrayList<>();
    private ArrayList<Uri[]> radioTheater = new ArrayList<>();

    private static Uri prevSong; //also functionally current song
    private static Uri nextSong;
    private static Uri audio;
    private Calendar currentTime;
    private long timeModifier;
    private Calendar timeFlag;
    private int onTheFifteen;
    private static int flag;
    private int[] lastPlayed = new int[]{0,0,0}; //0=story, 1=track, 2=maxTrack
    private int[] theaterSequence = new int[]{0,-1,0}; //0=part, 1=track, 2=maxTrack

    private int wandererKarma = 0;
    private int wandererLevel = 1;

    private String uriPath = "android.resource://com.minx112.flr/raw/";

    private Context mContext;

    Radio(Context current, String radioName)
    {
        this.mContext = current;
        this.currentTime = Calendar.getInstance();
        this.timeFlag = (Calendar)this.currentTime.clone();

        if(r.nextBoolean())
            timeModifier = (long)(-1 * 300000 * r.nextDouble());
        else
            timeModifier = (long)(300000 * r.nextDouble());

        if(currentTime.get(Calendar.MINUTE) <= 15) //less than xx:15:xx
        {
            onTheFifteen = 15;
            this.timeFlag.set(Calendar.MINUTE, 15);
        }
        else if(currentTime.get(Calendar.MINUTE) <= 30) //less than xx:30:xx
        {
            onTheFifteen = 30;
            this.timeFlag.set(Calendar.MINUTE, 30);
        }
        else if(currentTime.get(Calendar.MINUTE) <= 45) //less than xx:45:xx
        {
            onTheFifteen = 45;
            this.timeFlag.set(Calendar.MINUTE, 45);
        }
        else //before xx:00:xx
        {
            onTheFifteen = 0;
            this.timeFlag.add(Calendar.HOUR_OF_DAY, 1);
            this.timeFlag.set(Calendar.MINUTE, 0);
        }

        this.timeFlag.add(Calendar.MINUTE, (int)timeModifier/60000);
        this.timeFlag.add(Calendar.SECOND, (int)(timeModifier%60000)/1000);
        this.timeFlag.add(Calendar.MILLISECOND, (int)(timeModifier%60000)%1000);

        System.out.println("Current News Flag: "+timeFlag.get(Calendar.HOUR_OF_DAY)+"H "+timeFlag.get(Calendar.MINUTE)+"M");

        if(radioName.toUpperCase().matches("GNR")) {

            for(int i = 1; i < 21; i++)
                radioSongs[i - 1] = new Song(Uri.parse(uriPath+"gnrsong"+i));

            musicExtroSpecific = new Uri[]{
                    Uri.parse(uriPath+"anything_goes_extro"),
                    Uri.parse(uriPath+"butcher_pete_extro"),
                    Uri.parse(uriPath+"civilization_extro"),
                    Uri.parse(uriPath+"crazy_he_calls_me_extro"),
                    Uri.parse(uriPath+"easy_livin_extro"),
                    Uri.parse(uriPath+"happy_times_extro"),
                    Uri.parse(uriPath+"maybe_extro"),
                    Uri.parse(uriPath+"mighty_mighty_man_extro"),
                    Uri.parse(uriPath+"rain_must_fall_extro"),
                    Uri.parse(uriPath+"way_back_home_extro"),
                    Uri.parse(uriPath+"wonderful_guy_extro"),
                    Uri.parse(uriPath+"world_on_fire_extro")};

            musicIntroSpecific = new Uri[]{
                    Uri.parse(uriPath+"anything_goes_intro"),
                    Uri.parse(uriPath+"butcher_pete_intro"),
                    Uri.parse(uriPath+"civilization_intro"),
                    Uri.parse(uriPath+"crazy_he_calls_me_intro"),
                    Uri.parse(uriPath+"easy_livin_intro"),
                    Uri.parse(uriPath+"happy_times_intro"),
                    Uri.parse(uriPath+"maybe_intro"),
                    Uri.parse(uriPath+"mighty_mighty_man_intro"),
                    Uri.parse(uriPath+"rain_must_fall_intro"),
                    Uri.parse(uriPath+"way_back_home_intro"),
                    Uri.parse(uriPath+"wonderful_guy_intro"),
                    Uri.parse(uriPath+"world_on_fire_intro")};

            for(int i = 1; i < 3; i++)
                musicIntroGeneric[i - 1] = Uri.parse(uriPath+"intro_music"+i);

            for(int i = 1; i < 14; i++)
                radioHello[i - 1] = new Song(Uri.parse(uriPath+"hello"+i));

            for(int i = 1; i < 7; i++)
                newsLink[i - 1] = new Song(Uri.parse(uriPath+"news_link"+i));

            newsStories.add(new Song[]{
                    new Song(Uri.parse(uriPath+"evergreen_raiders1")),
                    new Song(Uri.parse(uriPath+"evergreen_raiders2"))});

            newsStories.add(new Song[]{
                    new Song(Uri.parse(uriPath+"escape1")),
                    new Song(Uri.parse(uriPath+"escape2")),
                    new Song(Uri.parse(uriPath+"escape3"))});

            newsStories.add(new Song[]{
                    new Song(Uri.parse(uriPath+"intro1")),
                    new Song(Uri.parse(uriPath+"intro2")),
                    new Song(Uri.parse(uriPath+"intro3")),
                    new Song(Uri.parse(uriPath+"intro4")),
                    new Song(Uri.parse(uriPath+"intro5")),
                    new Song(Uri.parse(uriPath+"intro6")),
                    new Song(Uri.parse(uriPath+"intro7")),
                    new Song(Uri.parse(uriPath+"intro8"))});

            newsStories.add(new Song[]{
                    new Song(Uri.parse(uriPath+"forecast"))});

            newsStories.add(new Song[]{
                    new Song(Uri.parse(uriPath+"grayditch1")),
                    new Song(Uri.parse(uriPath+"grayditch2")),
                    new Song(Uri.parse(uriPath+"grayditch3"))});

            newsStories.add(new Song[]{
                    new Song(Uri.parse(uriPath+"praise_brotherhood1")),
                    new Song(Uri.parse(uriPath+"praise_brotherhood2")),
                    new Song(Uri.parse(uriPath+"praise_brotherhood3")),
                    new Song(Uri.parse(uriPath+"praise_brotherhood4"))});

            newsStories.add(new Song[]{
                    new Song(Uri.parse(uriPath+"superhuman1")),
                    new Song(Uri.parse(uriPath+"superhuman2")),
                    new Song(Uri.parse(uriPath+"superhuman3")),
                    new Song(Uri.parse(uriPath+"superhuman4")),
                    new Song(Uri.parse(uriPath+"superhuman5"))});

            newsStories.add(new Song[]{
                    new Song(Uri.parse(uriPath+"talon1")),
                    new Song(Uri.parse(uriPath+"talon2")),
                    new Song(Uri.parse(uriPath+"talon3"))});

            newsStories.add(new Song[]{
                    new Song(Uri.parse(uriPath+"tensions1")),
                    new Song(Uri.parse(uriPath+"tensions2")),
                    new Song(Uri.parse(uriPath+"tensions3")),
                    new Song(Uri.parse(uriPath+"tensions4")),
                    new Song(Uri.parse(uriPath+"tensions5"))});

            newsStories.add(new Song[]{
                    new Song(Uri.parse(uriPath+"tenpenny_tower1")),
                    new Song(Uri.parse(uriPath+"tenpenny_tower2")),
                    new Song(Uri.parse(uriPath+"tenpenny_tower3"))});

            newsStories.add(new Song[]{
                    new Song(Uri.parse(uriPath+"trees1")),
                    new Song(Uri.parse(uriPath+"trees2")),
                    new Song(Uri.parse(uriPath+"trees3")),
                    new Song(Uri.parse(uriPath+"trees4")),
                    new Song(Uri.parse(uriPath+"trees5"))});

            newsPost = new Uri[]{
                    Uri.parse(uriPath+"post_generic1"),
                    Uri.parse(uriPath+"post_generic2")};

            psaIntro = new Song[]{
                    new Song(Uri.parse(uriPath+"psa_intro1")),
                    new Song(Uri.parse(uriPath+"psa_intro2")),
                    new Song(Uri.parse(uriPath+"psa_intro3")),
                    new Song(Uri.parse(uriPath+"psa_intro4"))};

            psaInfos.add(new Song[]{
                    new Song(Uri.parse(uriPath+"ghouls1")),
                    new Song(Uri.parse(uriPath+"ghouls2")),
                    new Song(Uri.parse(uriPath+"ghouls3")),
                    new Song(Uri.parse(uriPath+"ghouls4")),
                    new Song(Uri.parse(uriPath+"ghouls5")),
                    new Song(Uri.parse(uriPath+"ghouls6"))});

            psaInfos.add(new Song[]{
                    new Song(Uri.parse(uriPath+"mutants1")),
                    new Song(Uri.parse(uriPath+"mutants2")),
                    new Song(Uri.parse(uriPath+"mutants3")),
                    new Song(Uri.parse(uriPath+"mutants4"))});

            psaInfos.add(new Song[]{
                    new Song(Uri.parse(uriPath+"radiation1")),
                    new Song(Uri.parse(uriPath+"radiation2")),
                    new Song(Uri.parse(uriPath+"radiation3"))});

            psaInfos.add(new Song[]{
                    new Song(Uri.parse(uriPath+"raiders1")),
                    new Song(Uri.parse(uriPath+"raiders2")),
                    new Song(Uri.parse(uriPath+"raiders3")),
                    new Song(Uri.parse(uriPath+"raiders4"))});

            psaInfos.add(new Song[]{
                    new Song(Uri.parse(uriPath+"weapons1")),
                    new Song(Uri.parse(uriPath+"weapons2")),
                    new Song(Uri.parse(uriPath+"weapons3"))});

            psaInfos.add(new Song[]{new Song(Uri.parse(uriPath+"yaoguai"))});

            radioTheater.add(new Uri[]{
                    Uri.parse(uriPath+"part1_1"), Uri.parse(uriPath+"part1_2"),
                    Uri.parse(uriPath+"part1_3"), Uri.parse(uriPath+"part1_4"),
                    Uri.parse(uriPath+"part1_5"), Uri.parse(uriPath+"part1_6"),
                    Uri.parse(uriPath+"part1_7"), Uri.parse(uriPath+"part1_8"),
                    Uri.parse(uriPath+"part1_9"), Uri.parse(uriPath+"part1_10"),
                    Uri.parse(uriPath+"part1_11"), Uri.parse(uriPath+"part1_12"),
                    Uri.parse(uriPath+"part1_13"), Uri.parse(uriPath+"part1_14"),
                    Uri.parse(uriPath+"part1_15"), Uri.parse(uriPath+"part1_16"),
                    Uri.parse(uriPath+"part1_17"), Uri.parse(uriPath+"part1_18"),
                    Uri.parse(uriPath+"part1_19"), Uri.parse(uriPath+"part1_20")});

            radioTheater.add(new Uri[]{
                    Uri.parse(uriPath+"part2_1"), Uri.parse(uriPath+"part2_2"),
                    Uri.parse(uriPath+"part2_3"), Uri.parse(uriPath+"part2_4"),
                    Uri.parse(uriPath+"part2_5"), Uri.parse(uriPath+"part2_6"),
                    Uri.parse(uriPath+"part2_7"), Uri.parse(uriPath+"part2_8"),
                    Uri.parse(uriPath+"part2_9"), Uri.parse(uriPath+"part2_10"),
                    Uri.parse(uriPath+"part2_11"), Uri.parse(uriPath+"part2_12"),
                    Uri.parse(uriPath+"part2_13"), Uri.parse(uriPath+"part2_14"),
                    Uri.parse(uriPath+"part2_15"), Uri.parse(uriPath+"part2_16"),
                    Uri.parse(uriPath+"part2_17"), Uri.parse(uriPath+"part2_18"),
                    Uri.parse(uriPath+"part2_19"), Uri.parse(uriPath+"part2_20"),
                    Uri.parse(uriPath+"part2_21"), Uri.parse(uriPath+"part2_22"),
                    Uri.parse(uriPath+"part2_23"), Uri.parse(uriPath+"part2_24")});

            radioTheater.add(new Uri[]{
                    Uri.parse(uriPath+"part3_1"), Uri.parse(uriPath+"part3_2"),
                    Uri.parse(uriPath+"part3_3"), Uri.parse(uriPath+"part3_4"),
                    Uri.parse(uriPath+"part3_5"), Uri.parse(uriPath+"part3_6"),
                    Uri.parse(uriPath+"part3_7"), Uri.parse(uriPath+"part3_8"),
                    Uri.parse(uriPath+"part3_9"), Uri.parse(uriPath+"part3_10"),
                    Uri.parse(uriPath+"part3_11"), Uri.parse(uriPath+"part3_12"),
                    Uri.parse(uriPath+"part3_13"), Uri.parse(uriPath+"part3_14"),
                    Uri.parse(uriPath+"part3_15"), Uri.parse(uriPath+"part3_16"),
                    Uri.parse(uriPath+"part3_17"), Uri.parse(uriPath+"part3_18")});

            radioTheater.add(new Uri[]{
                    Uri.parse(uriPath+"part4_1"), Uri.parse(uriPath+"part4_2"),
                    Uri.parse(uriPath+"part4_3"), Uri.parse(uriPath+"part4_4"),
                    Uri.parse(uriPath+"part4_5"), Uri.parse(uriPath+"part4_6"),
                    Uri.parse(uriPath+"part4_7"), Uri.parse(uriPath+"part4_8"),
                    Uri.parse(uriPath+"part4_9"), Uri.parse(uriPath+"part4_10"),
                    Uri.parse(uriPath+"part4_11"), Uri.parse(uriPath+"part4_12"),
                    Uri.parse(uriPath+"part4_13"), Uri.parse(uriPath+"part4_14"),
                    Uri.parse(uriPath+"part4_15"), Uri.parse(uriPath+"part4_16"),
                    Uri.parse(uriPath+"part4_17"), Uri.parse(uriPath+"part4_18"),
                    Uri.parse(uriPath+"part4_19"), Uri.parse(uriPath+"part4_20"),
                    Uri.parse(uriPath+"part4_21")});
        }
        else
            System.out.println("Input a correct radio name in code, please.");
    }

    public void setWandererFlags(int currentKarma, int currentLevel)
    {
        this.wandererKarma = currentKarma;
        this.wandererLevel = currentLevel;
    }

    public void playRadio(MediaPlayer mp)
    {
        nextSong =  Uri.parse("android.resource://com.minx112.flr/raw/radio_start");//start static
        try {
            mp.setDataSource(mContext, nextSong);
            mp.prepare();
            mp.start();

            setSong();
        }catch(java.io.IOException e){
            System.out.println("Playing song error");
        }

        //if(wanderer.isNotDone())
        while(true) {

            //play music until about the 15 of the hour is hit
            while (currentTime.before(this.timeFlag)) {
                this.currentTime = Calendar.getInstance();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        try {
                            System.out.println("Current News Flag: "+timeFlag.get(Calendar.HOUR_OF_DAY)+"H "+timeFlag.get(Calendar.MINUTE)+"M");
                            mp.reset();
                            mp.setDataSource(mContext, nextSong);
                            mp.prepare();
                            mp.start();

                            setSong();
                        }catch(java.io.IOException e){
                            System.out.println("Playing song error");
                        }
                    }
                });
            }
            /*play news
            -1 = break news
            0 = music extro
            1 = theater
            2 = news link
            3 = news story
            4 = news post
            5 = psa intro
            6 = psa
            7 = music intro
            */
            flag = 0;
            while(flag != -1)
            {
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {

                        if(flag == 0) //set music extro
                        {
                            if(r.nextInt(100) < 75)
                                flag += 2; //play news
                            else
                                flag++;
                            audio = setMusicExtro();
                        }

                        try {
                            mp.reset();
                            mp.setDataSource(mContext, audio);
                            mp.prepare();
                            mp.start();
                        }catch(java.io.IOException e){
                            System.out.println("Playing news error at flag "+flag);
                        }
                        if(flag == 1)//set theater
                        {
                            if(theaterSequence[1] < theaterSequence[2])
                                audio = setTheater();
                            else //theater is done
                            {
                                audio = Uri.parse(uriPath + "theater_outro");
                                flag = 7; //jump to music extro
                                if (theaterSequence[0] == 3) //ready next theater story
                                    theaterSequence[0] = 0;
                                else
                                    theaterSequence[0]++;
                                theaterSequence[1] = -1; //reset rest of array
                                theaterSequence[2] = 0;
                            }
                        }
                        else if(flag == 2)//set news link
                        {
                            flag++;
                            audio = setNewsLink();
                        }
                        else if(flag == 3)//set news story
                        {
                            audio = setNewsStory();

                            if(lastPlayed[1] == lastPlayed[2])//story is done
                            {
                                flag++;
                                for (int i = 0; i < lastPlayed.length; i++)
                                    lastPlayed[i] = 0;
                            }
                        }
                        else if(flag == 4)//set news post
                        {
                            flag++;
                            audio = setNewsPost();
                        }
                        else if(flag == 5)//set psa intro
                        {
                            flag++;
                            audio = setPSAIntro();
                        }
                        else if(flag == 6)//set psa info
                        {
                            audio = setPSAInfo();

                            if(lastPlayed[1] == lastPlayed[2])//PSA is done
                            {
                                flag++;
                                for (int i = 0; i < lastPlayed.length; i++)
                                    lastPlayed[i] = 0;
                            }
                        }
                        else if(flag == 7)//set music intro
                        {
                            flag++;
                            audio = setMusicIntro();
                        }
                        else
                            flag = -1;//news is done
                    }
                });
            }

            if(r.nextBoolean())
                timeModifier = (long)(-1 * 300000 * r.nextDouble());
            else
                timeModifier = (long)(300000 * r.nextDouble());

            switch (onTheFifteen)
            {
                case 15: onTheFifteen = 30;
                    this.timeFlag.set(Calendar.MINUTE, 30);
                    break;
                case 30: onTheFifteen = 45;
                    this.timeFlag.set(Calendar.MINUTE, 45);
                    break;
                case 45: onTheFifteen = 0;
                    this.timeFlag.add(Calendar.HOUR_OF_DAY, 1);
                    this.timeFlag.set(Calendar.MINUTE, 0);
                    break;
                case 0: onTheFifteen = 15;
                    this.timeFlag.set(Calendar.MINUTE, 15);
            }

            this.currentTime = Calendar.getInstance();

            timeFlag.add(Calendar.MINUTE, (int)timeModifier/60000 + 15);
            timeFlag.add(Calendar.SECOND, (int)(timeModifier%60000)/1000);
            timeFlag.add(Calendar.MILLISECOND, (int)(timeModifier%60000)%1000);

            System.out.println("Current News Flag: "+timeFlag.get(Calendar.HOUR_OF_DAY)+"H "+timeFlag.get(Calendar.MINUTE)+"M");
        }
                /*
                else
                    check wanderer karma and play ending
                 */
    }

    private void setSong()
    {
        int chosenSong = wamR.WAMR(radioSongs);
        prevSong = nextSong;
        nextSong = radioSongs[chosenSong].getSong();
        wamR.alterPriority(radioSongs, chosenSong);
    }

    private Uri setMusicExtro() {
        Uri musicExtroAudio;

        if(prevSong.equals(Uri.parse(uriPath+"gnrsong1")))
            musicExtroAudio = musicExtroSpecific[3];
        else if(prevSong.equals(Uri.parse(uriPath+"gnrsong2")))
            musicExtroAudio = musicExtroSpecific[4];
        else if(prevSong.equals(Uri.parse(uriPath+"gnrsong3")))
            musicExtroAudio = musicExtroSpecific[5];
        else if(prevSong.equals(Uri.parse(uriPath+"gnrsong4")))
            musicExtroAudio = musicExtroSpecific[9];
        else if(prevSong.equals(Uri.parse(uriPath+"gnrsong5")))
            musicExtroAudio = musicExtroSpecific[0];
        else if(prevSong.equals(Uri.parse(uriPath+"gnrsong6")))
            musicExtroAudio = musicExtroSpecific[2];
        else if(prevSong.equals(Uri.parse(uriPath+"gnrsong7")))
            musicExtroAudio = musicExtroSpecific[8];
        else if(prevSong.equals(Uri.parse(uriPath+"gnrsong16")))
            musicExtroAudio = musicExtroSpecific[11];
        else if(prevSong.equals(Uri.parse(uriPath+"gnrsong17")))
            musicExtroAudio = musicExtroSpecific[6];
        else if(prevSong.equals(Uri.parse(uriPath+"gnrsong18")))
            musicExtroAudio = musicExtroSpecific[1];
        else if(prevSong.equals(Uri.parse(uriPath+"gnrsong19")))
            musicExtroAudio = musicExtroSpecific[7];
        else if(prevSong.equals(Uri.parse(uriPath+"gnrsong20")))
            musicExtroAudio = musicExtroSpecific[10];
        else {
            int chosen = wamR.WAMR(radioHello);

            wamR.alterPriority(radioHello, chosen);
            musicExtroAudio = radioHello[chosen].getSong();
        }

        return musicExtroAudio;
    }

    private Uri setTheater()
    {
        Uri theaterAudio;
        if(theaterSequence[2] == 0) {
            theaterAudio = Uri.parse(uriPath+"theater_intro");
            theaterSequence[1] = 0;
            theaterSequence[2] = radioTheater.get(theaterSequence[0]).length;
        }
        else {
            theaterAudio = radioTheater.get(theaterSequence[0])[theaterSequence[1]];
            theaterSequence[1]++;
        }

        return theaterAudio;
    }

    public Uri setNewsLink()
    {
        int chosen = wamR.WAMR(newsLink);
        wamR.alterPriority(newsLink, chosen);

        return newsLink[chosen].getSong();
    }

    public Uri setNewsStory()
    {
        if(lastPlayed[2] == 0) {
            int chosen = wamR.WAMR(newsStories);
            wamR.alterPriority(newsStories, chosen);
            lastPlayed[0] = chosen;
            lastPlayed[1] = 0;
            lastPlayed[2] = newsStories.get(lastPlayed[0]).length;
        }

        Uri newsStoryAudio = newsStories.get(lastPlayed[0])[lastPlayed[1]].getSong();
        lastPlayed[1]++;

        return newsStoryAudio;
    }

    public Uri setNewsPost()
    {
        return newsPost[r.nextInt(newsPost.length)];
    }

    public Uri setPSAIntro()
    {
        int chosen = wamR.WAMR(psaIntro);
        wamR.alterPriority(psaIntro, chosen);

        return psaIntro[chosen].getSong();
    }

    public Uri setPSAInfo()
    {
        if(lastPlayed[2] == 0) {
            int chosen = wamR.WAMR(psaInfos);
            wamR.alterPriority(psaInfos, chosen);
            lastPlayed[0] = chosen;
            lastPlayed[1] = 0;
            lastPlayed[2] = psaInfos.get(lastPlayed[0]).length;
        }

        Uri psaInfoAudio = psaInfos.get(lastPlayed[0])[lastPlayed[1]].getSong();
        lastPlayed[1]++;

        return psaInfoAudio;
    }

    private Uri setMusicIntro()
    {
        Uri musicIntroAudio;

        if(nextSong.equals(Uri.parse(uriPath+"gnrsong1")))
            musicIntroAudio = musicIntroSpecific[3];
        else if(nextSong.equals(Uri.parse(uriPath+"gnrsong2")))
            musicIntroAudio = musicIntroSpecific[4];
        else if(nextSong.equals(Uri.parse(uriPath+"gnrsong3")))
            musicIntroAudio = musicIntroSpecific[5];
        else if(nextSong.equals(Uri.parse(uriPath+"gnrsong4")))
            musicIntroAudio = musicIntroSpecific[9];
        else if(nextSong.equals(Uri.parse(uriPath+"gnrsong5")))
            musicIntroAudio = musicIntroSpecific[0];
        else if(nextSong.equals(Uri.parse(uriPath+"gnrsong6")))
            musicIntroAudio = musicIntroSpecific[2];
        else if(nextSong.equals(Uri.parse(uriPath+"gnrsong7")))
            musicIntroAudio = musicIntroSpecific[8];
        else if(nextSong.equals(Uri.parse(uriPath+"gnrsong16")))
            musicIntroAudio = musicIntroSpecific[11];
        else if(nextSong.equals(Uri.parse(uriPath+"gnrsong17")))
            musicIntroAudio = musicIntroSpecific[6];
        else if(nextSong.equals(Uri.parse(uriPath+"gnrsong18")))
            musicIntroAudio = musicIntroSpecific[1];
        else if(nextSong.equals(Uri.parse(uriPath+"gnrsong19")))
            musicIntroAudio = musicIntroSpecific[7];
        else if(nextSong.equals(Uri.parse(uriPath+"gnrsong20")))
            musicIntroAudio = musicIntroSpecific[10];
        else
            musicIntroAudio = musicIntroGeneric[r.nextInt(musicIntroGeneric.length)];

        return musicIntroAudio;
    }

    public void addToNews(Uri[] news)
    {
        Song temp[] = new Song[news.length];

        for(int i=0; i < news.length; i++)
        {
            temp[i] = new Song(news[i]);
        }
        this.newsStories.add(temp);

        System.out.println("After add, News Stories has:");
        for(int i=0;i<newsStories.size();i++)
            System.out.println(newsStories.get(i)[0].getSong().getPath());
    }

    public void removeFromNews(Uri uriFlag)
    {
        int search = 0;

        for(int i=0;search<newsStories.size();i++)
            if (newsStories.get(i)[0].getSong().equals(uriFlag)) {
                search = i;
                break;
            }

        newsStories.remove(search);

        System.out.println("After remove, News Stories has:");
        for(int i=0;i<newsStories.size();i++)
            System.out.println(newsStories.get(i)[0].getSong().getPath());
    }
}

//TODO: getfunctions so onClose can easily get data