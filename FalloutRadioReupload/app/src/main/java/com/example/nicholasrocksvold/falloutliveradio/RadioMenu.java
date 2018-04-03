package com.example.nicholasrocksvold.falloutliveradio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class RadioMenu extends AppCompatActivity {

    private Button mPlayButton;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mPlayButton = (Button) findViewById(R.id.play_button);

        mPlayButton.setText(R.string.play_button);

        final Radio radioGNR = new Radio(getApplicationContext(), "GNR");
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.radio_start);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(wanderer.isNotDone())
                flag = 0;
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mPlayer) {
                        radioGNR.playRadio(flag);
                        flag = (flag + 1) % 3; }});
                /*
                else
                    check wanderer karma and play ending
                 */
            }
        });
    }

}