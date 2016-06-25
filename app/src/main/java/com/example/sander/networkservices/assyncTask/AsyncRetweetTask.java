package com.example.sander.networkservices.assyncTask;

import android.os.AsyncTask;

import com.example.sander.networkservices.Model.TwatterApp;
import com.example.sander.networkservices.MyOAuthService;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

/**
 * Deze Task retweet een status.
 */
public class AsyncRetweetTask extends AsyncTask<String, Void, Boolean> {
    private static final String TAG = "AsyncRetweetTask";

    @Override
    protected Boolean doInBackground(String... params) {
        final OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.twitter.com/1.1/statuses/retweet/:id.json", MyOAuthService.getInstance().getService());
        request.addParameter("id", params[0]);
        MyOAuthService.getInstance().getService().signRequest(TwatterApp.getAccessToken(), request);
        final Response response = request.send();
        if (response.isSuccessful())
            return true;
        return false;
    }
}
