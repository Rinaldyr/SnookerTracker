package com.example.rinaldy.snookertracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SetUpActivity extends AppCompatActivity {

    EditText team_1;
    EditText team_2;
    EditText player_1;
    EditText player_2;
    EditText player_3;
    EditText player_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        this.team_1 = (EditText) findViewById(R.id.team_1);
        this.team_2 = (EditText) findViewById(R.id.team_2);
        this.player_1 = (EditText) findViewById(R.id.player_1);
        this.player_2 = (EditText) findViewById(R.id.player_2);
        this.player_3 = (EditText) findViewById(R.id.player_3);
        this.player_4 = (EditText) findViewById(R.id.player_4);
    }

    public void onResetClick(View view) {
        this.team_1.setText("");
        this.team_2.setText("");
        this.player_1.setText("");
        this.player_2.setText("");
        this.player_3.setText("");
        this.player_4.setText("");
    }

    public void onStartClick(View view) {
        if (TextUtils.isEmpty(this.team_1.getText())
            || TextUtils.isEmpty(this.team_2.getText())
            || TextUtils.isEmpty(this.player_1.getText())
            || TextUtils.isEmpty(this.player_2.getText())) {
            Toast.makeText(this, "Please fill the forms", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                .setTitle("Everybody Ready?")
                .setMessage("The game will commence!")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(SetUpActivity.this, GameActivity.class);
                        intent.putExtra("team_1", SetUpActivity.this.team_1.getText().toString());
                        intent.putExtra("team_2", SetUpActivity.this.team_2.getText().toString());
                        intent.putExtra("player_1", SetUpActivity.this.player_1.getText().toString());
                        intent.putExtra("player_2", SetUpActivity.this.player_2.getText().toString());
                        intent.putExtra("player_3", SetUpActivity.this.player_3.getText().toString());
                        intent.putExtra("player_4", SetUpActivity.this.player_4.getText().toString());
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .show();
        }
    }
}
