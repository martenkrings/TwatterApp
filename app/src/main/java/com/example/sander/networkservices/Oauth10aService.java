package com.example.sander.networkservices;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;

/**
 * Created by Gebruiker on 6/7/2016.
 */
public class Oauth10aService extends DefaultApi10a {
    private static Oauth10aService ourInstance = new Oauth10aService();

    public static Oauth10aService getInstance() {
        return ourInstance;
    }

    private Oauth10aService() {
    }

    @Override
    public String getAccessTokenEndpoint() {
        return null;
    }

    @Override
    public String getRequestTokenEndpoint() {
        return null;
    }

    @Override
    public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
        return null;
    }
}
