package com.example.sander.networkservices.Model;

/**
 * Model klasse voor een hashtag.
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
