package com.proj.androidexam;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class CountriesRequestQueue {
    private static Context context;
    private static CountriesRequestQueue instance;
    private RequestQueue requestQueue;

    private CountriesRequestQueue(Context context) {
        CountriesRequestQueue.context = context;
        this.requestQueue = getRequestQueue();
    }

    public static synchronized CountriesRequestQueue getInstance(Context context) {
        if (instance == null) {
            instance = new CountriesRequestQueue(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
