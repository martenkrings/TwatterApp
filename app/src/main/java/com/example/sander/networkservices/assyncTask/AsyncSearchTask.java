package com.example.sander.networkservices.assyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.sander.networkservices.Model.TwatterApp;

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

/**
 * Deze Task is voor het uitvoern van een zoekopdracht.
 */
public class AsyncSearchTask extends AsyncTask {
    private static final String TAG = "AsyncSearchTask";
    private String searchParameters;

    public AsyncSearchTask(String searchParameters) {
        this.searchParameters = searchParameters;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            URL url = new URL("https://api.twitter.com/1.1/search/tweets.json?q=" + searchParameters);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //set headers
            conn.addRequestProperty("Authorization", "Bearer " + TwatterApp.getInstance().getBearerToken());

            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                InputStream is = conn.getInputStream();
                String results = IOUtils.toString(is);
                JSONObject jsonObject = new JSONObject(results);
                JSONArray jsonObjects = jsonObject.getJSONArray("statuses");
                TwatterApp.getInstance().addSearchResults(jsonObjects);
            }
        } catch (MalformedURLException e) {
            Log.d(TAG, "MalfromedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.d(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "IOException: " + e.getMessage());
        } catch (JSONException e) {
            Log.d(TAG, "JSONException: " + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
