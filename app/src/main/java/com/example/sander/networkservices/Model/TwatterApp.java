package com.example.sander.networkservices.Model;

import android.util.Log;

import com.github.scribejava.core.model.OAuth1AccessToken;

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
    private static OAuth1AccessToken accessToken = null;
    private static String bearerToken = null;
    private User ingelogteUser = new User("1", "sanderGW", "SanderGW", "Wed Apr 20 13:01:49 +0000 2016", 50, 40, 60, "een dummy user", "https://lh3.googleusercontent.com/32GbAQWrubLZX4mVPClpLN0fRbAd3ru5BefccDAj7nKD8vz-_NzJ1ph_4WMYNefp3A=w300-rw");
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

    public static OAuth1AccessToken getAccessToken() {
        return accessToken;
    }

    public static void setAccesToken(OAuth1AccessToken accessToken) {
        TwatterApp.accessToken = accessToken;
    }

    public ArrayList<Tweet> getUserTimeLine() {
        return userTimeLine;
    }

    public void setUserTimeLine(ArrayList<Tweet> userTimeLine) {
        this.userTimeLine = userTimeLine;
    }


}
