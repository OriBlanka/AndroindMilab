package com.HowIMetYourMother.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActorsActivity extends AppCompatActivity {

    Button backButton;
    MediaPlayer mPlayer;
    RecyclerView recyclerView;

    String actorsNames[];
    String actorsRealNames[];
    int actorsImages[] = {R.drawable.marshall, R.drawable.ted,R.drawable.barney};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actors);

        actorsNames = getResources().getStringArray(R.array.actorsNames);
        actorsRealNames = getResources().getStringArray(R.array.actorsRealNames);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        mPlayer = MediaPlayer.create(ActorsActivity.this, R.raw.hey_beautiful);
        mPlayer.start();

        ActorsAdapter actorsAdapter = new ActorsAdapter(this, actorsNames, actorsRealNames, actorsImages);
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