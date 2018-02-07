package com.example.rinaldy.snookertracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Set;

public class ReviewMatchesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_matches);

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        Set<String> s = prefs.getStringSet("results", null);
        if (s != null) {
            String[] listItems = new String[s.size()];

            int i = 0;
            for (String str : s) {
                listItems[i++] = str;
            }
            ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_listview, listItems);
            ListView mListView = (ListView) findViewById(R.id.list);
            mListView.setAdapter(adapter);
        }
    }
}
