package com.example.sander.networkservices.Model;

import java.net.URL;

/**
 * Model klasse voor een url
 */
public class Url extends Entitie{
    private String url;
    private String expanded_url;
    private String display_url;

    public Url(int[] indeces, String url) {
        super(indeces);
        this.url = url;
    }
}
