package com.example.rinaldy.snookertracker;

import java.util.ArrayList;

/**
 * Created by Rinaldy on 26/01/2018.
 */

public class Team {

    private String name;
    private int points;
    private Player[] players;

    public Team(String name) {
        this.name = name;
        this.points = 0;
        this.players = new Player[2];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void addPointsP1(int points) {
        addPoints(points);
        players[0].addPoints(points);
    }
    public void addPointsP2(int points) {
        addPoints(points);
        players[1].addPoints(points);
    }

    public void addPlayers(Player p, Player q) {
        this.players[0] = p;
        this.players[1] = q;
    }

    public Player[] getPlayers() {
        return this.players;
    }

    @Override
    public String toString() {
        StringBuilder names = new StringBuilder();
        for (Player p : players) {
            names.append(p.getName()).append("  ");
        }
        return "Team " + this.name + ": " + names + "\nScore: " + this.points;
    }
}
