package com.HowIMetYourMother.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ActorsActivity extends AppCompatActivity {

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

        ActorsAdapter actorsAdapter = new ActorsAdapter(this, actorsNames, actorsRealNames, actorsImages);
        recyclerView.setAdapter(actorsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}