package com.example.newsfeed;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class FetchNews extends AsyncTask<String, Void, String> {
    private TextView title;

    public FetchNews(TextView titleText) {
        this.title = titleText;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject root = new JSONObject(s);
            JSONArray results = new JSONArray("results");
            JSONObject res1 = results.getJSONObject(0);
            String getTitle = res1.getString("webTitle");
            title.setText(getTitle);
        } catch (Exception e) {
            title.setText("No News found !");
            e.printStackTrace();
        }
    }

}
