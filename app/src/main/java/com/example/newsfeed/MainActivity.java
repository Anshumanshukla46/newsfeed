package com.example.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String log = MainActivity.class.getName();

    TextView titleView;
    EditText editText;
    Button websiteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleView = findViewById(R.id.title);
        editText = findViewById(R.id.editText);
        websiteButton = findViewById(R.id.buttonPanel);
    }

    public void clickWebsite(View view) {
        String query = editText.getText().toString();

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        // For Checking network and search field whether empty or not
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Log.i(log, "Search text : " + query);

            titleView.setText("Loading ...");

            new FetchNews(titleView).execute(query);
        } else {
            if (query.length() == 0)
                Toast.makeText(this, "Enter atleast three characters", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Check network connection", Toast.LENGTH_SHORT).show();
            titleView.setText("");
        }
    }
}