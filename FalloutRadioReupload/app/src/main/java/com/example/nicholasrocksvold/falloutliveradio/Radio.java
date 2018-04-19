package com.example.nicholasrocksvold.falloutliveradio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import java.util.Random;
import java.util.ArrayList;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import com.example.nicholasrocksvold.falloutliveradio.QuestDB;
import com.example.nicholasrocksvold.falloutliveradio.QuestDBHelper;



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

    private static Uri prevSong;
    private static Uri nextSong;
    private static Uri audio;
    private String radioName;
    private long startTime;
    private static int flag;
    private int[] lastPlayed = new int[]{0,0,0,0}; //0=story, 1=track, 2=maxTrack, 3=middle of story

    String uriPath = "android.resource://com.example.nicholasrocksvold.falloutliveradio/raw/";

    private Context mContext;
    private SQLiteDatabase mDatabase;

    Radio(Context current, String radioName)
    {
        this.radioName = radioName; //establishes radio name
        this.mContext = current;
        this.startTime =  System.currentTimeMillis();

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
                    new Song(Uri.parse(uriPath+"forecast"))});

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
        }
        else
            System.out.println("Input a correct radio name in code, please.");
    }

    public void playRadio(MediaPlayer mp)
    {
        nextSong =  Uri.parse("android.resource://com.example.nicholasrocksvold.falloutliveradio/raw/radio_start");//start static
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
            startTime = System.currentTimeMillis(); //fetch starting time
            //play music for 5 minutes
            while ((System.currentTimeMillis() - startTime) < 300000) {
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        try {
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
            //play news
            flag = 0;
            while(flag != -1)
            {
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {

                        if(flag == 0) //set music extro
                        {
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

                        if(flag == 1)//set news link
                        {
                            flag++;
                            audio = setNewsLink();
                        }
                        else if(flag == 2)//set news story
                        {
                            audio = setNewsStory();

                            if(lastPlayed[1] == lastPlayed[2])//story is done
                            {
                                flag++;
                                for (int i = 0; i < lastPlayed.length; i++)
                                    lastPlayed[i] = 0;
                            }
                        }
                        else if(flag == 3)//set news post
                        {
                            flag++;
                            audio = setNewsPost();
                        }
                        else if(flag == 4)//set psa intro
                        {
                            flag++;
                            audio = setPSAIntro();
                        }
                        else if(flag == 5)//set psa info
                        {
                            audio = setPSAInfo();

                            if(lastPlayed[1] == lastPlayed[2])//PSA is done
                            {
                                flag++;
                                for (int i = 0; i < lastPlayed.length; i++)
                                    lastPlayed[i] = 0;
                            }
                        }
                        else if(flag == 6)//set music intro
                        {
                            flag++;
                            audio = setMusicIntro();
                        }
                        else
                            flag = -1;//news is done
                    }
                });
            }
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

    public Uri setMusicExtro() {
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
            lastPlayed[3]++;
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
            lastPlayed[3]++;
        }

        Uri psaInfoAudio = psaInfos.get(lastPlayed[0])[lastPlayed[1]].getSong();
        lastPlayed[1]++;

        return psaInfoAudio;
    }

    public Uri setMusicIntro()
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
/*
    public void addDB(Quest q) {
        ContentValues values = getContentValues(q);
        mDatabase.insert(QuestDB.QuestTable.NAME, null, values);
    }
    private static ContentValues getContentValues(Quest quest) {
        ContentValues values = new ContentValues();
        values.put(QuestDB.QuestTable.Cols.timeClosed, Quest.timeClosed);
        values.put(QuestDB.QuestTable.Cols.questsDone, Quest.questsDone);
        values.put(QuestDB.QuestTable.Cols.currentQuestTime, Quest.currentQuestTime);
        values.put(QuestDB.QuestTable.Cols.distances,Quest.distances);
    }
*/
}
