package com.example.sander.networkservices.Model;

/**
 * Model klasse voor een entity.
 */
public class Entitie {
    private int[] indeces;

    public Entitie(int[] indeces) {
        this.indeces = indeces;
    }

    public int[] getIndeces() {
        return indeces;
    }
}
