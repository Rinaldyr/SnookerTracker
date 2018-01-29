package com.example.rinaldy.snookertracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SetUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
    }

    public void onResetClick(View view) {
        EditText team_1 = (EditText) findViewById(R.id.team_1);
        EditText team_2 = (EditText) findViewById(R.id.team_2);
        EditText player_1 = (EditText) findViewById(R.id.player_1);
        EditText player_2 = (EditText) findViewById(R.id.player_2);

        team_1.setText("");
        team_2.setText("");
        player_1.setText("");
        player_2.setText("");
    }

    public void onRadioButtonClick(View view) {
        boolean two_player = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.play_radio_2p: {
                if (two_player)
                break;
            }
            case R.id.play_radio_4p: {
                if (two_player)
                break;
            }
        }
    }

    public void onStartClick(View view) {
        EditText team_1 = (EditText) findViewById(R.id.team_1);
        EditText team_2 = (EditText) findViewById(R.id.team_2);
        EditText player_1 = (EditText) findViewById(R.id.player_1);
        EditText player_2 = (EditText) findViewById(R.id.player_2);
        if (TextUtils.isEmpty(team_1.getText())
            || TextUtils.isEmpty(team_2.getText())
            || TextUtils.isEmpty(player_1.getText())
            || TextUtils.isEmpty(player_2.getText())) {
            Toast.makeText(this, "Please fill the forms", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(SetUpActivity.this, GameActivity.class);
            intent.putExtra("team_1", team_1.getText().toString());
            intent.putExtra("team_2", team_2.getText().toString());
            intent.putExtra("player_1", player_1.getText().toString());
            intent.putExtra("player_2", player_2.getText().toString());
            startActivity(intent);
        }
    }
}
