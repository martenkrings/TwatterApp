package com.example.sander.networkservices.assyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.sander.networkservices.Model.TwatterApp;
import com.example.sander.networkservices.MyOAuthService;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Sander on 22-6-2016.
 */
public class AssyncGetFriendListTask extends AsyncTask<Void, Void, JSONArray> {
    private static final String TAG = "AssyncgetFriendlistTask";

    @Override
    protected JSONArray doInBackground(Void... params) {
        final OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.twitter.com/1.1/friends/list.json", MyOAuthService.getInstance().getService());
        MyOAuthService.getInstance().getService().signRequest(TwatterApp.getAccessToken(), request);
        final Response response = request.send();
        try {
            JSONArray jsonArray = new JSONArray(response.getBody());
            return jsonArray;
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return null;
    }
}
