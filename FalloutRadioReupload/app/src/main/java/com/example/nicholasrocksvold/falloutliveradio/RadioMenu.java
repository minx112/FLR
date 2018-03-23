package com.example.nicholasrocksvold.falloutliveradio;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class RadioMenu extends AppCompatActivity {

    private Button mPlayButton;
    private static MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        final int[] GNRSongs = new int[]{R.raw.song1, R.raw.song2, R.raw.song3, R.raw.song4, R.raw.song5,
                R.raw.song6, R.raw.song7, R.raw.song8, R.raw.song9, R.raw.song10, R.raw.song11,
                R.raw.song12, R.raw.song13, R.raw.song14, R.raw.song15, R.raw.song16, R.raw.song17,
                R.raw.song18, R.raw.song19, R.raw.song20};

                mPlayButton = (Button) findViewById(R.id.play_button);

        mPlayButton.setText(R.string.play_button);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while(true)
                {
                    mp = MediaPlayer.create(getApplicationContext(), GNRSongs[0]);
                    mp.start();
                }

            }
        });
    }
}
