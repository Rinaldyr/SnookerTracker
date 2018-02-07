package com.example.rinaldy.snookertracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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

        String team_won = winning_team + " has won with " + winning_score + " score!";
        String mvp_won = mvp + " is the MVP with " + mvp_score + " score!";

        TextView t = (TextView) findViewById(R.id.winning_team);
        TextView p = (TextView) findViewById(R.id.mvp);

        t.setText(team_won);
        p.setText(mvp_won);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Thanks for sharing!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



}
