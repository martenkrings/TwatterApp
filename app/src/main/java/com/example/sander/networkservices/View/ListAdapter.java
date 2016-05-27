package com.example.sander.networkservices.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sander.networkservices.Model.Tweet;
import com.example.sander.networkservices.Model.Tweet_Model;
import com.example.sander.networkservices.R;

/**
 * Created by Sander on 13-5-2016.
 */
public class ListAdapter extends ArrayAdapter {
    public ListAdapter(Context context) {
        super(context, 0, Tweet_Model.getInstance().getTweets());
    }

    //TODO change date format to fit, change atAuthor format to fit , get Image via url(picasso), change ;ayoput of types(spanableString)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView author = (TextView) convertView.findViewById(R.id.tv_author_name);
        TextView atAuthor = (TextView) convertView.findViewById(R.id.tv_atAuthor);
        TextView timePassed = (TextView) convertView.findViewById(R.id.tv_time_passed);
        TextView tweetText = (TextView) convertView.findViewById(R.id.tv_tweet_text);
        ImageView image = (ImageView) convertView.findViewById(R.id.iv_picture);

        Tweet tweet = (Tweet) getItem(position);
        author.setText(tweet.getUser().getName());
        atAuthor.setText("@" + tweet.getUser().getScreen_name());
        timePassed.setText(tweet.getCreated_at());
        tweetText.setText(tweet.getText());
        //image.setImageResource(tweet.getUser().xxxxxxxxxxxxxxxx);

        return convertView;
    }
}
