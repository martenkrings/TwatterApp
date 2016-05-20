package com.example.sander.networkservices.Activity;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.sander.networkservices.Model.Tweet;
import com.example.sander.networkservices.Model.Tweet_Model;
import com.example.sander.networkservices.R;
import com.example.sander.networkservices.View.ListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView tweetList = (ListView) findViewById(R.id.lv_listview);
        ListAdapter adapter = new ListAdapter(this);

        //Try to progress the info of the JSON file
        try {
            String test = readAssetIntoString("JSON_example.json");
            Log.d(TAG, "(String of json object) " + test);
            progressString(readAssetIntoString("JSON_example.json"));
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tweetList.setAdapter(adapter);
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
                    Log.e(TAG, e.getLocalizedMessage());
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
        Log.d(TAG, gebrObj.toString());
        JSONArray JSON_Objects = gebrObj.getJSONArray("statuses");
        for (int i = 0; i < JSON_Objects.length(); i++) {
            Tweet tweetAdded = new Tweet(JSON_Objects.getJSONObject(i));
            Tweet_Model.getInstance().addTweet(tweetAdded);
        }
    }
}
