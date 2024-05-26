package com.example.league_final_entry;

import javafx.scene.control.Label;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Team implements Serializable{
    private static final long serialVersionUID = 1L;
    private String teamName;
    private List<Player> players;
    private int points;
    private int goalsScored;
    private int goalsConceded;

    private int matchesPlayed;
    private int win;
    private int loss;
    private int draw;

    public Team(String teamName) {
        this.teamName = teamName;
        this.players = new ArrayList<>();
        this.points = 0;
        this.goalsScored = 0;
        this.goalsConceded = 0;
        this.matchesPlayed = 0;
        this.win = 0;
        this.loss = 0;
        this.draw = 0;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public void incrementWins(){
        win++;
    }
    public void incrementLoss(){
        loss++;
    }
    public void incrementDraws(){
        draw++;
    }
    public int getMatchesPlayed() {
        return matchesPlayed;
    }
    public Player findPlayerByName(String playerName) {
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(playerName)) {
                return player;
            }
        }
        return null;
    }
    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }
    public void incrementMatchesPlayed(){
        matchesPlayed++;
    }
    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public boolean addPlayer(Player player, League league, Label message) {
        // Check if the player's name already exists in any other team
        if (league.isPlayerNameInUse(player.getName())) {
            message.setText("Player with name " + player.getName() + " already exists in another team.");
            return true;
        } else {
            players.add(player);
            message.setText(player.getName() + " has been added to " + teamName);
            return false;
        }
    }
    public boolean hasPlayerWithName(String playerName) {
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(playerName)) {
                return true;
            }
        }
        return false;
    }
    public void removePlayer(Player player) {
        players.remove(player);
    }
    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public int getGoalsScored() {
        return goalsScored;
    }
    public int getGoalsConceded() {
        return goalsConceded;
    }
    public void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }
    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }
    public int getTotalGoalsScored(){
        int totalGoals = 0;

        for (Player player : players) {
            totalGoals += player.getGoals_Scored();
        }
        return totalGoals;
    }
    public int getTotalAssists() {
        int totalAssists = 0;
        for (Player player : players) {
            totalAssists += player.getAssists();
        }
        return totalAssists;
    }
    public void clearPlayers(){
        players.clear();
    }
    public boolean hasPlayer(Player player){
        return players.contains(player);
    }
    public double calculateTeamRating() {
        if (players.isEmpty()) {
            return 0;
        }

        double totalRating = 0;
        for (Player player : players) {
            totalRating += player.getRating();
        }

        return totalRating / players.size();
    }
    public int goalDifference(){
        return goalsScored - goalsConceded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(teamName, team.teamName) && Objects.equals(players, team.players);
    }
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("\nTeam Name: ").append(teamName).append(" ");
        sb.append("\nPlayers: ");
        for (Player player: players){
            sb.append(player).append(" ");
        }
        sb.append("\nTotal Goals Scored: ").append(getTotalGoalsScored()).append("\n");
        sb.append("Total Assists: ").append(getTotalAssists()).append("\n");
        sb.append("Points: ").append(points).append("\n");
        sb.append("Goals Scored: ").append(goalsScored).append("\n");
        sb.append("Goals Conceded: ").append(goalsConceded).append("\n");
        sb.append("Goals Conceded: ").append(goalsConceded).append("\n");

        return sb.toString();
    }

}

