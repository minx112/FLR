package com.example.nicholasrocksvold.falloutliveradio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RadioMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Radio radioGNR = new Radio(this, "GNR");
        final Wanderer wanderer= new Wanderer(radioGNR);

        class startTheWanderer extends AsyncTask<Radio, Void, Void>{
            @Override
            protected Void doInBackground(Radio... params){

                wanderer.start();

                return null;
            }
        }

        class playTheRadio extends AsyncTask<MediaPlayer, Void, Void>{
            @Override
            protected Void doInBackground(MediaPlayer... params){

                radioGNR.playRadio(params[0]);

                return null;
            }
        }

        final MediaPlayer mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        final playTheRadio playRadio = new playTheRadio();
        final startTheWanderer startWanderer = new startTheWanderer();
        startWanderer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        Button mPlayButton = findViewById(R.id.play_button);
        Button mStopButton = findViewById(R.id.play_button2);

        mPlayButton.setText(R.string.play_button);
        mStopButton.setText("Stop Radio");

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(wanderer.isNotDone())
                playRadio.execute(mp);
                /*
                else
                    check wanderer karma and play ending
                 */
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hit the stop button");
                mp.release();
            }
        });
    }
    public void addDB(Quest q) {
        ContentValues values = getContentValues(q);
        mDatabase.insert(QuestDB.QuestTable.NAME, null, values);
    }
    /*
    private int karma;
    private boolean gender; //true=male false=female
    private boolean isNotDone;
    private String currentPlace;
    Radio radio;
    private Random random = new Random();
    private Calendar startTime;
    private Calendar timeElapsed;
    private long timeModifier;

    private ArrayList<Quest> availableQuests = new ArrayList<>();
    private Quest toBeDoneQuests[] = new Quest[2];
    private Quest currentQuest;

    private String uriPath = "android.resource://com.example.nicholasrocksvold.falloutliveradio/raw/";

     */
    private static ContentValues getContentValues(Quest quest, Wanderer wanderer) {
        // usage?? and why wont the get functions register
        ContentValues values = new ContentValues();
        //quest = new Quest();
        values.put(QuestDB.QuestTable.Cols.timeClosed, wanderer.???????????.toString()); // expects key value pair
        values.put(QuestDB.QuestTable.Cols.questsDone, wanderer.?????????().toString());//
        values.put(QuestDB.QuestTable.Cols.currentQuestTime.toString(), wanderer.getTimeElapsed().toString());
        values.put(QuestDB.QuestTable.Cols.gender, wanderer.getGender().toString());
        values.put(QuestDB.QuestTable.Cols.lastTheater, redioGNR.?????);
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

    public void questsAvailable() {
        //a bunch of if statements
    }

    //methods converting variables

}