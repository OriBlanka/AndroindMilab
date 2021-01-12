package com.stockquotes;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FetchStockPrice {
    private RequestQueue _queue;
    private static final String TAG = "StockPriceFetcher";
    private final static String REQUEST_URL = "http://10.0.0.38:8080/stocks";


    public class PriceResponse {
        public boolean isError;
        public String symbol;
        public String price;

        public PriceResponse(boolean isError, String symbol, String price) {
            this.isError = isError;
            this.symbol = symbol;
            this.price = price;
        }
    }

    public interface StockPriceResponseListener {
        public void onResponse(PriceResponse response);
    }

    public FetchStockPrice(Context context) {
        _queue = Volley.newRequestQueue(context);
    }

    private PriceResponse createErrorResponse() {
        PriceResponse priceR = new PriceResponse(true, null, null);
        return priceR;
    }

    public void dispatchRequest(String stockN, String token, StockPriceResponseListener listener) {
        JSONObject getBody = new JSONObject();
        try {
            //Write to the JASON file
            getBody.put("stockName", stockN);
            getBody.put("token", token);
        }

        catch (JSONException e) {
            listener.onResponse(createErrorResponse());
            return; //There is any problem with write to the JASON file - There is no reason to continue

        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, REQUEST_URL, getBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    PriceResponse res = new PriceResponse(false, stockN, response.getJSONObject("Global Quote").getString("05. price"));
                    listener.onResponse(res);
                }
                catch (Exception e) { //(JSONException e) {
                    listener.onResponse(createErrorResponse());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponse(createErrorResponse());
            }
        });
        _queue.add(req);
    }
}
