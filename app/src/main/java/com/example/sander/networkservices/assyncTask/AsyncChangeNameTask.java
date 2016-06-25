package com.example.sander.networkservices.assyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.sander.networkservices.Model.TwatterApp;
import com.example.sander.networkservices.MyOAuthService;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

/**
 * Deze Task verandert je profiel naam.
 */
public class AsyncChangeNameTask extends AsyncTask<String, Void, Boolean> {
    private static final String TAG = "AsyncChangeNameTask";

    @Override
    protected Boolean doInBackground(String... params) {
        final OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.twitter.com/1.1/account/update_profile.json", MyOAuthService.getInstance().getService());
        request.addParameter("name", params[0]);
        MyOAuthService.getInstance().getService().signRequest(TwatterApp.getAccessToken(), request);
        final Response response = request.send();
        Log.d(TAG, "was response succesfull: " + response.isSuccessful());
        Log.d(TAG, "respnse message: " + response.getMessage());
        if (response.isSuccessful()){
            return true;
        }
        return false;
    }

}
