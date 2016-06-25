package com.example.sander.networkservices.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.sander.networkservices.Model.TwatterApp;
import com.example.sander.networkservices.R;
import com.example.sander.networkservices.View.ListAdapter;
import com.example.sander.networkservices.assyncTask.AsyncSearchTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private ImageView profileIcon;
    private ImageView tweetIcon;
    private ImageView logoutX;
    private Button searchButton;
    private EditText searchBar;
    private ListView searchList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBar = (EditText) findViewById(R.id.et_searchbar);
        searchList = (ListView) findViewById(R.id.lv_search_listview);
        adapter = new ListAdapter(SearchActivity.this, TwatterApp.getInstance().getSearchResults());
        searchList.setAdapter(adapter);

        //toolbar buttons
        logoutX = (ImageView) findViewById(R.id.iv_logout_x);
        logoutX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                intent.putExtra("uitloggen", 1);
                startActivity(intent);
            }
        });

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

        searchButton = (Button) findViewById(R.id.b_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = null;
                try {
                    search = URLEncoder.encode(searchBar.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Log.d(TAG, "UnsupportedEncodingException:" + e.getMessage());
                }
                AsyncSearchTask searchTask = new AsyncSearchTask(search);
                searchTask.execute();

                //wait for the searchTask
                try {
                    searchTask.get(10000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    Log.d(TAG, "InterruptedException: " + e.getMessage());
                } catch (ExecutionException e) {
                    Log.d(TAG, "ExecutionException: " + e.getMessage());
                } catch (TimeoutException e) {
                    Log.d(TAG, "TimeoutException: " + e.getMessage());
                }

                //notify the adapter
                adapter.notifyDataSetChanged();
            }
        });
    }
}
