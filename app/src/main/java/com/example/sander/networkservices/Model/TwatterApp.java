package com.example.sander.networkservices.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;


import java.util.ArrayList;

/**
 * Created by Sander on 7-6-2016.
 */
public class TwatterApp {
    private static final String TAG = "TwatterApp";
    private static TwatterApp instance;
    private static final String API_key = "VeBNqm0wZy8iaXQ1frmUZZvBM";
    private static final String API_secret = "nOicddrX7CV0UwLVpxpTOAlzWAo3bJf2b1CL9vF4Lx9Mc5p9Uz";
    private static  String accessToken = null;
    private static String bearerToken = null;
    private User ingelogteUser = null;
    private ArrayList<Tweet> searchResults;
    private ArrayList<Tweet> userTimeLine;

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
        try {
            while(!searchResults.isEmpty()){
                searchResults.remove(0);
            }
            for (int i = 0; i < jsonObjects.length(); i++) {
                Tweet tweetAdded = new Tweet(jsonObjects.getJSONObject(i));
                searchResults.add(tweetAdded);
            }
        } catch (JSONException e) {
            Log.d(TAG, "JSONException: " + e.getMessage());
        }
    }

    public ArrayList<Tweet> getSearchResults() {
        return searchResults;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccesToken(String accessToken) {
        TwatterApp.accessToken = accessToken;
    }

    public ArrayList<Tweet> getUserTimeLine() {
        return userTimeLine;
    }

    public void setUserTimeLine(ArrayList<Tweet> userTimeLine) {
        this.userTimeLine = userTimeLine;
    }
}
