package com.quotesapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    Button btNotification;
    Button btTimingNotification;
    static String CHANNEL_ID = "CHANNEL";
    public static String[] quotesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Call to createQuotesArray and throw exception if there is any problem with the file
        try {
            createQuotesArray();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        createNotificationChannel();

        //OnClick function for immediate quote notification
        btNotification = (Button)findViewById(R.id.quotesButtonNow);
        btNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quote = quotesArray[(int)(Math.random() * 60)];
                NotificationCompat.Builder builder = new NotificationCompat.Builder(view.getContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_verified)
                        .setContentTitle("Smart Quotes")
                        .setContentText(quote)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(quote));

                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                int notifyId = 1;
                notificationManager.notify(notifyId, builder.build());
            }
        });


        //OnClick function for timing quote notifications
        btTimingNotification = (Button)findViewById(R.id.timingQuotesButton);
        btTimingNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qIntent = new Intent (view.getContext(), QuotesIntentService.class);
                startService(qIntent);
            }
        });
    }

    /**********************************************************************
        From some API version we need to create a notification channel
        and throw exception if there is any problem with the file
    **********************************************************************/
    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Ex3_channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**********************************************************************
        Create an array that contains a quotes that read from a txt file
     **********************************************************************/
    private void createQuotesArray() throws IOException {
        quotesArray = new String[60];
        InputStream file = this.getResources().openRawResource(R.raw.quotes);
        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        for (int i = 0; i < 60; i++) {
            quotesArray[i] = br.readLine();
        }
        br.close();
    }
}