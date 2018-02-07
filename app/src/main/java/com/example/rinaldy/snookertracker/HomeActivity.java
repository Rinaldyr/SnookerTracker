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

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
    }

    public void openRules(View view) {
        Intent intent = new Intent(HomeActivity.this, RulesActivity.class);
        startActivity(intent);
    }

    public void openSetUp(View view) {
        Intent intent = new Intent(HomeActivity.this, SetUpActivity.class);
        startActivity(intent);
    }

    public void openMatches(View view) {
        Intent intent = new Intent(HomeActivity.this, ReviewMatchesActivity.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
