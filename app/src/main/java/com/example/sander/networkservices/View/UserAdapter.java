package com.example.sander.networkservices.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sander.networkservices.Model.User;
import com.example.sander.networkservices.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Adapter voor een lijst gebruikers.
 */
public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(Context context, ArrayList<User> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_list_item, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_profile_picture_friend_list);
        TextView userName = (TextView) convertView.findViewById(R.id.tv_porfile_name_friend_list);
        TextView screenName = (TextView) convertView.findViewById(R.id.tv_porfile_screen_name_friend_list);
        TextView followerCount = (TextView) convertView.findViewById(R.id.tv_proflile_volgers_friend_list);

        User user = getItem(position);
        Picasso.with(getContext()).load(user.getImageUrl()).into(imageView);
        userName.setText(user.getName());
        screenName.setText("@" + user.getScreen_name());
        followerCount.setText(user.getFollower_count() + "");
        return convertView;
    }
}
