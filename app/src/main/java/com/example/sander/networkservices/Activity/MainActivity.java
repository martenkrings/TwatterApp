package com.example.sander.networkservices.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.sander.networkservices.Model.TwatterApp;
import com.example.sander.networkservices.Model.Tweet;
import com.example.sander.networkservices.Model.Tweet_Model;
import com.example.sander.networkservices.assyncTask.MyAssyncBearerTask;
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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private ImageView logoutX;
    private ImageView userIcon;
    private ImageView searchIcon;
    private ImageView tweetIcon;
    private ListView tweetList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("ACCESSTOKEN_TOKEN", "null");
        String secret = sharedPreferences.getString("ACCESSTOKEN_SECRET", "null");
        if (token.equals("null") || secret.equals("null")){
            Intent intent = new Intent(this, AuthorizationActivity.class);
            startActivity(intent);
        }
        OAuth1AccessToken accessToken = new OAuth1AccessToken(token, secret);
        TwatterApp.setAccesToken(accessToken);


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
        tweetIcon = (ImageView) findViewById(R.id.iv_tweet_icon);
        tweetIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TweetActivity.class);
                startActivity(intent);
            }
        });


        tweetList = (ListView) findViewById(R.id.lv_listview);
        adapter = new ListAdapter(this, TwatterApp.getInstance().getUserTimeLine());

        //Try to progress the info of the JSON file
        try {
            progressString(readAssetIntoString("JSON_example.json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tweetList.setAdapter(adapter);

        MyAssyncBearerTask x = new MyAssyncBearerTask();
        x.execute();
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
