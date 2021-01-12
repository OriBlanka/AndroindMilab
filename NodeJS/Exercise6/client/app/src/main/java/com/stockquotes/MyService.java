package com.stockquotes;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class MyService extends FirebaseMessagingService  {

    private static final String TAG = "MyFirebaseMsgService";
    private static final String SERVER_ADDRESS = "http://10.0.0.38:8080";
    private static final String USERNAME = "username";

    static String CHANNEL_ID = "CHANNEL";
    static int notifyId = 1;

    private RequestQueue _queue;


    @Override
    public void onCreate() {
        super.onCreate();
        _queue = Volley.newRequestQueue(this);
    }

    /**********************************************************************
        Called if InstanceID token is updated. This may occur if the
        security of the previous token had been compromised. Note that
        this is called when the InstanceID token is initially generated
        so this is where you would retrieve the token.
    ***********************************************************************/
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    /**********************************************************************
        Persist token to third-party servers.
        Modify this method to associate the user's FCM InstanceID token
        with any server-side account maintained by your application.
    ***********************************************************************/
    private void sendRegistrationToServer(String token) {
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("token", token);
        }
        catch (JSONException e) {}

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, SERVER_ADDRESS + USERNAME + "/token", requestObject,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i(TAG, "Token saved successfully");
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Failed to save token - " + error);
                }
            });

        _queue.add(req);
    }

    /**********************************************************************
        Called when message is received.
    ***********************************************************************/
    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        Intent intent = new Intent();
        //write the relevant values into intent object
        intent.putExtra("symbol", remoteMessage.getData().get("symbol"));
        intent.putExtra("price", remoteMessage.getData().get("price"));
        intent.setAction("com.stockquotes.onMessageReceived");
        sendBroadcast(intent);

        //Push notification with the information that sent from firebase
        sendNotification(remoteMessage.getNotification().getBody());
    }

    /**********************************************************************
        Create and show notification with the information that sent from
        firebase
    ***********************************************************************/
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.dollar)
                        .setContentTitle("Stock Price")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d(TAG, "Sent notification with id " + notifyId);
        notificationManager.notify(notifyId, builder.build());
        notifyId = notifyId + 1;
    }

    /**********************************************************************
        From some API version we need to create a notification channel
        and throw exception if there is any problem with the file
    ***********************************************************************/
    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Ex6_channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}