package com.example.sander.networkservices.Model;

/**
 * Model klasse voor een usermention.
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
