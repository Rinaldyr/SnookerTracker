package com.example.rinaldy.snookertracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class ResultActivity extends AppCompatActivity {

    String winning_team;
    String winning_score;
    String mvp;
    String mvp_score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] winner = getIntent().getStringArrayExtra("winner");
        winning_team = winner[0];
        winning_score = winner[1];
        mvp = winner[2];
        mvp_score = winner[3];

        TextView t = (TextView) findViewById(R.id.winning_team);
        TextView p = (TextView) findViewById(R.id.mvp);

        int accent = getResources().getColor(R.color.colorAccent);
        int primary = getResources().getColor(R.color.colorPrimary);
        int gold = getResources().getColor(R.color.colorYlwBallT);

        appendColoredText(t, winning_team, accent);
        appendColoredText(t, " has won with ", primary);
        appendColoredText(t, winning_score, gold);
        appendColoredText(t, " points!", primary);

        appendColoredText(p, mvp, accent);
        appendColoredText(p, " is the MVP with ", primary);
        appendColoredText(p, mvp_score, gold);
        appendColoredText(p, " points!", primary);

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);

        String results = getIntent().getStringExtra("results");
        Set<String> s = prefs.getStringSet("results", null);
        if (s == null) {
            s = new HashSet<>();
        }

        s.add(results);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet("results", s);
        editor.apply();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = mvp + " just scored " + mvp_score + " points in Snooker! Come join!";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Snooker Tracker");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

                Snackbar.make(view, "Thanks for sharing!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void backToHome(View view) {
         Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
         startActivity(intent);
    }

    public static void appendColoredText(TextView tv, String text, int color) {
        int start = tv.getText().length();
        tv.append(text);
        int end = tv.getText().length();

        Spannable spannableText = (Spannable) tv.getText();
        spannableText.setSpan(new ForegroundColorSpan(color), start, end, 0);
    }

}
