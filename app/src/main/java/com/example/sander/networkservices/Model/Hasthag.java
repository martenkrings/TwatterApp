package com.example.sander.networkservices.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sander on 13-5-2016.
 */
public class Hasthag extends Entitie{
    private String text;

    public Hasthag(int[] indeces, String text) {
        super(indeces);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
