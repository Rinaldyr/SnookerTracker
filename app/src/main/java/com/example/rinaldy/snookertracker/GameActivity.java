package com.example.rinaldy.snookertracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    int basicText;
    int accentText;

    private Button foulButton;
    private Button nextButton;

    private TextView team_1_score;
    private TextView team_2_score;
    private TextView player_message;
    private TextView remaining;

    private TextView player_1_name;
    private TextView player_2_name;
    private TextView player_3_name;
    private TextView player_4_name;
    private TextView player_1_score;
    private TextView player_2_score;
    private TextView player_3_score;
    private TextView player_4_score;
    private TextView player_1_log;
    private TextView player_2_log;
    private TextView player_3_log;
    private TextView player_4_log;

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        basicText = getResources().getColor(R.color.colorBlkBallText);
        accentText = getResources().getColor(R.color.colorAccent);

        TextView team_1_label = findViewById(R.id.team_1_label);
        TextView team_2_label = findViewById(R.id.team_2_label);
        team_1_score = (TextView) findViewById(R.id.team_1_score);
        team_2_score = (TextView) findViewById(R.id.team_2_score);
        remaining = (TextView) findViewById(R.id.remaining);

        player_1_name = (TextView) findViewById(R.id.player_1_label);
        player_2_name = (TextView) findViewById(R.id.player_2_label);
        player_3_name = (TextView) findViewById(R.id.player_3_label);
        player_4_name = (TextView) findViewById(R.id.player_4_label);
        player_1_score = (TextView) findViewById(R.id.player_1_score);
        player_2_score = (TextView) findViewById(R.id.player_2_score);
        player_3_score = (TextView) findViewById(R.id.player_3_score);
        player_4_score = (TextView) findViewById(R.id.player_4_score);
        player_1_log = (TextView) findViewById(R.id.player_1_log);
        player_2_log = (TextView) findViewById(R.id.player_2_log);
        player_3_log = (TextView) findViewById(R.id.player_3_log);
        player_4_log = (TextView) findViewById(R.id.player_4_log);

        player_message = (TextView) findViewById(R.id.yourturn);
        foulButton = (Button) findViewById(R.id.foul);
        nextButton = (Button) findViewById(R.id.nextPlayer);

        Intent intent = getIntent();
        Team team_1 = new Team(intent.getStringExtra("team_1"));
        Team team_2 = new Team(intent.getStringExtra("team_2"));
        String player_1 = intent.getStringExtra("player_1");
        String player_2 = intent.getStringExtra("player_2");
        String player_3 = intent.getStringExtra("player_3");
        String player_4 = intent.getStringExtra("player_4");

        team_1.addPlayers(new Player(player_1), new Player(player_3));
        team_2.addPlayers(new Player(player_2), new Player(player_4));
        game = new Game(team_1, team_2);

        team_1_label.setText(team_1.getName());
        team_2_label.setText(team_2.getName());

        player_1_name.setTextColor(basicText);
        player_2_name.setTextColor(basicText);
        player_3_name.setTextColor(basicText);
        player_4_name.setTextColor(basicText);

        String status = "On a red (15 reds left).";
        player_message.setText(status);

        Player p1 = team_1.getPlayers()[0];
        player_1_name.setTextColor(accentText);
        player_1_name.setText(p1.getName());
        player_1_score.setTextColor(accentText);
        player_1_score.setText(new StringBuilder(p1.getPoints() + ""));

        Player p3 = team_1.getPlayers()[1];
        player_3_name.setText(p3.getName());
        player_3_score.setText(new StringBuilder(p3.getPoints() + ""));

        Player p2 = team_2.getPlayers()[0];
        player_2_name.setText(p2.getName());
        player_2_score.setText(new StringBuilder(p2.getPoints() + ""));

        Player p4 = team_2.getPlayers()[1];
        player_4_name.setText(p4.getName());
        player_4_score.setText(new StringBuilder(p4.getPoints() + ""));

        String rem = "Available: " + game.getRemaining();
        remaining.setText(rem);
    }

    public void pot(View view) {
        String ball_color = view.getTag().toString();
        switch (ball_color) {
            case "RED":
                if (game.potRed()) {
                    updateUILog(game.getTurn(), R.color.colorRedBallF);
                } break;
            case "YLW":
                if (game.potYlw()) {
                    updateUILog(game.getTurn(), R.color.colorYlwBallF);
                } break;
            case "GRN":
                if (game.potGrn()) {
                    updateUILog(game.getTurn(), R.color.colorGrnBallF);
                } break;
            case "BWN":
                if (game.potBwn()) {
                    updateUILog(game.getTurn(), R.color.colorBwnBallF);
                } break;
            case "BLU":
                if (game.potBlu()) {
                    updateUILog(game.getTurn(), R.color.colorBluBallF);
                } break;
            case "PNK":
                if (game.potPnk()) {
                    updateUILog(game.getTurn(), R.color.colorPnkBallF);
                } break;
            case "BLK":
                if (game.potBlk()) {
                    updateUILog(game.getTurn(), R.color.colorBlkBallF);
                } if (game.getColorToPot() == Game.DONE) {
                    Snackbar
                        .make(view, "Frame over, see the results.", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Next", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                frameOver();
                            }
                        })
                        .show();
                }
                break;
        }
        updateUI();
    }

    private void frameOver() {
        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
        intent.putExtra("winner", game.winner());
        startActivity(intent);
    }

    public void nextPlayer(View view) {
        this.game.nextPlayer();
        updateUI();
    }

    // foul score is added to the team score not individual.
    public void foul(View view) {
        this.game.foul();
        updateUI();
    }

    public void endFrame(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
            .setTitle("End the frame?")
            .setMessage("The game will be over.")
            .setCancelable(true)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    String message = "Frame over";
                    player_message.setText(message);
                    frameOver();
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            })
            .show();
    }

    public void updateUI() {
        String status = "";
        Player p = game.getPlayer();
        String score = p.getPoints() + "";
        int turn = game.getTurn();

        String rem = "Available: " + game.getRemaining();
        remaining.setText(rem);

        player_1_name.setTextColor(basicText);
        player_2_name.setTextColor(basicText);
        player_3_name.setTextColor(basicText);
        player_4_name.setTextColor(basicText);
        player_1_score.setTextColor(basicText);
        player_2_score.setTextColor(basicText);
        player_3_score.setTextColor(basicText);
        player_4_score.setTextColor(basicText);

        if (turn == 0) {
            player_1_name.setTextColor(accentText);
            player_1_score.setTextColor(accentText);
            player_1_score.setText(score);
            team_1_score.setText(new StringBuilder(game.team_1.getPoints() + ""));
        } else if (turn == 1) {
            player_2_name.setTextColor(accentText);
            player_2_score.setTextColor(accentText);
            player_2_score.setText(score);
            team_2_score.setText(new StringBuilder(game.team_2.getPoints() + ""));
        } else if (turn == 2) {
            player_3_name.setTextColor(accentText);
            player_3_score.setTextColor(accentText);
            player_3_score.setText(score);
            team_1_score.setText(new StringBuilder(game.team_1.getPoints() + ""));
        } else {
            player_4_name.setTextColor(accentText);
            player_4_score.setTextColor(accentText);
            player_4_score.setText(score);
            team_2_score.setText(new StringBuilder(game.team_2.getPoints() + ""));
        }

        int colour = game.getColorToPot();
        if (colour == Game.RED) {
            status += "On a red";
        } else if (colour == Game.NOTRED) {
            status += "On a colour";
        } else if (colour == Game.YLW) {
            status += "On yellow";
        } else if (colour == Game.GRN) {
            status += "On green";
        } else if (colour == Game.BWN) {
            status += "On brown";
        } else if (colour == Game.BLU) {
            status += "On blue";
        } else if (colour == Game.PNK) {
            status += "On pink";
        } else if (colour == Game.BLK) {
            status += "On black";
        } else { // game is over
            status = "Frame over!";
            foulButton.setEnabled(false);
            foulButton.setVisibility(View.INVISIBLE);
            nextButton.setEnabled(false);
            nextButton.setVisibility(View.INVISIBLE);
            player_message.setText(status);
            return;
        }

        if (game.getRedCount() != 0) {
            status += " (" + game.getRedCount() + " reds left).";
        } else {
            status += " (No more reds).";
        }
        player_message.setText(status);
    }

    public void updateUILog(int turn, int ball_color) {
        if ((turn & 1) == 0) { // team 1
            if (turn == 0) {
                appendColoredText(player_1_log, getResources().getColor(ball_color));
            } else {
                appendColoredText(player_3_log, getResources().getColor(ball_color));
            }
        } else {
            if (turn == 1) {
                appendColoredText(player_2_log, getResources().getColor(ball_color));
            } else {
                appendColoredText(player_4_log, getResources().getColor(ball_color));
            }
        }
    }

    public static void appendColoredText(TextView tv, int color) {
        int start = tv.getText().length();
        tv.append("O ");
        int end = tv.getText().length();

        Spannable spannableText = (Spannable) tv.getText();
        spannableText.setSpan(new ForegroundColorSpan(color), start, end, 0);
    }
}
