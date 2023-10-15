package com.example.newsfeed;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

public class NetworkUtils {
    private static final String log = NetworkUtils.class.getName();

    private static final String BASE_URL = "https://content.guardianapis.com/search?";

    public static String getBookInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String newsJSONString = null;
        Log.e(log,"error in getInfoBook");
        try {
            Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter("q", queryString)
                    .appendQueryParameter("order-by", "relevance")
                    .appendQueryParameter("api-key", "key")
                    .build();

            URL requestUrl = new URL(buildUri.toString());
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null)
                return null;

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line + "\n");
                line = reader.readLine();
            }
            if (buffer.length() == 0) {
                return null;   // stream was empty that is no point of parsing
            }

            newsJSONString = buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.d(log, newsJSONString);
            return newsJSONString;
        }
    }

}
