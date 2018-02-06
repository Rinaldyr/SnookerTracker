package com.example.rinaldy.snookertracker;

/**
 * Created by Rinaldy on 04/02/2018.
 */

public class Game {

    static final int NOTRED = 0; // not red to be pot
    static final int DONE = -1; // game over
    static final int RED = 1; // an enum as well as its value
    static final int YLW = 2;
    static final int GRN = 3;
    static final int BWN = 4;
    static final int BLU = 5;
    static final int PNK = 6;
    static final int BLK = 7;
    static final int FOUL = 4; // points for foul

    private int remaining; // available points in ideal case
    private int red_count;
    private int ylw_count;
    private int grn_count;
    private int bwn_count;
    private int blu_count;
    private int pnk_count;
    private int blk_count;

    Team team_1; // Has player 1 and 3
    Team team_2; // Has player 2 and 4
    private int turn; // 0 = player 1's turn -- Players are alternating in numbers for easy
                        // 1 = player 2's turn -- access. i.e. If it is player 0's turn, it means
                        // 2 = player 3's turn -- that he is of team 1, because even number.
                        // 3 = player 4's turn -- Similarly, team 2 will have odd enums.

    private int color_to_pot; // 1 = red, 2 = ylw ... 0 = all but red

    public Game(Team one, Team two) {
        this.team_1 = one;
        this.team_2 = two;
        this.turn = 0;
        this.remaining = 147;
        this.red_count = 15;
        this.ylw_count = this.grn_count = this.bwn_count = this.blu_count = this.pnk_count = this.blk_count = 1;
        this.color_to_pot = RED;
    }

    public void award(int score) {
        if ((turn & 1) == 0) { // even ? team 1 : team 2
            if (turn == 0)
                team_1.addPointsP1(score);
            else
                team_1.addPointsP2(score);
        } else {
            if (turn == 1)
                team_2.addPointsP1(score);
            else
                team_2.addPointsP2(score);
        }
        remaining = red_count != 0 ? 8 * red_count + 27 : ylw_count * YLW + grn_count * GRN + bwn_count * BWN
                                                        + blu_count * BLU + pnk_count * PNK + blk_count * BLK;
    }

    public boolean potRed() {
        if (red_count > 0) {
            --red_count;
            award(RED);
            color_to_pot = NOTRED;
            return true;
        }
        return false;
    }

    public boolean potYlw() {
        if (ylw_count > 0) {
            if (red_count == 0 && color_to_pot == YLW) {
                ylw_count--;
                award(YLW);
                color_to_pot = GRN;
                return true;
            }
            if (color_to_pot == NOTRED) {
                award(YLW);
                color_to_pot = red_count > 0 ? RED : YLW;
                return true;
            }
        }
        return false;
    }

    public boolean potGrn() {
        if (grn_count > 0) {
            if (ylw_count == 0 && color_to_pot == GRN) {
                grn_count--;
                award(GRN);
                color_to_pot = BWN;
                return true;
            }
            if (color_to_pot == NOTRED) {
                award(GRN);
                color_to_pot = red_count > 0 ? RED : YLW;
                return true;
            }
        }
        return false;
    }

    public boolean potBwn() {
        if (bwn_count > 0) {
            if (grn_count == 0 && color_to_pot == BWN) {
                bwn_count--;
                award(BWN);
                color_to_pot = BLU;
                return true;
            }
            if (color_to_pot == NOTRED) {
                award(BWN);
                color_to_pot = red_count > 0 ? RED : YLW;
                return true;
            }
        }
        return false;
    }

    public boolean potBlu() {
        if (blu_count > 0) {
            if (bwn_count == 0 && color_to_pot == BLU) {
                blu_count--;
                award(BLU);
                color_to_pot = PNK;
                return true;
            }
            if (color_to_pot == NOTRED) {
                award(BLU);
                color_to_pot = red_count > 0 ? RED : YLW;
                return true;
            }
        }
        return false;
    }

    public boolean potPnk() {
        if (pnk_count > 0) {
            if (blu_count == 0 && color_to_pot == PNK) {
                pnk_count--;
                award(PNK);
                color_to_pot = BLK;
                return true;
            }
            if (color_to_pot == NOTRED) {
                award(PNK);
                color_to_pot = red_count > 0 ? RED : YLW;
                return true;
            }
        }
        return false;
    }

    public boolean potBlk() {
        if (blk_count > 0) {
            if (pnk_count == 0 && color_to_pot == BLK) {
                blk_count--;
                award(BLK);
                color_to_pot = DONE;
                return true;
            }
            if (color_to_pot == NOTRED) {
                award(BLK);
                color_to_pot = red_count > 0 ? RED : YLW;
                return true;
            }
        }
        return false;
    }

    public void foul() {
        if ((turn & 1) == 0) {
            team_2.addPoints(FOUL);
        } else {
            team_1.addPoints(FOUL);
        }
        nextPlayer();
    }

    public String winner() {
        String winning_message = "";
        if (team_1.getPoints() > team_2.getPoints()) {
            winning_message += team_1.getName() + " has won with " + team_1.getPoints() + " points!";
        } else if (team_2.getPoints() > team_1.getPoints()) {
            winning_message += team_2.getName() + " has won with " + team_2.getPoints() + " points!!";
        } else {
            winning_message += "It's a tie! Uhh lucky?";
        }
        return winning_message;
    }

    public int getTurn() {
        return turn;
    }

    public Player getPlayer() {
        return turn == 0 ? team_1.getPlayers()[0] :
                turn == 1 ? team_2.getPlayers()[0] :
                        turn == 2 ? team_1.getPlayers()[1] : team_2.getPlayers()[1];
    }

    public void nextPlayer() {
        turn = (turn + 1) % 4;
        color_to_pot = red_count == 0 ? color_to_pot : RED;
    }

    public int getRemaining() {
        return remaining;
    }

    public int getRedCount() {
        return red_count;
    }

    public int getColorToPot() {
        return color_to_pot;
    }
}
