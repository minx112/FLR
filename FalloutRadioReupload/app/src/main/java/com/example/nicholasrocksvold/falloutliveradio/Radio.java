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
    private String radioName;
    private int[] lastPlayed = new int[]{0,0,0,0}; //0=story, 1=track, 2=maxTrack, 3=middle of story

    String uriPath = "android.resource://com.example.nicholasrocksvold.falloutliveradio/raw/";

    private Context mContext;
    private SQLiteDatabase mDatabase;

    Radio(Context current, String radioName)
    {
        this.radioName = radioName; //establishes radio name
        this.mContext = current;

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

    public void playRadio(final Uri audio, int passedFlag)
    {
        final int flag = passedFlag + 1;

        MediaPlayer mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mp.setDataSource(mContext, audio);
            mp.prepare();
            mp.start();

            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    int chosenSong = wamR.WAMR(radioSongs);
                    prevSong = nextSong;
                    nextSong = radioSongs[chosenSong].getSong();
                    wamR.alterPriority(radioSongs, chosenSong);

                    if (flag == 2)
                        playGNRNews(nextSong, 0);
                    else
                        playRadio(nextSong, flag);

                    mp.release();
                }
            });
        }catch(java.io.IOException e){
            System.out.println("Playing song error: (Uri)"+nextSong);
        }
    }

    private void playGNRNews(Uri audio, final int flag)
    {
        MediaPlayer mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

        if(flag == 0)
        {
            int chosenSong = wamR.WAMR(radioSongs);
            prevSong = nextSong;
            nextSong = radioSongs[chosenSong].getSong();
            wamR.alterPriority(radioSongs, chosenSong);
        }

        try {
            mp.setDataSource(mContext, audio);
            mp.prepare();
            mp.start();

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {

                if(flag == 0)
                    MusicExtro(flag);
                else if(flag == 1)
                    newsLink(flag);
                else if(flag == 2 )
                    newsStory(flag);
                else if(flag == 3)
                    newsPost(flag);
                //TODO:Add possibility of radio theater
                //TODO:Add possibility of karma news
                else if(flag == 7)
                    psaIntro(flag);
                else if(flag == 8)
                    psaInfo(flag);
                else if(flag == 9)
                    musicIntro(flag);
                else
                    playRadio(nextSong, 0);

                mp.release();
            }
        });
        }catch(java.io.IOException e){
            System.out.println("Playing news error: (Uri)"+nextSong);
        }
    }

    private void MusicExtro(int flag) {
        Uri musicExtroAudio;
        flag++;

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

        playGNRNews(musicExtroAudio, flag);
    }

    private void newsLink(int flag)
    {
        flag++;
        int chosen = wamR.WAMR(newsLink);
        wamR.alterPriority(newsLink, chosen);

        playGNRNews(newsLink[chosen].getSong(), flag);
    }

    private void newsStory(int flag)
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

        if(lastPlayed[1] == lastPlayed[2]) {
            flag++;
            for (int i = 0; i < lastPlayed.length; i++)
                lastPlayed[i] = 0;
        }

        playGNRNews(newsStoryAudio,flag);
    }

    private void newsPost(int flag)
    {
        flag += 4; //jump to psaIntro
        playGNRNews(newsPost[r.nextInt(newsPost.length)], flag);
    }

    private void psaIntro(int flag)
    {
        flag++;
        int chosen = wamR.WAMR(psaIntro);
        wamR.alterPriority(psaIntro, chosen);

        playGNRNews(psaIntro[chosen].getSong(), flag);
    }

    private void psaInfo(int flag)
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

        if(lastPlayed[1] == lastPlayed[2]) {
            flag++;
            for (int i = 0; i < lastPlayed.length; i++)
                lastPlayed[i] = 0;
        }

        playGNRNews(psaInfoAudio,flag);
    }

    private void musicIntro(int flag)
    {
        Uri musicIntroAudio;
        flag++;

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

        playGNRNews(musicIntroAudio, flag);
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
