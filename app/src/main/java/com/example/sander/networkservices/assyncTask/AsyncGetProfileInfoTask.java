package com.example.sander.networkservices.assyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.sander.networkservices.Model.TwatterApp;
import com.example.sander.networkservices.Model.User;
import com.example.sander.networkservices.MyOAuthService;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sander on 25-6-2016.
 */
public class AsyncGetProfileInfoTask extends AsyncTask {
    private static final String TAG = "AsyncGetProfileInfoTask";

    @Override
    protected Object doInBackground(Object[] params) {
        final OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/account/verify_credentials.json", MyOAuthService.getInstance().getService());
        MyOAuthService.getInstance().getService().signRequest(TwatterApp.getAccessToken(), request);
        final Response response = request.send();

        if (response.isSuccessful()){
            try {
                JSONObject jsonObject = new JSONObject(response.getBody());
                User user = new User(jsonObject);
                TwatterApp.getInstance().setIngelogteUser(user);
            } catch (JSONException e) {
                Log.d(TAG, "JSONException: " + e.getMessage());
            }
        }
        return null;
    }
}
