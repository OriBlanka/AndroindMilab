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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    static String token = "";

    TextInputEditText stockName;
    TextView stockText;
    TextView stockPriceText;
    String sName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getToken();
        stockText = (TextView)findViewById(R.id.stockNameText);
        stockText.setVisibility(View.INVISIBLE);
        stockPriceText = (TextView)findViewById(R.id.stockPriceText);
        stockPriceText.setVisibility(View.INVISIBLE);
    }

    /**********************************************************************
        Call when clicking on the submitButton (connect then on the xml
        file) and connect to the server in order to fetch the stock
        price
    ***********************************************************************/
    public void fetchStockPrice (View v) {
        Log.d(TAG, "Submit button clicked");
        stockName = (TextInputEditText) findViewById(R.id.symbolEditText);
        sName = stockName.getText().toString();
        FetchStockPrice stockFetcher = new FetchStockPrice(v.getContext());
        Toast toast = Toast.makeText(v.getContext(), "Fetching " + sName + " stock price ...", Toast.LENGTH_LONG);
        toast.show();

        stockFetcher.dispatchRequest(sName, MainActivity.token, new FetchStockPrice.StockPriceResponseListener() {
            @Override
            public void onResponse(FetchStockPrice.PriceResponse response) {
                if (response.isError) {
                    Toast toast = Toast.makeText(v.getContext(), "Error while fetching weather", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
            }
        });
    }

    /**********************************************************************
        Get the token of the firebase instance
    ***********************************************************************/
    public void getToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                //Check if we success to get firebase instance ID
                if (!task.isSuccessful()) {
                    Log.w(TAG, "getInstanceId failed", task.getException());
                    return; //There is a problem when trying to getInstanceId, there is no reason to continue
                }

                // Get new Instance ID token
                token  = task.getResult().getToken();
                // Log
                String msg = "InstanceID Token: " + token;
                Log.d(TAG, msg);
            }
        });
    }

   /**********************************************************************
        Call when the application is resume
   ***********************************************************************/
   public void onResume() {
       super.onResume();
       IntentFilter intentFilter = new IntentFilter();
       intentFilter.addAction("com.stockquotes.onMessageReceived");
       AppBroadcastReceiver receiver = new AppBroadcastReceiver();
       registerReceiver(receiver, intentFilter);
   }

    /**********************************************************************
        When message receive from FCM we want to take it and update the
        edit text accordingly.
    ***********************************************************************/
    private class AppBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            String symbol = extras.getString("symbol");
            Log.d(TAG, "symbol got from firebase: " + symbol);
            String price = extras.getString("price");
            Log.d(TAG, "price got from FireBase: " + price);
            stockText.setVisibility(View.VISIBLE);
            stockPriceText.setVisibility(View.VISIBLE);
            ((TextView)MainActivity.this.findViewById(R.id.stockNameText)).setText(symbol);
            ((TextView)MainActivity.this.findViewById(R.id.stockPriceText)).setText(price + "$");
        }
    }
}