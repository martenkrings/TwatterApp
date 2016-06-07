package com.example.sander.networkservices.assyncTask;

import android.os.AsyncTask;

import com.example.sander.networkservices.Model.TwatterApp;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Sander on 3-6-2016.
 */
public class AssyncSearchTask extends AsyncTask {
    private String searchParameters;

    public AssyncSearchTask(String searchParameters) {
        this.searchParameters = searchParameters;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            URL url = new URL("https://api.twitter.com/1.1/Search/tweets.json?q=" + searchParameters);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.addRequestProperty("Authorization", "Bearer " + TwatterApp.getInstance().getBearerToken());

            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                InputStream is = conn.getInputStream();
                //turn is into objects

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
