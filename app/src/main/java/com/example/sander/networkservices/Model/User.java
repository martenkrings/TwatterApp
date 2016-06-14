package com.example.sander.networkservices.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sander on 13-5-2016.
 */
public class User {
    private String user_id_str;
    private String name;
    private String screen_name;
    private String created_at;
    private int follower_count;
    private int favorite_count;
    private int friends_count;
    private String description;
    private String imageUrl;
    private int followed_count;

    public User(String user_id_str, String name, String screen_name, String created_at, int followers_count, int favorite_count, int friends_count, String description) {
        this.user_id_str = user_id_str;
        this.name = name;
        this.screen_name = screen_name;
        this.created_at = created_at;
        this.follower_count = followers_count;
        this.favorite_count = favorite_count;
        this.friends_count = friends_count;
        this.description = description;
    }

    public User(JSONObject jsonObject) throws JSONException {
        this.user_id_str = jsonObject.getString("id_str");
        this.name = jsonObject.getString("name");
        this.screen_name = jsonObject.getString("screen_name");
        this.created_at = jsonObject.getString("created_at");
        this.follower_count = jsonObject.getInt("followers_count");
        this.favorite_count = jsonObject.getInt("favourites_count");
        this.friends_count = jsonObject.getInt("friends_count");
        this.description = jsonObject.getString("description");
        this.imageUrl = jsonObject.getString("profile_image_url_https");
    }

    public User(String name, String sccreenName) {
        this.name = name;
        this.screen_name = sccreenName;
    }

    public String getUser_id_str() {
        return user_id_str;
    }

    public String getName() {
        return name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public String getDescription() {
        return description;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
