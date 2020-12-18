package com.quotesapplication;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.annotation.Nullable;

/**
 * A subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class QuotesIntentService extends IntentService {

    public static final String NOTIFY = "com.quotesapplication.notify";

    public QuotesIntentService() {
        super("QuotesIntentService");
        setIntentRedelivery(true);
    }


    /**********************************************************************
         Try to run handleActionNotify function and throw exception
         if there is any problem with the file
     **********************************************************************/
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                handleActionNotify();
            } catch (PendingIntent.CanceledException exception){
                exception.printStackTrace();
            }
        }
    }


    /**********************************************************************
        Run the reciver class and use AlarmManger in order to pop
        a notification every five minutes.
     **********************************************************************/
    private void handleActionNotify() throws PendingIntent.CanceledException {
        //compute 5 minutes in milliseconds
        long alarmRepeatingTime = 1000 * 60 * 5;

        //Start to running the receiver class
        Intent intent = new Intent(this, QuotesReciver.class);
        intent.setAction(NOTIFY);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        pendingIntent.send();

        AlarmManager quotesAlarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        //ELAPSED_REALTIME_WAKEUP -> time since boot, including sleep
        //SystemClock.elapsedRealtime() -> returns milliseconds since boot, including time spent in sleep.
        quotesAlarm.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), alarmRepeatingTime, pendingIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}