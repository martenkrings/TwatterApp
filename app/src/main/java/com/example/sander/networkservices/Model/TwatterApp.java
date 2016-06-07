package com.example.sander.networkservices.Model;

/**
 * Created by Sander on 7-6-2016.
 */
public class TwatterApp {
    private static TwatterApp instance;
    private static final String API_key = "VeBNqm0wZy8iaXQ1frmUZZvBM";
    private static final String API_secret = "nOicddrX7CV0UwLVpxpTOAlzWAo3bJf2b1CL9vF4Lx9Mc5p9Uz";
    private String bearerToken = null;

    private TwatterApp (){

    }

    public static TwatterApp getInstance(){
        if (instance == null){
            instance = new TwatterApp();
        }
        return instance;
    }

    public static String getAPI_key() {
        return API_key;
    }

    public static String getAPI_secret() {
        return API_secret;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
