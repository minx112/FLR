package com.example.nicholasrocksvold.falloutliveradio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RadioMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Radio radioGNR = new Radio(this, "GNR");

        class playTheRadio extends AsyncTask<MyTaskParams, Void, Void>{
            @Override
            protected Void doInBackground(MyTaskParams... params){
                radioGNR.playRadio(params[0].song, params[0].flag);

                return null;
            }
        }

        final MediaPlayer mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        final MyTaskParams params = new MyTaskParams(Uri.parse("android.resource://com.example.nicholasrocksvold.falloutliveradio/raw/radio_start"), 0);
        final playTheRadio playRadio = new playTheRadio();


        Button mPlayButton = findViewById(R.id.play_button);
        Button mStopButton = findViewById(R.id.play_button2);

        mPlayButton.setText(R.string.play_button);
        mStopButton.setText("Stop Radio");

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(wanderer.isNotDone())
                playRadio.execute(params);
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

    private static class MyTaskParams {
        Uri song;
        int flag;

        MyTaskParams(Uri foo, int bar) {
            this.song = foo;
            this.flag = bar;
        }
    }
}