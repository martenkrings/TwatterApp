package com.example.sander.networkservices.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sander.networkservices.MyOAuthService;
import com.example.sander.networkservices.R;
import com.github.scribejava.core.model.OAuth1AccessToken;

public class AuthorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        WebView wv = (WebView) findViewById(R.id.wvAuthentication);
        String url = MyOAuthService.getInstance().getService()
                .getAuthorizationUrl(MyOAuthService.getInstance().getService().getRequestToken());
        wv.loadUrl(url);

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("https://www.google.nl")) {
                    Uri uri = Uri.parse(url);
                    String verifier = uri.getQueryParameter("oauth_verifier");

                    AsyncAccessTask accessTask = new AsyncAccessTask();
                    accessTask.execute(verifier);
                }
                return false;
            }
        });
    }

    public void saveAccessToken(OAuth1AccessToken accessToken) {
        String token = accessToken.getToken();
        String secret = accessToken.getTokenSecret();

        SharedPreferences preferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ACCESSTOKEN_TOKEN", token);
        editor.putString("ACCESSTOKEN_SECRET", secret);
    }

    private class AsyncAccessTask extends AsyncTask<String, Void, OAuth1AccessToken> {

        @Override
        protected OAuth1AccessToken doInBackground(String... params) {

            OAuth1AccessToken accessToken =
                    MyOAuthService.getInstance().getService()
                            .getAccessToken(MyOAuthService.getInstance().getService().getRequestToken(), params[0]);

            return accessToken;
        }

        @Override
        protected void onPostExecute(OAuth1AccessToken oAuth1AccessToken) {
            AuthorizationActivity.this.saveAccessToken(oAuth1AccessToken);
        }
    }
}
