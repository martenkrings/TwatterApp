package com.example.sander.networkservices.assyncTask;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.example.sander.networkservices.Model.TwatterApp;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Sander on 27-5-2016.
 */
public class MyAssyncBearerTask extends AsyncTask {
    private static final String TAG = "MyAssyncBearerTask";
    private static final String CHARSET_UTF_8 = "UTF-8";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            //prepare request
            URL url = new URL("https://api.twitter.com/oauth2/token");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            //Encode API key and secret
            String authString = URLEncoder.encode(TwatterApp.getInstance().getAPI_key(), CHARSET_UTF_8)
                    + ":" + URLEncoder.encode(TwatterApp.getInstance().getAPI_secret(), CHARSET_UTF_8);

            //Apply Base64 encoding on the encode string
            String authStringBase64 = Base64.encodeToString(authString.getBytes(CHARSET_UTF_8),
                    Base64.NO_WRAP);

            //Set headers
            conn.setRequestProperty("Authorization", "Basic " + authStringBase64);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            //Set body
            conn.setDoOutput(true);
            byte[] body = "grant_type=client_credentials".getBytes("UTF-8");

            conn.setFixedLengthStreamingMode(body.length);
            BufferedOutputStream os = new BufferedOutputStream(conn.getOutputStream());
            os.write(body);
            os.close();

            Log.d(TAG, "" + conn.getResponseCode());
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                is = conn.getInputStream();
                Log.d(TAG, "Bearer token: " + IOUtils.toString(is));
                TwatterApp.getInstance().setBearerToken(IOUtils.toString(is));
                IOUtils.closeQuietly(is);
            }
        } catch (MalformedURLException e) {
            Log.d(TAG, "MalformaedURLException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "IOException: " + e.getMessage());
        } finally {
            conn.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
    }
}
