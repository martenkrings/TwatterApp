package com.example.sander.networkservices.Model;

import java.util.ArrayList;

/**
 * Created by Sander on 13-5-2016.
 */
public class Tweet_Model {
    private ArrayList<Tweet> tweets = new ArrayList<>();
    private static Tweet_Model instance;

    private Tweet_Model(){
        User user = new User("Sander", "SanderGW");
        Tweet tweet = new Tweet("08556", "Dit is een tweet", "9 jan 2016", user, 9, 9);
        tweets.add(tweet);
    }

    public static Tweet_Model getInstance(){
        if (instance == null){
            instance = new Tweet_Model();
        }
        return instance;
    }

    public ArrayList<Tweet> getTweets(){
        return tweets;
    }

    public void addTweet(Tweet tweet){
        tweets.add(tweet);
    }
}
