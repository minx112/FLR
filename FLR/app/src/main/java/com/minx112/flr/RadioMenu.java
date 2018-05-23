package com.minx112.flr;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RadioMenu extends AppCompatActivity{
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
}
