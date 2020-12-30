package com.HowIMetYourMother.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActressesActivity extends AppCompatActivity {

    Button backButton;
    MediaPlayer mPlayer;
    RecyclerView recyclerView;

    String actressesNames[];
    String actressesRealNames[];
    int actressesImages[] = {R.drawable.lily, R.drawable.robin,R.drawable.tracy};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actresses);
        actressesNames = getResources().getStringArray(R.array.actressesNames);
        actressesRealNames = getResources().getStringArray(R.array.actressesRealNames);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        mPlayer = MediaPlayer.create(ActressesActivity.this, R.raw.hey_beautiful);
        mPlayer.start();

        ActorsAdapter actorsAdapter = new ActorsAdapter(this, actressesNames, actressesRealNames, actressesImages);
        recyclerView.setAdapter(actorsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(v.getContext(), MainActivity.class);
                startActivity(backIntent);
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