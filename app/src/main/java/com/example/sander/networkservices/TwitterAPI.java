package com.example.sander.networkservices;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;

/**
 * Created by Gebruiker on 6/16/2016.
 */
public class TwitterAPI extends DefaultApi10a {

    private static TwitterAPI instance = new TwitterAPI();

    private final String REQUEST_URL = "https://api.twitter.com/oauth/request_token";
    private final String ACCESS_URL = "https://api.twitter.com/oauth/access_token";
    private final String AUTHORIZATION_URL = "https://api.twitter.com/oauth/authorize";

    private TwitterAPI() {
    }

    public static TwitterAPI getInstance() {
        return instance;
    }

    @Override
    public String getRequestTokenEndpoint() {
        return REQUEST_URL;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return ACCESS_URL;
    }

    @Override
    public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
        return AUTHORIZATION_URL + "?oauth_token=" + requestToken.getToken();
    }
}
