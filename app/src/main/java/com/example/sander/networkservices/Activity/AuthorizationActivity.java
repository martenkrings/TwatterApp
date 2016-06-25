package com.example.sander.networkservices.Activity;

import android.content.Context;
import android.content.Intent;
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
import com.github.scribejava.core.model.OAuth1RequestToken;

/**
 * Deze activiteit verbind met de twitter api om een accesstoken op te vragen.
 * Dit token wordt gebruikt om andere request uit te kunnen voeren.
 */
public class AuthorizationActivity extends AppCompatActivity {
    private static final String TAG = "AuthorizationActivity";
    public static final int sharedPreferenceName = R.string.sharedpref;
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

        /**
         * Wanneer de callback url wordt geladen wordt de url opgevangen en wordt de oauth verifier
         * uit de url gehaald om de accesstoken op te vragen.
         */
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("https://www.google.nl")) {
                    Uri uri = Uri.parse(url);
                    String verifier = uri.getQueryParameter("oauth_verifier");

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

    /**
     * Deze methode laadt de authorization url.
     * @param requestUrl de url die wordt geladen.
     */
    public void setRequestUrl(String requestUrl) {
        url = requestUrl;
        wv.loadUrl(url);
    }

    /**
     * Deze methode slaat de access token op om deze later te kunnen gebruiken.
     * @param accessToken
     */
    public void saveAccessToken(OAuth1AccessToken accessToken) {
        String token = accessToken.getToken();
        String secret = accessToken.getTokenSecret();
        SharedPreferences preferences = this.getSharedPreferences(getString(sharedPreferenceName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ACCESSTOKEN_TOKEN", token);
        editor.putString("ACCESSTOKEN_SECRET", secret);
        editor.commit();
    }

    /**
     * Deze klasse verzorgt het opvragen van de access token.
     */
    private class AsyncAccessTask extends AsyncTask<String, Void, OAuth1AccessToken> {
        @Override
        protected OAuth1AccessToken doInBackground(String... params) {
            OAuth1AccessToken accessToken =
                    MyOAuthService.getInstance().getService()
                            .getAccessToken(AuthorizationActivity.this.requestToken, params[0]);
            return accessToken;
        }

        /**
         * on post execute sla de token op en ga naar de mainactivity
         * @param oAuth1AccessToken onze acces token
         */
        @Override
        protected void onPostExecute(OAuth1AccessToken oAuth1AccessToken) {
            AuthorizationActivity.this.saveAccessToken(oAuth1AccessToken);
            Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
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
