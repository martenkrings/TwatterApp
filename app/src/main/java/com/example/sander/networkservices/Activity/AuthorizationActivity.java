package com.example.sander.networkservices.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sander.networkservices.MyOAuthService;
import com.example.sander.networkservices.R;
import com.example.sander.networkservices.TwitterAPI;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

public class AuthorizationActivity extends AppCompatActivity {

    private String url;
    private WebView wv;
    private OAuth1RequestToken requestToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        wv = (WebView) findViewById(R.id.wvAuthentication);
        AsyncRequestTokenTask task = new AsyncRequestTokenTask();
        task.execute();

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("https://www.google.nl")) {
                    Uri uri = Uri.parse(url);
                    Log.d("url:", url);
                    String token = uri.getQueryParameter("oauth_token");
                    String verifier = uri.getQueryParameter("oauth_verifier");

                    Log.d("verifier:", verifier);
                    Log.d("token:", token);

                    AsyncAccessTask accessTask = new AsyncAccessTask();
                    accessTask.execute(verifier);
                    return true;
                }
                return false;
            }
        });
    }

    public void setRequestToken(OAuth1RequestToken oAuth1RequestToken) {
        this.requestToken = oAuth1RequestToken;
    }

    public void setRequestUrl(String requestUrl) {
        url = requestUrl;
        wv.loadUrl(url);
    }

    public void saveAccessToken(OAuth1AccessToken accessToken) {
        String token = accessToken.getToken();
        String secret = accessToken.getTokenSecret();

        SharedPreferences preferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ACCESSTOKEN_TOKEN", token);
        editor.putString("ACCESSTOKEN_SECRET", secret);
        editor.apply();
        finish();
    }

    private class AsyncAccessTask extends AsyncTask<String, Void, OAuth1AccessToken> {

        @Override
        protected OAuth1AccessToken doInBackground(String... params) {
            Log.d("params[0]:", params[0]);

            OAuth1AccessToken accessToken =
                    MyOAuthService.getInstance().getService()
                            .getAccessToken(AuthorizationActivity.this.requestToken, params[0]);
            //MyOAuthService.getInstance().getService().getRequestToken()
            return accessToken;
        }

        @Override
        protected void onPostExecute(OAuth1AccessToken oAuth1AccessToken) {
            AuthorizationActivity.this.saveAccessToken(oAuth1AccessToken);
        }
    }

    private class AsyncRequestTokenTask extends AsyncTask<Void, Void, OAuth1RequestToken> {

        @Override
        protected OAuth1RequestToken doInBackground(Void... params) {
            return MyOAuthService.getInstance().getService().getRequestToken();
        }

        @Override
        protected void onPostExecute(OAuth1RequestToken requestToken) {
            setRequestToken(requestToken);
            String s = MyOAuthService.getInstance().getService().getAuthorizationUrl(requestToken);
            AuthorizationActivity.this.setRequestUrl(s);
        }
    }
}
