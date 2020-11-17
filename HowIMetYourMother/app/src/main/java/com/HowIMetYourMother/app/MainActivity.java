package com.HowIMetYourMother.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button actorButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actorButton = (Button)findViewById(R.id.actorsButton);
        actorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actorsIntent = new Intent(v.getContext(), ActorsActivity.class);
                startActivity(actorsIntent);
                //openActorsActivity();
            }
        });
    }

    public void openActorsActivity(){
        Intent actorsIntent = new Intent(this.getApplicationContext(), ActorsActivity.class);
        startActivity(actorsIntent);
    }



}