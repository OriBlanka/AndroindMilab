package com.stockquotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.installations.FirebaseInstallations;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private static final String SERVER_ADDRESS = "http://10.0.0.21:8080/";

    TextInputEditText stockName;
    Button submitButton;
    String sName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitButton = (Button)findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Submit button clicked");
                stockName = (TextInputEditText) findViewById(R.id.symbolEditText);
                sName = stockName.getText().toString();
                Toast toast = Toast.makeText(v.getContext(), "Fetching " + sName + " stock price ...", Toast.LENGTH_LONG);
                toast.show();
                sendToServer(sName);
            }
        });
    }

    /**********************************************************************
        Call when clicking on the submitButton and connect to the server
        in order to fetch the stock price
    ***********************************************************************/
    public void fetchStockPrice (String stockName, String token) {
        JSONObject reqObject = new JSONObject();
        try {
            reqObject.put("token", token);
        } catch (JSONException e) {
            return; //There is a problem when trying to getInstanceId, there is no reason to continue
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, SERVER_ADDRESS +
                "stocks/" + stockName,
                reqObject, response -> Log.i("sendToServer", "Symbol updated successfully"),
                error -> Log.e("sendToServer", "Failed to save symbol - " + error)
        );

        //New request - need to adding it into the request queue
        FetcherRequestQueue.getInstance(this).getQueue().add(req);
    }

    /**********************************************************************
        Send a stock's name to the server
    ***********************************************************************/
    private void sendToServer(String stockName){
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                fetchStockPrice(stockName, token);
            }
        });
    }
}