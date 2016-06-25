package com.example.sander.networkservices;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth10aService;

/**
 * Singleton klasse voor het bouwen en het ophalen van de oauth service.
 */
public class MyOAuthService {

    public final String CONSUMER_KEY = "VeBNqm0wZy8iaXQ1frmUZZvBM";
    public final String CONSUMER_SECRET = "nOicddrX7CV0UwLVpxpTOAlzWAo3bJf2b1CL9vF4Lx9Mc5p9Uz";
    public final String CALLBACK_URL = "https://www.google.nl/";
    private OAuth10aService service;
    private static MyOAuthService ourInstance = new MyOAuthService();

    private MyOAuthService() {
        service = new ServiceBuilder()
                .apiKey(CONSUMER_KEY)
                .apiSecret(CONSUMER_SECRET)
                .callback(CALLBACK_URL)
                .build(TwitterAPI.getInstance());
    }

    public static MyOAuthService getInstance() {
        return ourInstance;
    }

    public OAuth10aService getService() {
        return service;
    }
}
