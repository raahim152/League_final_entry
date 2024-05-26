package com.example.league_final_entry;

import java.util.Objects;
import java.io.Serializable;

public class Player implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private  String position;

    private double rating;

    private int goals_Scored;
    private int assists;

    public Player(String name, String position, double rating){
        this.name = name;
        this.position = position;
        this.rating = rating;
        this.goals_Scored = 0;
        this.assists = 0;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getGoals_Scored() {
        return goals_Scored;
    }

    public void setGoals_Scored(int goals_Scored) {
        this.goals_Scored = goals_Scored;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public void scoreGoal(){
        goals_Scored++;
    }

    public void assistGoal(){
        assists++;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public String toString() {
        return "Player Info: " + "Name: " + name + "Position: " + position + "Rating: " + rating + "Goals Scored: " + goals_Scored  +"Assists: " + assists + "\n";
    }
}
