package com.example.sander.networkservices.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sander.networkservices.Activity.MainActivity;
import com.example.sander.networkservices.Activity.SearchActivity;
import com.example.sander.networkservices.Model.Hasthag;
import com.example.sander.networkservices.Model.Tweet;
import com.example.sander.networkservices.Model.Tweet_Model;
import com.example.sander.networkservices.Model.Url;
import com.example.sander.networkservices.Model.UserMention;
import com.example.sander.networkservices.R;
import com.example.sander.networkservices.controller.RetweetCallbacks;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sander on 13-5-2016.
 */
public class ListAdapter extends ArrayAdapter {
    public ListAdapter(Context context, ArrayList<Tweet> tweets) {
        super(context, 0, tweets);
    }

    private RetweetCallbacks retweetCallbacks;



    //TODO change layout of entities(spanableString)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView author = (TextView) convertView.findViewById(R.id.tv_author_name);
        TextView atAuthor = (TextView) convertView.findViewById(R.id.tv_atAuthor);
        TextView date = (TextView) convertView.findViewById(R.id.tv_time_passed);
        TextView tweetText = (TextView) convertView.findViewById(R.id.tv_tweet_text);
        ImageView image = (ImageView) convertView.findViewById(R.id.iv_picture);

        Tweet tweet = (Tweet) getItem(position);
        author.setText(tweet.getUser().getName());
        atAuthor.setText("@" + tweet.getUser().getScreen_name());
        date.setText(tweet.getCreated_at());
        Picasso.with(getContext()).load(tweet.getUser().getImageUrl()).into(image);
        SpannableString spannableString = new SpannableString(tweet.getText());
        for (Hasthag hasthag:tweet.getHasthags()) {
            int[] indices = hasthag.getIndeces();
            spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), indices[0], indices[1], 0);
        }
        for (Url url:tweet.getUrls()) {
            int[] indices = url.getIndeces();
            spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), indices[0], indices[1], 0);
        }
        for (UserMention userMention:tweet.getUserMentions()) {
            int[] indices = userMention.getIndeces();
            spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), indices[0], indices[1], 0);
        }
        tweetText.setText(spannableString);
        return convertView;
    }
}
