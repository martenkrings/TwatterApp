package com.example.sander.networkservices.assyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.sander.networkservices.Model.TwatterApp;
import com.example.sander.networkservices.Model.Tweet;

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
        HttpURLConnection conn = null;
        try{
            URL url = new URL("https://api.twitter.com/1.1/statuses/home_timeline.json");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //set headers
            conn.addRequestProperty("Authorization", "AccessToken " + TwatterApp.getInstance().getAccessToken());

            Log.d(TAG, conn.getResponseCode() + "");
            if (conn.getResponseCode() == 200){
                InputStream is = conn.getInputStream();
                String results = IOUtils.toString(is);
                Log.d(TAG, results);
                JSONArray jsonArray = new JSONArray(results);
                Log.d(TAG, jsonArray.toString());
                ArrayList<Tweet> tweetsFound = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    tweetsFound.add(new Tweet(jsonObject));
                }
            }
        } catch (MalformedURLException e) {
            Log.d(TAG, "MalformedUrlException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.d(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "IOException: " + e.getMessage());
        } catch (JSONException e) {
            Log.d(TAG, "JSONException: " + e.getMessage());
        }
        return null;
    }
}
