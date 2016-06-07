package com.example.sander.networkservices.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sander.networkservices.Model.TwatterApp;
import com.example.sander.networkservices.R;
import com.example.sander.networkservices.assyncTask.AssyncSearchTask;

import java.net.URLEncoder;

public class SearchActivity extends AppCompatActivity {
    private ImageView profileIcon;
    private ImageView tweetIcon;
    private Button searchButton;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        profileIcon = (ImageView) findViewById(R.id.iv_user_icon);
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        tweetIcon = (ImageView) findViewById(R.id.iv_tweet_icon);
        tweetIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        searchBar = (EditText) findViewById(R.id.et_searchbar);
        searchButton = (Button) findViewById(R.id.b_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SEARCH
            }
        });
    }
}
