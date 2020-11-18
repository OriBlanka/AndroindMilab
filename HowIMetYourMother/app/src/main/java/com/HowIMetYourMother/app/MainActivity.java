package com.HowIMetYourMother.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    private Button actorButton;
    private Button actressesButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayer = MediaPlayer.create(MainActivity.this, R.raw.hey_beautiful);
        mPlayer.start();

        actorButton = (Button)findViewById(R.id.actorsButton);
        actorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actorsIntent = new Intent(v.getContext(), ActorsActivity.class);
                startActivity(actorsIntent);
            }
        });

        actressesButton = (Button)findViewById(R.id.actressButton);
        actressesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actorsIntent = new Intent(v.getContext(), ActressesActivity.class);
                startActivity(actorsIntent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayer.release();
        finish();
    }

}