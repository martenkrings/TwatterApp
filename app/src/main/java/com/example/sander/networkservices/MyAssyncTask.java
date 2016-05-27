package com.example.sander.networkservices;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Sander on 27-5-2016.
 */
public class MyAssyncTask extends AsyncTask {
    private String searchRequest;
    private String urlSearchRequest;
    private static final String API_key;
    private static final String API_secret;
    private String bearerToken = null;

    public MyAssyncTask(String searchRequest) throws UnsupportedEncodingException {
        urlSearchRequest = URLEncoder.encode(searchRequest, "UTF-8");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        if (bearerToken == null) {
            try {
                //prepare request
                URL url = new URL("https://api.twitter.com/oauth2/token");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                //Encode API key and secret
                String authString = URLEncoder.encode(API_key, CHARSET_UTF_8)
                        + ":" + URLEncoder.encode(API_secret, CHARSET_UTF_8);

                //Apply Base64 encoding on the encode string
                String authStringBase64 = Base64.encodeToString(authString.getBytes(CHARSET_UTF_8),
                        Base64.NO_WRAP);

                //Set headers
                conn.setRequestProperty("Authorization", "Basic " + authStringBase64);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencode;charset=UTF-8");

                //Set body
                conn.setDoOutput(true);
                byte[] body = "grant_type=client_credentials".getBytes("UTF-8");

                conn.setFixedLengthStreamingMode(body.length);
                BufferedOutputStream os = new BufferedOutputStream(conn.getOutputStream());
                os.write(body);
                os.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
