package com.proj.androidexam;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CountriesApiService {
    public static final String QUERY_FOR_ALL_COUNTRIES = "https://restcountries.com/v3.1/all";
    public Context context;
    public String selectedRegion;

    public CountriesApiService(Context context) {
        this.context = context;
    }

    public interface ResponseListener {
        void onResponse(List<String> list);

        void onError(String message);
    }

    public void getRegions(ResponseListener listener) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, QUERY_FOR_ALL_COUNTRIES, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<String> regions = extractedRegionsFromResponse(response);
                listener.onResponse(regions);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.getMessage());
            }
        });
        CountriesRequestQueue.getInstance(context).addToRequestQueue(request);
    }

    public void getCountries(String region, ResponseListener listener) {
        selectedRegion = region;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, QUERY_FOR_ALL_COUNTRIES, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<String> countries = extractedCountriesFromResponse(response);
                listener.onResponse(countries);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.getMessage());
            }
        });
        CountriesRequestQueue.getInstance(context).addToRequestQueue(request);
    }

    private List<String> extractedRegionsFromResponse(JSONArray response) {
        Set<String> extractedRegions = new TreeSet<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                extractedRegions.add(response.getJSONObject(i).getString("region"));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(extractedRegions);
    }

    private List<String> extractedCountriesFromResponse(JSONArray response) {
        Set<String> extractedCountries = new TreeSet<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject country = response.getJSONObject(i);
                if (country.getString("region").equals(selectedRegion)) {
                    extractedCountries.add(country.getJSONObject("name").getString("common"));
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(extractedCountries);
    }
}
