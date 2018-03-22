package com.example.nicholasrocksvold.falloutliveradio;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RadioMenu extends AppCompatActivity {

    private Button mPlayButton;
    private static MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        mPlayButton = (Button) findViewById(R.id.play_button);

        mPlayButton.setText(R.string.play_button);

        final Radio GNR = new Radio(this, "Galaxy News Radio", 1, 20);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while(true)
                {
                    mp = GNR.songs.get(0);
                }

            }
        });
    }
}
