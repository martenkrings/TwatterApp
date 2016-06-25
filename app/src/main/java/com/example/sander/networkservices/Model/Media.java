package com.example.sander.networkservices.Model;

/**
 * Model klasse voor een media element.
 */
public class Media extends Entitie {
    private String id_str;
    private Url url;

    public Media(int[] indeces, String id_str, Url url) {
        super(indeces);
        this.id_str = id_str;
        this.url = url;
    }
}
