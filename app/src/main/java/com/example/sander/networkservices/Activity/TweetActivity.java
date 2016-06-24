package com.example.sander.networkservices.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sander.networkservices.Model.TwatterApp;
import com.example.sander.networkservices.R;
import com.example.sander.networkservices.assyncTask.AsyncTweetTask;

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
                TwatterApp.getInstance().setIngelogteUser(null);
                //GO TO LOGIN
            }
        });
        userIcon = (ImageView) findViewById(R.id.iv_user_icon);
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TweetActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        searchIcon = (ImageView) findViewById(R.id.iv_search_icon);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TweetActivity.this, SearchActivity.class);
            }
        });

        tweetIcon = (ImageView) findViewById(R.id.iv_tweet_icon);
        tweetIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TweetActivity.this, TweetActivity.class);
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
                    finish();
                    startActivity(getIntent());
                }
            }
        });

    }
}
