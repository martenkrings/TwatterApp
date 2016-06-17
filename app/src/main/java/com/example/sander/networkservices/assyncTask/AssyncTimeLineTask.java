package com.example.sander.networkservices.assyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.sander.networkservices.Model.TwatterApp;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Sander on 9-6-2016.
 */
public class AssyncTimeLineTask extends AsyncTask {
    private static final String TAG = "AssyncTimeLineTask";

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
                JSONObject jsonObject = new JSONObject(results);
                Log.d(TAG, jsonObject.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
