package com.example.sander.networkservices.assyncTask;

import android.os.AsyncTask;
import android.util.Base64;

import com.example.sander.networkservices.Model.TwatterApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Sander on 27-5-2016.
 */
public class MyAssyncBearerTask extends AsyncTask {
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
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencode;charset=UTF-8");

            //Set body
            conn.setDoOutput(true);
            byte[] body = "grant_type=client_credentials".getBytes("UTF-8");

            conn.setFixedLengthStreamingMode(body.length);
            BufferedOutputStream os = new BufferedOutputStream(conn.getOutputStream());
            os.write(body);
            os.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                is = conn.getInputStream();
                JSONObject jsonObject = new JSONObject(is.toString());
                is.close();
                System.out.println("Bearer token: " + jsonObject.getString("acces_token"));
                TwatterApp.getInstance().setBearerToken(jsonObject.getString("access_token"));
            }

            System.out.println("connection status= " + conn.getResponseCode() + " Should be: " + HttpURLConnection.HTTP_OK + " Message: " + conn.getResponseMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
    }
}
