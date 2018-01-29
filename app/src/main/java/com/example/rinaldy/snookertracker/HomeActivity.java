package com.example.rinaldy.snookertracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void openRules(View view) {
        Intent intent = new Intent(HomeActivity.this, RulesActivity.class);
        startActivity(intent);
    }

    public void openSetUp(View view) {
        Intent intent = new Intent(HomeActivity.this, SetUpActivity.class);
        startActivity(intent);
    }
}
