package com.example.sander.networkservices.Model;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sander on 13-5-2016.
 */
public class Tweet {
    private static final String TAG = "Tweet";

    private String id_str;
    private String text;
    private String created_at;
    private User user;
    private int favorite_count;
    private int retweet_count;

    public Tweet(String id_str, String text, String created_at, User user, int favorite_count, int retweet_count) {
        this.id_str = id_str;
        this.text = text;
        this.created_at = created_at;
        this.user = user;
        this.favorite_count = favorite_count;
        this.retweet_count = retweet_count;
    }

    /**
     *
     * @param JSON_Tweet
     * @throws JSONException
     */
    public Tweet(JSONObject JSON_Tweet) throws JSONException {
        this.id_str = JSON_Tweet.getString("id_str");
        this.text = JSON_Tweet.getString("text");
        this.created_at = JSON_Tweet.getString("created_at");
        this.favorite_count = JSON_Tweet.getInt("favorite_count");
        this.retweet_count = JSON_Tweet.getInt("retweet_count");

        JSONObject userObject = JSON_Tweet.getJSONObject("user");
        User newUSer = new User(userObject);
        this.user = newUSer;
    }

    public String getCreated_at() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
        sdf.setLenient(true);
        Date date = new Date();
        try {
            date = sdf.parse(created_at);
        } catch (ParseException e) {
            Log.d(TAG, "ParseException: " + e.getMessage());
        }
        SimpleDateFormat sd = new SimpleDateFormat("dd MMM");
        return sd.format(date).toString();
    }

    public String getId_str() {
        return id_str;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public int getRetweet_count() {
        return retweet_count;
    }
}
