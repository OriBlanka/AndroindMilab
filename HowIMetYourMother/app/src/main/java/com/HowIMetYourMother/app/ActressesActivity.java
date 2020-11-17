package com.HowIMetYourMother.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ActressesActivity extends AppCompatActivity {

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

        ActorsAdapter actorsAdapter = new ActorsAdapter(this, actressesNames, actressesRealNames, actressesImages);
        recyclerView.setAdapter(actorsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}