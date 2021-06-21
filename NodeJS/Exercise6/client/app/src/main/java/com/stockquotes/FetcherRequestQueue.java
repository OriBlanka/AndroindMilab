package com.stockquotes;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class FetcherRequestQueue {
    private static  FetcherRequestQueue fetchInstance;
    private RequestQueue reqQueue;

    /**********************************************************************
        Construct a new request queue
    ***********************************************************************/
    public FetcherRequestQueue(Context context){
        reqQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    /**********************************************************************
        Create instance for this class if there isn't and return it
    ***********************************************************************/
    public static synchronized FetcherRequestQueue getInstance(Context context){
        if(fetchInstance == null){
            fetchInstance = new FetcherRequestQueue(context);
        }
        return fetchInstance;
    }

    /**********************************************************************
        Get request queue
    ***********************************************************************/
    public RequestQueue getQueue(){
        return reqQueue;
    }
}
