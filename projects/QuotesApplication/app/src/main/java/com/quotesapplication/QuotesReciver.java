package com.quotesapplication;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class QuotesReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String quote = MainActivity.quotesArray[(int)(Math.random() * 60)];
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_verified)
                .setContentTitle("Smart Quotes")
                .setContentText(quote)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(quote));

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notifyId = 1;
        notificationManager.notify(notifyId, builder.build());
    }
}