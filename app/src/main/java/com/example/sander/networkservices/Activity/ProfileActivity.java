package com.example.sander.networkservices.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sander.networkservices.Model.TwatterApp;
import com.example.sander.networkservices.R;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    private ImageView tweetIcon;
    private ImageView searchIcon;
    private ImageView profilePicture;
    private TextView profileName;
    private TextView profileScreenName;
    private TextView volgers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //connect with views
        profilePicture = (ImageView) findViewById(R.id.iv_profile_picture);
        profileName = (TextView) findViewById(R.id.tv_porfile_name);
        profileScreenName = (TextView) findViewById(R.id.tv_profile_screenName);
        volgers = (TextView) findViewById(R.id.tv_proflile_volgers);

        //fill views
        Picasso.with(this).load(TwatterApp.getInstance().getIngelogteUser().getImageUrl()).into(profilePicture);
        profileName.setText(TwatterApp.getInstance().getIngelogteUser().getName());
        profileScreenName.setText(TwatterApp.getInstance().getIngelogteUser().getScreen_name());
        volgers.setText(TwatterApp.getInstance().getIngelogteUser().getFollower_count());

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
    }
}
