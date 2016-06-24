package com.example.sander.networkservices.assyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.sander.networkservices.Model.TwatterApp;
import com.example.sander.networkservices.Model.Tweet;
import com.example.sander.networkservices.MyOAuthService;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Sander on 9-6-2016.
 */
public class AsyncTimeLineTask extends AsyncTask {
    private static final String TAG = "AsyncTimeLineTask";

    @Override
    protected Object doInBackground(Object[] params) {

        final OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/statuses/home_timeline.json", MyOAuthService.getInstance().getService());
        MyOAuthService.getInstance().getService().signRequest(TwatterApp.getAccessToken(), request);
        final Response response = request.send();

        Log.d(TAG, "response code: " + response.getMessage());
        if (response.isSuccessful()) {
            JSONArray jsonArray = null;
            ArrayList<Tweet> tweetsFound = new ArrayList<>();
            //pars te result
            try {
                jsonArray = new JSONArray(response.getBody());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    tweetsFound.add(new Tweet(jsonObject));
                }
                //save the result
                Log.d(TAG, "we saved the timeline");
                TwatterApp.getInstance().setUserTimeLine(tweetsFound);
            } catch (JSONException e) {
                Log.d(TAG, "JSONException: " + e.getMessage());
            }
        }
        return null;
    }
}