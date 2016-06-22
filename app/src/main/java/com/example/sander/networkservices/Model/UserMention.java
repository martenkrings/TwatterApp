package com.example.sander.networkservices.Model;

/**
 * Created by Sander on 13-5-2016.
 */
public class UserMention extends Entitie {
    private String name;
    private String screenName;
    private String id_str;

    public UserMention(int[] indeces, String name) {
        super(indeces);
        this.name = name;
    }
}
