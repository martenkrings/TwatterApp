package com.example.sander.networkservices.Model;

import java.net.URL;

/**
 * Created by Sander on 13-5-2016.
 */
public class Url extends Entitie{
    private Url url;
    private URL expanded_url;
    private URL display_url;

    public Url(int[] indeces, Url url) {
        super(indeces);
        this.url = url;
    }
}
