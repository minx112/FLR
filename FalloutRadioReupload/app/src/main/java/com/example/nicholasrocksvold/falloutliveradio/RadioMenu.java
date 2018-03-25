package com.example.nicholasrocksvold.falloutliveradio;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class RadioMenu extends AppCompatActivity {

    private Button mPlayButton;
    private static MediaPlayer mp;
    public int currentTrack = 0;
    public int currentStory = 0;
    public int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        final int[] GNRSongs = new int[]{R.raw.song1, R.raw.song2, R.raw.song3, R.raw.song4, R.raw.song5,
                R.raw.song6, R.raw.song7, R.raw.song8, R.raw.song9, R.raw.song10, R.raw.song11,
                R.raw.song12, R.raw.song13, R.raw.song14, R.raw.song15, R.raw.song16, R.raw.song17,
                R.raw.song18, R.raw.song19, R.raw.song20};

        final int[] GNRNews = new int[] {R.raw.story1, R.raw.story2, R.raw.story3, R.raw.story4,
                R.raw.story5, R.raw.story6, R.raw.story7, R.raw. story8, R.raw.story9, R.raw.story10};

        mPlayButton = (Button) findViewById(R.id.play_button);

        mPlayButton.setText(R.string.play_button);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random r = new Random();
                int randSong = r.nextInt(GNRSongs.length);
                int randNews = r.nextInt(GNRNews.length);

                System.out.println("Initialized randSong: " + randSong);
                System.out.println("Initialized randNews: " + randNews);

                mp = MediaPlayer.create(getApplicationContext(), GNRSongs[randSong]);
                while(true)
                {
                    if(!mp.isPlaying())
                    {
                        if(flag == 2){
                            mp = MediaPlayer.create(getApplicationContext(), GNRNews[randNews]);
                            randNews = r.nextInt(GNRNews.length);
                            System.out.println("Next randNews: " + randNews);
                        }
                        else {
                            mp = MediaPlayer.create(getApplicationContext(), GNRSongs[randSong]);
                            randSong = r.nextInt(GNRSongs.length);
                            System.out.println("Next randSong: " + randSong);
                        }
                        mp.start();
                        flag = (flag+1)%3;
                    }
                }

            }
        });
    }
}
