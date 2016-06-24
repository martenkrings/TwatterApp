package com.example.sander.networkservices.Model;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private ArrayList<Hasthag> hasthags = new ArrayList<>();
    private ArrayList<Url> urls = new ArrayList<>();
    private ArrayList<UserMention> userMentions = new ArrayList<>();

    public Tweet(String id_str, String text, String created_at, User user, int favorite_count, int retweet_count) {
        this.id_str = id_str;
        this.text = text;
        this.created_at = created_at;
        this.user = user;
        this.favorite_count = favorite_count;
        this.retweet_count = retweet_count;
    }

    /**
     * @param JSON_Tweet
     * @throws JSONException
     */
    public Tweet(JSONObject JSON_Tweet) throws JSONException {
        this.id_str = JSON_Tweet.getString("id_str");
        this.text = JSON_Tweet.getString("text");
        this.created_at = JSON_Tweet.getString("created_at");
        this.favorite_count = JSON_Tweet.getInt("favorite_count");
        this.retweet_count = JSON_Tweet.getInt("retweet_count");

        //add entites
        JSONObject jsonEntities = JSON_Tweet.getJSONObject("entities");

        //add hasthags
        JSONArray jsonHashtags = jsonEntities.getJSONArray("hashtags");
        //if there are no hashtags dont try to add any
        if (jsonHashtags.length() != 0) {
            for (int i = 0; i < jsonHashtags.length(); i++) {
                JSONObject jsonHashtag = jsonHashtags.getJSONObject(i);
                String hashtagText = jsonHashtag.getString("text");
                JSONArray jsonIndices = jsonHashtag.getJSONArray("indices");
                int[] indices = {jsonIndices.getInt(0), jsonIndices.getInt(1)};
                addhastag(new Hasthag(indices, hashtagText));
            }
        }
        //add urls
        JSONArray jsonUrls = jsonEntities.getJSONArray("urls");
        //if there are no urls dont try to add any
        if (jsonUrls.length() != 0){
            for (int i = 0; i < jsonUrls.length(); i++){
                JSONObject jsonUrl = jsonUrls.getJSONObject(i);
                JSONArray jsonIndices = jsonUrl.getJSONArray("indices");
                int[] indices = {jsonIndices.getInt(0), jsonIndices.getInt(1)};
                addUrl(new Url(indices, jsonUrl.getString("url")));
            }
        }
        //add userMentions
        JSONArray jsonUserMentions = jsonEntities.getJSONArray("user_mentions");
        //if there are no userMention dont try to add any
        if (jsonUserMentions.length() != 0){
            for (int i = 0; i < jsonUserMentions.length(); i++){
                JSONObject jsonUserMention = jsonUserMentions.getJSONObject(i);
                JSONArray jsonIndices = jsonUserMention.getJSONArray("indices");
                int[] indices = {jsonIndices.getInt(0), jsonIndices.getInt(1)};
                addUserMention(new UserMention(indices, jsonUserMention.getString("name")));
            }
        }
        //add user
        JSONObject userObject = JSON_Tweet.getJSONObject("user");
        User newUSer = new User(userObject);
        this.user = newUSer;
    }

    /*
    voeg een hasthag toe
     */
    public void addhastag(Hasthag hasthag) {
        hasthags.add(hasthag);
    }

    /*
    voeg een url toe
     */
    public void addUrl(Url url) {
        urls.add(url);
    }

    /*
    voeg een userMention toe
     */
    public void addUserMention(UserMention userMention) {
        userMentions.add(userMention);
    }

    //getters en setters
    public ArrayList<Hasthag> getHasthags() {
        return hasthags;
    }

    public ArrayList<Url> getUrls() {
        return urls;
    }

    public ArrayList<UserMention> getUserMentions() {
        return userMentions;
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
