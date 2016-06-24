package com.example.sander.networkservices.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.sander.networkservices.Model.TwatterApp;
import com.example.sander.networkservices.Model.Tweet;
import com.example.sander.networkservices.Model.Tweet_Model;
import com.example.sander.networkservices.assyncTask.AsyncGetProfileInfoTask;
import com.example.sander.networkservices.assyncTask.AsyncTimeLineTask;
import com.example.sander.networkservices.assyncTask.MyAsyncBearerTask;
import com.example.sander.networkservices.R;
import com.example.sander.networkservices.View.ListAdapter;
import com.github.scribejava.core.model.OAuth1AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private ImageView logoutX;
    private ImageView userIcon;
    private ImageView searchIcon;
    private ListView tweetList;
    private ListAdapter adapter;
    private String token;
    private String secret;
    private Button tweetPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set our custom toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // all the buttons
        logoutX = (ImageView) findViewById(R.id.iv_logout_x);
        logoutX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwatterApp.getInstance().setIngelogteUser(null);
                //GO TO LOGIN
            }
        });
        userIcon = (ImageView) findViewById(R.id.iv_user_icon);
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        searchIcon = (ImageView) findViewById(R.id.iv_search_icon);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });


        //koppelen views
        tweetList = (ListView) findViewById(R.id.lv_listview);
        tweetPostButton = (Button) findViewById(R.id.b_tweet_post_button);

        //if the tweet post  button is pressed go to the tweet activity
        tweetPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TweetActivity.class);
                startActivity(intent);
            }
        });

        //get a bearer token
        MyAsyncBearerTask x = new MyAsyncBearerTask();
        x.execute();

        Log.d(TAG, "ik kom voor de if");

        //if we dont already have an acces token get one!
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(AuthorizationActivity.sharedPreferenceName), Context.MODE_PRIVATE);
        token = sharedPreferences.getString("ACCESSTOKEN_TOKEN", "null");
        secret = sharedPreferences.getString("ACCESSTOKEN_SECRET", "null");
        Log.d(TAG, "token: " + token);
        Log.d(TAG, "secret: " + secret);
        OAuth1AccessToken accessToken = new OAuth1AccessToken(token, secret);
        TwatterApp.setAccesToken(accessToken);
        if (token.equals("null") || secret.equals("null")){
            Log.d(TAG, "ik ga nu de acces token op halen");
            Intent intent = new Intent(this, AuthorizationActivity.class);
            startActivity(intent);
        } else {
            Log.d(TAG, "we hebben al een acces token");
            //get the users timeline
            AsyncTimeLineTask asyncTimeLineTask = new AsyncTimeLineTask();
            asyncTimeLineTask.execute();

            //wait for the asyncTimeLineTask
            try {
                asyncTimeLineTask.get(10000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Log.d(TAG, "InterruptedException: " + e.getMessage());
            } catch (ExecutionException e) {
                Log.d(TAG, "ExecutionException: " + e.getMessage());
            } catch (TimeoutException e) {
                Log.d(TAG, "TimeoutException: " + e.getMessage());
            }

            //get the users data
            AsyncGetProfileInfoTask asyncGetProfileInfoTask = new AsyncGetProfileInfoTask();
            asyncGetProfileInfoTask.execute();

            if (TwatterApp.getInstance().getUserTimeLine() != null) {
                adapter = new ListAdapter(this, TwatterApp.getInstance().getUserTimeLine());
                tweetList.setAdapter(adapter);
                Log.d(TAG, "test");
            }
        }
    }

    /**
     * Reads an asset file and returns a string with the full contents.
     *
     * @param filename  The filename of the file to read.
     * @return          The contents of the file.
     * @throws IOException  If file could not be found or not read.
     */
    private String readAssetIntoString(String filename) throws IOException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            InputStream is = getAssets().open(filename, AssetManager.ACCESS_BUFFER);
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                   e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * Progresses the String gotten from a JSON file in Tweets and users
     * @param jsonString the String to be progressed
     * @throws JSONException
     */
    public void progressString(String jsonString) throws JSONException {
        JSONObject gebrObj = new JSONObject(jsonString);
        JSONArray JSON_Objects = gebrObj.getJSONArray("statuses");
        for (int i = 0; i < JSON_Objects.length(); i++) {
            Tweet tweetAdded = new Tweet(JSON_Objects.getJSONObject(i));
            Tweet_Model.getInstance().addTweet(tweetAdded);
        }
    }
}
