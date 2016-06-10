package com.example.sander.networkservices.assyncTask;

import android.os.AsyncTask;

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
    @Override
    protected Object doInBackground(Object[] params) {
        HttpURLConnection conn = null;
        InputStream is = null;
        try{
            URL url = new URL("https://api.twitter.com/1.1/statuses/home_timeline.json");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //Set headers
            //conn.setRequestProperty("Authorization", );


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
