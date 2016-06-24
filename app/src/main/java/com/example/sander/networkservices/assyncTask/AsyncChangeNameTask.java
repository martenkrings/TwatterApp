package com.example.sander.networkservices.assyncTask;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.example.sander.networkservices.Model.TwatterApp;
import com.example.sander.networkservices.MyOAuthService;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

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
 * Created by Sander on 21-6-2016.
 */
public class AsyncChangeNameTask extends AsyncTask<String, Void, Boolean> {
    private static final String TAG = "AsyncChangeNameTask";

    @Override
    protected Boolean doInBackground(String... params) {
        final OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.twitter.com/1.1/account/update_profile.json", MyOAuthService.getInstance().getService());
        request.addParameter("name", params[0]);
        MyOAuthService.getInstance().getService().signRequest(TwatterApp.getAccessToken(), request);
        final Response response = request.send();
        if (response.isSuccessful()){
            return true;
        }
        return false;
    }

}
