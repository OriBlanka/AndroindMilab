package com.HowIMetYourMother.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ActorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actors);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.actorsList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }
}