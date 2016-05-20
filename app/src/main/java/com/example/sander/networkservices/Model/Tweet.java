package com.example.sander.networkservices.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Sander on 13-5-2016.
 */
public class Tweet {
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
        System.out.println("test");
        this.favorite_count = JSON_Tweet.getInt("favorite_count");
        this.retweet_count = JSON_Tweet.getInt("retweet_count");

        JSONObject userObject = JSON_Tweet.getJSONObject("user");
        User newUSer = new User(userObject.getString("id_str"), userObject.getString("name"), userObject.getString("screen_name"), userObject.getString("created_at"), userObject.getInt("follower_count"), userObject.getInt("following_count"), userObject.getInt("favorite_count"), userObject.getInt("friends_count"), userObject.getString("description"));
        this.user = newUSer;
    }

    public String getId_str() {
        return id_str;
    }

    public String getText() {
        return text;
    }

    public String getCreated_at() {
        return created_at;
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
