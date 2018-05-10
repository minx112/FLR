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
    private ArrayList<Uri[]> radioTheater = new ArrayList<>();

    private static Uri prevSong;
    private static Uri nextSong;
    private static Uri audio;
    private String radioName;
    private long startTime;
    private static int flag;
    private int[] lastPlayed = new int[]{0,0,0}; //0=story, 1=track, 2=maxTrack
    private int[] theaterSequence = new int[]{0,-1,0}; //0=part, 1=track, 2=maxTrack

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
                System.out.println("Flag: "+flag);
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
<<<<<<< HEAD
<<<<<<< HEAD
=======
        public static final String currentQuest = "currentquest";
            public static final String timeClosed = "timeclosed";// date convert to date
            public static final String questsDone = "questsdone";// make string, seperate "ints" by coma, parse string to get sub strings of quests done
            public static final Integer currentQuestTime = 0; // keep track in miliseconds
            public static final String gender = "gender";
            public static final String lastTheater = "lasttheater";
            public static final Integer currentQuestLength = 0;
*/
public void addDB(Quest q) {
    ContentValues values = getContentValues(q);
    mDatabase.insert(QuestDB.QuestTable.NAME, null, values);
}
    private static ContentValues getContentValues(Quest quest, Wanderer wanderer) {
        // usage?? and why wont the get functions register
        ContentValues values = new ContentValues();
        //quest = new Quest();
        values.put(QuestDB.QuestTable.Cols.timeClosed, quest.getTimeClosed().toString()); // expects key value pair
        values.put(QuestDB.QuestTable.Cols.questsDone, quest.getQuestsDone().toString());//
        values.put(QuestDB.QuestTable.Cols.currentQuestTime.toString(), quest.getCurrentQuestTime().toString());
        values.put(QuestDB.QuestTable.Cols.gender, wanderer.getGender);
        values.put(QuestDB.QuestTable.Cols.lastTheater, ??????);
        values.put(QuestDB.QuestTable.Cols.currentQuestLength, ?????);
        return values;
    }
    private QuestCurserWrapper queryQuests(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(QuestDB.QuestTable.NAME,null,whereClause,whereArgs,null,null,null);
        return new QuestCurserWrapper(cursor);
    }
    public Quest getQuest(String currentQuest) {
        QuestCurserWrapper cursor = queryQuests(QuestDB.QuestTable.Cols.currentQuest + " = ?"
                , new String[] {currentQuest});
        try {
            if(cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getQuest();
        } finally {
            cursor.close();
        }
    }
    //method to parse the string quests done
    public ArrayList<Integer> parseQuestsDone(String qd) {
        //load String from database

        ArrayList<Integer> questsDone = new ArrayList<>();
        String [] tokens = qd.split(",");
        for(String t : tokens ) {
            questsDone.add(Integer.parseInt(t));
        }
    }
<<<<<<< HEAD
>>>>>>> parent of fd28234... Merge branch 'master' of https://github.com/minx112/Fallout-Live-radio
=======
<<<<<<< HEAD
        public static final String currentQuest = "currentquest";
            public static final String timeClosed = "timeclosed";// date convert to date
            public static final String questsDone = "questsdone";// make string, seperate "ints" by coma, parse string to get sub strings of quests done
            public static final Integer currentQuestTime = 0; // keep track in miliseconds
            public static final String gender = "gender";
            public static final String lastTheater = "lasttheater";
            public static final Integer currentQuestLength = 0;
*/
public void addDB(Quest q) {
    ContentValues values = getContentValues(q);
    mDatabase.insert(QuestDB.QuestTable.NAME, null, values);
}
    private static ContentValues getContentValues(Quest quest, Wanderer wanderer) {
        // usage?? and why wont the get functions register
        ContentValues values = new ContentValues();
        //quest = new Quest();
        values.put(QuestDB.QuestTable.Cols.timeClosed, quest.getTimeClosed().toString()); // expects key value pair
        values.put(QuestDB.QuestTable.Cols.questsDone, quest.getQuestsDone().toString());//
        values.put(QuestDB.QuestTable.Cols.currentQuestTime.toString(), quest.getCurrentQuestTime().toString());
        values.put(QuestDB.QuestTable.Cols.gender, wanderer.getGender);
        values.put(QuestDB.QuestTable.Cols.lastTheater, ??????);
        values.put(QuestDB.QuestTable.Cols.currentQuestLength, ?????);
        return values;
    }
    private QuestCurserWrapper queryQuests(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(QuestDB.QuestTable.NAME,null,whereClause,whereArgs,null,null,null);
        return new QuestCurserWrapper(cursor);
    }
    public Quest getQuest(String currentQuest) {
        QuestCurserWrapper cursor = queryQuests(QuestDB.QuestTable.Cols.currentQuest + " = ?"
                , new String[] {currentQuest});
        try {
            if(cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getQuest();
        } finally {
            cursor.close();
        }
    }
    //method to parse the string quests done
    public ArrayList<Integer> parseQuestsDone(String qd) {
        //load String from database

        ArrayList<Integer> questsDone = new ArrayList<>();
        String [] tokens = qd.split(",");
        for(String t : tokens ) {
            questsDone.add(Integer.parseInt(t));
        }
    }
>>>>>>> parent of 97b722a... newest

    public void questsAvailable() {
        //a bunch of if statements
    }

    //method to parse throu distances
    //method to find out if a quest is done? save it in quest object

}
<<<<<<< HEAD
<<<<<<< HEAD

=======
>>>>>>> parent of fd28234... Merge branch 'master' of https://github.com/minx112/Fallout-Live-radio
=======
*/
>>>>>>> parent of bc22710... stuff
=======
=======
>>>>>>> aa49f8587d8359c914161dad0e8dd54f54b40b86
>>>>>>> parent of 97b722a... newest
}
