package com.example.sander.networkservices.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.sander.networkservices.Model.User;
import com.example.sander.networkservices.R;
import com.example.sander.networkservices.View.UserAdapter;
import com.example.sander.networkservices.assyncTask.AsyncFollowersTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Deze activiteit laat je volgers zien.
 */
public class FollowersListActivity extends AppCompatActivity {
    private static final String TAG = "FollowersListActivity";
    private Toolbar toolbar;
    private ImageView tweetIcon;
    private ImageView searchIcon;
    private ImageView userIcon;
    private ImageView logoutX;
    private ListView listView;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // all the buttons
        logoutX = (ImageView) findViewById(R.id.iv_logout_x);
        logoutX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FollowersListActivity.this, MainActivity.class);
                intent.putExtra("uitloggen", 1);
                startActivity(intent);
            }
        });
        userIcon = (ImageView) findViewById(R.id.iv_user_icon);
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FollowersListActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        searchIcon = (ImageView) findViewById(R.id.iv_search_icon);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FollowersListActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        tweetIcon = (ImageView) findViewById(R.id.iv_tweet_icon);
        tweetIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FollowersListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //koppel listView
        listView = (ListView) findViewById(R.id.lv_followers_list);
        ArrayList<User> arrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new AsyncFollowersTask().execute().get();
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                User user = new User(jsonObject);
                arrayList.add(user);
            }
        } catch (InterruptedException e) {
            Log.d(TAG, "InterruptedException: " + e.getMessage());
        } catch (ExecutionException e) {
            Log.d(TAG, "ExecutinException: " + e.getMessage());
        } catch (JSONException e) {
            Log.d(TAG, "JSONException: " + e.getMessage());
        }
        adapter = new UserAdapter(this, arrayList);
        listView.setAdapter(adapter);
    }
}
