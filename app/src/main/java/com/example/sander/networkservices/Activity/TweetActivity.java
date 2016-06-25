package com.example.sander.networkservices.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sander.networkservices.Model.TwatterApp;
import com.example.sander.networkservices.R;
import com.example.sander.networkservices.assyncTask.AsyncTweetTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TweetActivity extends AppCompatActivity {
    private static final String TAG = "TweetActivity";
    private Toolbar toolbar;
    private ImageView tweetIcon;
    private ImageView searchIcon;
    private ImageView userIcon;
    private ImageView logoutX;
    private EditText status;
    private Button tweetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // all the buttons
        logoutX = (ImageView) findViewById(R.id.iv_logout_x);
        logoutX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TweetActivity.this, MainActivity.class);
                intent.putExtra("uitloggen", 1);
                finish();
                startActivity(intent);
            }
        });
        userIcon = (ImageView) findViewById(R.id.iv_user_icon);
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TweetActivity.this, ProfileActivity.class);
                finish();
                startActivity(intent);
            }
        });

        searchIcon = (ImageView) findViewById(R.id.iv_search_icon);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TweetActivity.this, SearchActivity.class);
                finish();
                startActivity(intent);
            }
        });

        tweetIcon = (ImageView) findViewById(R.id.iv_tweet_icon);
        tweetIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TweetActivity.this, TweetActivity.class);
                finish();
                startActivity(intent);
            }
        });

        status = (EditText) findViewById(R.id.et_tweet);
        tweetButton = (Button) findViewById(R.id.btn_tweet);

        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status.getText().equals("")){
                    AsyncTweetTask asyncTweetTask = new AsyncTweetTask();
                    asyncTweetTask.execute(status.getText().toString());

                    //wait on the tweettask so we wont run out of memory
                    try {
                        asyncTweetTask.get(10000, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        Log.d(TAG, "InterruptedException: " + e.getMessage());
                    } catch (ExecutionException e) {
                        Log.d(TAG, "ExecutionException: " + e.getMessage());
                    } catch (TimeoutException e) {
                        Log.d(TAG, "TimeoutException: " + e.getMessage());
                    }
                    finish();
                    startActivity(getIntent());
                }
            }
        });

    }
}
