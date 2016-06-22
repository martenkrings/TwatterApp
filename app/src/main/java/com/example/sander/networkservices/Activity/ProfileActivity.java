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
import android.widget.TextView;

import com.example.sander.networkservices.Model.TwatterApp;
import com.example.sander.networkservices.R;
import com.example.sander.networkservices.View.ListAdapter;
import com.example.sander.networkservices.assyncTask.AssyncChangeNameTask;
import com.example.sander.networkservices.assyncTask.AssyncGetFriendListTask;
import com.example.sander.networkservices.assyncTask.AssyncTimeLineTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {
    private ImageView tweetIcon;
    private ImageView searchIcon;
    private ImageView profilePicture;
    private TextView profileName;
    private TextView profileScreenName;
    private TextView volgers;
    private Button vriendenButton;
    private Button naamButton;
    private EditText changeName;
    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AssyncTimeLineTask assyncTimeLineTask = new AssyncTimeLineTask();
        assyncTimeLineTask.execute();

        //connect with views
        profilePicture = (ImageView) findViewById(R.id.iv_profile_picture);
        profileName = (TextView) findViewById(R.id.tv_porfile_name);
        profileScreenName = (TextView) findViewById(R.id.tv_profile_screenName);
        volgers = (TextView) findViewById(R.id.tv_proflile_volgers);
        naamButton = (Button) findViewById(R.id.b_profile_naam_bewerken);
        vriendenButton = (Button) findViewById(R.id.b_search_button);
        changeName = (EditText) findViewById(R.id.et_profiel_naam);


        //fill views
        Picasso.with(this).load(TwatterApp.getInstance().getIngelogteUser().getImageUrl()).into(profilePicture);
        profileName.setText(TwatterApp.getInstance().getIngelogteUser().getName());
        profileScreenName.setText(TwatterApp.getInstance().getIngelogteUser().getScreen_name());
        volgers.setText(TwatterApp.getInstance().getIngelogteUser().getFollower_count() + "");

        //configure buttons
        searchIcon = (ImageView) findViewById(R.id.iv_search_icon);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SearchActivity.class);
            }
        });

        tweetIcon = (ImageView) findViewById(R.id.iv_tweet_icon);
        tweetIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        vriendenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssyncGetFriendListTask assyncGetFriendListTask = new AssyncGetFriendListTask();
                try {
                    JSONArray jsonArray = assyncGetFriendListTask.execute().get();
                } catch (InterruptedException e) {
                    Log.d(TAG, "InterruptedException: " + e.getMessage());
                } catch (ExecutionException e) {
                    Log.d(TAG, "ExecutionException: " + e.getMessage());
                }
            }
        });


        naamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!changeName.getText().equals("")){
                    AssyncChangeNameTask assyncChangeNameTask = new AssyncChangeNameTask();
                    assyncChangeNameTask.execute(changeName.getText().toString());
                    finish();
                    startActivity(getIntent());
                }
            }
        });

    }
}
