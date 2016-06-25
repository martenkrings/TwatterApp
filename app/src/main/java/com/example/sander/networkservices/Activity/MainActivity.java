package com.example.sander.networkservices.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.sander.networkservices.Model.TwatterApp;
import com.example.sander.networkservices.assyncTask.AsyncGetProfileInfoTask;
import com.example.sander.networkservices.assyncTask.AsyncTimeLineTask;
import com.example.sander.networkservices.assyncTask.MyAsyncBearerTask;
import com.example.sander.networkservices.R;
import com.example.sander.networkservices.View.ListAdapter;
import com.github.scribejava.core.model.OAuth1AccessToken;

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
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("uitloggen", 1);
                finish();
                startActivity(intent);
            }
        });
        userIcon = (ImageView) findViewById(R.id.iv_user_icon);
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                finish();
                startActivity(intent);
            }
        });
        searchIcon = (ImageView) findViewById(R.id.iv_search_icon);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                finish();
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
                finish();
                startActivity(intent);
            }
        });

        //get a bearer token
        MyAsyncBearerTask x = new MyAsyncBearerTask();
        x.execute();

        //get our sharedPreferences
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(AuthorizationActivity.sharedPreferenceName), Context.MODE_PRIVATE);

        //if this activity was called with an logout boolean logout
        Intent intent = getIntent();
        int uitloggen = intent.getIntExtra("uitloggen", 0);
        if (uitloggen == 1){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("ACCESSTOKEN_TOKEN");
            editor.remove("ACCESSTOKEN_SECRET");
            editor.commit();
        }

        //if we dont already have an acces token get one!
        token = sharedPreferences.getString("ACCESSTOKEN_TOKEN", "null");
        secret = sharedPreferences.getString("ACCESSTOKEN_SECRET", "null");
        OAuth1AccessToken accessToken = new OAuth1AccessToken(token, secret);
        TwatterApp.setAccesToken(accessToken);
        if (token.equals("null") || secret.equals("null")) {
            Intent authorizationIntent = new Intent(this, AuthorizationActivity.class);
            finish();
            startActivity(authorizationIntent);
        } else {
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

            //get the user data
            AsyncGetProfileInfoTask asyncGetProfileInfoTask = new AsyncGetProfileInfoTask();
            asyncGetProfileInfoTask.execute();

            if (TwatterApp.getInstance().getUserTimeLine() != null) {
                adapter = new ListAdapter(this, TwatterApp.getInstance().getUserTimeLine());
                tweetList.setAdapter(adapter);
            }
        }
    }
}
