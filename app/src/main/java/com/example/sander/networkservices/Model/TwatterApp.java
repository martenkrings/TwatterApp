package com.example.sander.networkservices.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sander on 7-6-2016.
 */
public class TwatterApp {
    private static final String TAG = "TwatterApp";
    private static TwatterApp instance;
    private static final String API_key = "VeBNqm0wZy8iaXQ1frmUZZvBM";
    private static final String API_secret = "nOicddrX7CV0UwLVpxpTOAlzWAo3bJf2b1CL9vF4Lx9Mc5p9Uz";
    private String bearerToken = null;
    private User ingelogteUser = null;
    private ArrayList<Tweet> searchResults;

    private TwatterApp (){
        searchResults = new ArrayList<>();
    }

    public static TwatterApp getInstance(){
        if (instance == null){
            Log.d(TAG, "Instance == null");
            instance = new TwatterApp();
        }
        return instance;
    }

    public static String getAPI_key() {
        return API_key;
    }

    public static String getAPI_secret() {
        return API_secret;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public User getIngelogteUser() {
        return ingelogteUser;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public void setIngelogteUser(User ingelogteUser) {
        this.ingelogteUser = ingelogteUser;
    }

    public void addSearchResults(JSONArray jsonObjects){
        Log.d(TAG, "start of addSearchresults");
        try {
            for (Tweet tweet: searchResults){
                searchResults.remove(tweet);
            }
            Log.d(TAG, "past deleting");
            for (int i = 0; i < jsonObjects.length(); i++) {
                Tweet tweetAdded = new Tweet(jsonObjects.getJSONObject(i));
                Log.d(TAG, tweetAdded.toString());
                searchResults.add(tweetAdded);
            }
            Log.d(TAG, "Tweets: " + searchResults.toString());
        } catch (JSONException e) {
            Log.d(TAG, "JSONException: " + e.getMessage());
        }
    }

    public ArrayList<Tweet> getSearchResults() {
        return searchResults;
    }
}
