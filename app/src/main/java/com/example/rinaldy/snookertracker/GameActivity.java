package com.example.rinaldy.snookertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    static final int RED = 1;
    static final int YLW = 2;
    static final int GRN = 3;
    static final int BWN = 4;
    static final int BLU = 5;
    static final int PNK = 6;
    static final int BLK = 7;
    static final int FOUL = 4;

    int redCount;
    int ylwCount;
    int grnCount;
    int bwnCount;
    int bluCount;
    int pnkCount;
    int blkCount;

    private Button redButton;
    private Button ylwButton;
    private Button grnButton;
    private Button bwnButton;
    private Button bluButton;
    private Button pnkButton;
    private Button blkButton;

    private TextView team_1_score;
    private TextView team_2_score;
    private TextView player_message;
    private TextView player_name;
    private TextView player_score;

    private Team team_1;
    private Team team_2;
    private boolean turn; // true means team 1's turn
    private boolean redToPot;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(team_1.toString());
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(team_2.toString());
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(new StringBuilder("goodwork"));
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        TextView team_1_label = findViewById(R.id.team_1_label);
        TextView team_2_label = findViewById(R.id.team_2_label);
        player_message = (TextView) findViewById(R.id.yourturn);
        player_name = (TextView) findViewById(R.id.player_label);
        player_score = (TextView) findViewById(R.id.player_score);
        team_1_score = (TextView) findViewById(R.id.team_1_score);
        team_2_score = (TextView) findViewById(R.id.team_2_score);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        this.turn = this.redToPot = true;
        this.redCount = 15;
        this.ylwCount = this.grnCount = this.bwnCount = this.bluCount = this.pnkCount = this.blkCount = 1;
        this.redButton = (Button) findViewById(R.id.redButton);
        this.ylwButton = (Button) findViewById(R.id.ylwButton);
        this.grnButton = (Button) findViewById(R.id.grnButton);
        this.bwnButton = (Button) findViewById(R.id.bwnButton);
        this.bluButton = (Button) findViewById(R.id.bluButton);
        this.pnkButton = (Button) findViewById(R.id.pnkButton);
        this.blkButton = (Button) findViewById(R.id.blkButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            team_1 = new Team(getIntent().getStringExtra("team_1"));
            team_2 = new Team(getIntent().getStringExtra("team_2"));
            String player_1 = getIntent().getStringExtra("player_1");
            String player_2 = getIntent().getStringExtra("player_2");
            String player_3 = getIntent().getStringExtra("player_3");
            String player_4 = getIntent().getStringExtra("player_4");
            if (player_1 != null) team_1.addPlayer(new Player(player_1));
            if (player_2 != null) team_2.addPlayer(new Player(player_2));
            if (player_3 != null) team_1.addPlayer(new Player(player_3));
            if (player_4 != null) team_2.addPlayer(new Player(player_4));
        }
        team_1_label.setText(team_1.getName());
        team_2_label.setText(team_2.getName());

        String whoseTurn = team_1.getName() + "'s turn";
        player_message.setText(whoseTurn);

        Player p = team_1.getPlayers().get(0);
        player_name.setText(p.getName());
        player_score.setText(new StringBuilder(p.getPoints() + ""));
    }

    public void pot(View view) {
        String ball_color = view.getTag().toString();
        int score = 0;
        switch (ball_color) {
            case "RED": score = RED; redCount--; break;
            case "YLW": score = YLW; break;
            case "GRN": score = GRN; break;
            case "BWN": score = BWN; break;
            case "BLU": score = BLU; break;
            case "PNK": score = PNK; break;
            case "BLK": score = BLK; break;
        }
        //toggleButtons();
        if (turn) {
            team_1.addPoints(score);
            team_1.getPlayers().get(0).addPoints(score);
            team_1_score.setText(new StringBuilder(team_1.getPoints() + ""));

            Player p = team_1.getPlayers().get(0);
            player_name.setText(p.getName());
            player_score.setText(new StringBuilder(p.getPoints() + ""));
        }
        else {
            team_2.addPoints(score);
            team_2.getPlayers().get(0).addPoints(score);
            team_2_score.setText(new StringBuilder(team_2.getPoints() + ""));

            Player p = team_2.getPlayers().get(0);
            player_name.setText(p.getName());
            player_score.setText(new StringBuilder(p.getPoints() + ""));
        }
    }

    public void toggleButtons() {
        if (redCount <= 0) {
            this.redButton.setEnabled(false);
        }
    }

    public void nextPlayer(View view) {
        this.turn = !turn;
        String whose_turn;
        if (turn) {
            Player p = team_1.getPlayers().get(0);
            player_name.setText(p.getName());
            player_score.setText(new StringBuilder(p.getPoints() + ""));
            whose_turn = team_1.getName() + "'s turn";
        } else {
            Player p = team_2.getPlayers().get(0);
            player_name.setText(p.getName());
            player_score.setText(new StringBuilder(p.getPoints() + ""));
            player_name.setText(team_2.getPlayers().get(0).getName());
            whose_turn = team_2.getName() + "'s turn";
        }
        player_message.setText(whose_turn);
    }

    public void foul(View view) {
        if (turn) {
            team_2.addPoints(FOUL);
        } else {
            team_1.addPoints(FOUL);
        }
        nextPlayer(view);
    }

    public void endFrame(View view) {
        String winning_message = "";
        if (team_1.getPoints() > team_2.getPoints()) {
            winning_message += team_1.getName() + " has won with " + team_1.getPoints() + " points!";
        } else if (team_2.getPoints() > team_1.getPoints()) {
            winning_message += team_2.getName() + " has won with " + team_2.getPoints() + " points!!";
        } else {
            winning_message += "It's a tie! Well played!";
        }
        player_message.setText(winning_message);

        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(GameActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        }, 5000);
    }
}
