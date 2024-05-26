package com.example.league_final_entry;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class League implements Serializable{
    private static final long serialVersionUID = 1L;
    private String leagueName;
    private List<Team> teams;
    private List<Match> fixtures;
    private List<MatchResults> matchResults;

    public String getLeagueName() {
        return leagueName;
    }
    public League(String leagueName) {
        this.leagueName = leagueName;
        this.teams = new ArrayList<>();
        this.fixtures = new ArrayList<>();
        this.matchResults = new ArrayList<>();
    }
    public Team findTeamByName(String teamName) {
        for (Team team : teams) {
            if (team.getTeamName().equalsIgnoreCase(teamName)) {
                return team;
            }
        }
        return null;
    }
    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }
    public void addTeam(Team team) {
        teams.add(team);
    }
    public boolean doesTeamExist(String teamName) {
        return teams.stream().anyMatch(team -> team.getTeamName().equals(teamName));
    }
    public void removeTeam(Team team) {
        teams.remove(team);
    }
    public List<Team> getTeams() {
        return teams;
    }
    public Team getTeamByName(String teamName) {
        return teams.stream().filter(team -> team.getTeamName().equals(teamName)).findFirst().orElse(null);
    }
    public List<Match> generateFixtures() {
        fixtures.clear(); // Clear fixtures for every new season

        int numTeams = teams.size();
        List<Team> shuffledTeams = new ArrayList<>(teams);
        Collections.shuffle(shuffledTeams); // Shuffle teams accordingly

        // Create a copy of shuffledTeams to ensure every team plays every other team at least once
        List<Team> tempTeams = new ArrayList<>(shuffledTeams);

        for (int i = 0; i < numTeams; i++) {
            for (int j = i + 1; j < numTeams; j++) {
                Team homeTeam = tempTeams.get(i);
                Team awayTeam = tempTeams.get(j);
                Match match = new Match(homeTeam, awayTeam);
                fixtures.add(match);
            }
        }

        // Repeat the process to ensure every team plays every other team at most twice
        for (int round = 0; round < numTeams - 1; round++) {
            for (int i = 0; i < numTeams / 2; i++) {
                Team homeTeam = tempTeams.get(i);
                Team awayTeam = tempTeams.get(numTeams - 1 - i);

                // Skip if both teams are null (dummy team)
                if (homeTeam != null && awayTeam != null) {
                    Match match = new Match(homeTeam, awayTeam);
                    fixtures.add(match);
                }
            }

            // Rotate teams to create new matches for the next round
            Collections.rotate(tempTeams.subList(1, tempTeams.size()), 1);
        }

        return fixtures; // Return the list of fixtures
    }
    public List<String> displayFixtures(){
        List<String> fixturesList = new ArrayList<>();
        System.out.println("Fixtures:");
        for (int i = 0; i < fixtures.size(); i++) {
            String fixture = "Match " + (i + 1) + ": " + fixtures.get(i);
            //System.out.println(fixture);
            fixturesList.add(fixture);
        }
        return fixturesList;
    }
    public List<Match> getFixtures() {
        return fixtures;
    }
    public List<String> getTeamFixtures(String teamName) {
        List<String> teamFixtures = new ArrayList<>();
        for (Match match : fixtures) {
            if (match.getHomeTeam().getTeamName().equalsIgnoreCase(teamName) ||
                    match.getAwayTeam().getTeamName().equalsIgnoreCase(teamName)) {
                teamFixtures.add(match.toString());
            }
        }
        return teamFixtures;
    }
    public void simulateMatches(){
        matchResults.clear();

        for (Match match: fixtures){

            Team homeTeam = match.getHomeTeam();
            Team awayTeam = match.getAwayTeam();

            double homeTeamRating = homeTeam.calculateTeamRating();
            double awayTeamRating = awayTeam.calculateTeamRating();

            double totalTeamRating = homeTeamRating + awayTeamRating;
            double homeWinProbability = homeTeamRating/totalTeamRating;
            double drawProbability = (totalTeamRating - Math.abs(homeTeamRating - awayTeamRating)) / totalTeamRating;
            double result = Math.random();

            int homeGoals;
            int awayGoals;

            if (result < homeWinProbability) {
                // Home team wins
                homeGoals = (int) (Math.random() * 5); // Random number of goals (0-5)
                awayGoals = (int) (Math.random() * 4); // Random number of goals (0-4)
            } else if (result < homeWinProbability + drawProbability) {
                // Draw
                homeGoals = (int) (Math.random() * 3); // Random number of goals (0-3)
                awayGoals = (int) (Math.random() * 2); // Random number of goals (0-2)
            } else {
                // Away team wins
                homeGoals = (int) (Math.random() * 5); // Random number of goals (0-5)
                awayGoals = (int) (Math.random() * 4); // Random number of goals (0-4)
            }

            match.setHomeTeamGoals(homeGoals);
            match.setAwayTeamGoals(awayGoals);

            MatchResults matchResult = new MatchResults(match, homeGoals, awayGoals);
            matchResults.add(matchResult);
        }
        updateStandings();
    }
    public List<String> getMatchResults(String teamName) {
        List<String> results = new ArrayList<>();
        for (MatchResults matchResult : matchResults) {
            Match match = matchResult.getMatch();
            if (match.getHomeTeam().getTeamName().equalsIgnoreCase(teamName) ||
                    match.getAwayTeam().getTeamName().equalsIgnoreCase(teamName)) {
                String result = match.getHomeTeam().getTeamName() + " " +
                        match.getHomeTeamGoals() + " - " +
                        match.getAwayTeamGoals() + " " +
                        match.getAwayTeam().getTeamName();
                results.add(result);
            }
        }
        return results;
    }
    public List<String> displayResults() {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < fixtures.size(); i++) {
            Match match = fixtures.get(i);
            MatchResults mr = matchResults.get(i);
            String result = "Match " + (i + 1) + ": " + match.getHomeTeam().getTeamName() + " " +
                    match.getHomeTeamGoals() + " - " + match.getAwayTeamGoals() + " " +
                    match.getAwayTeam().getTeamName() + "|||" + mr;
            results.add(result);
        }
        return results;
    }
    public void updateStandings() {
        // Reset goals scored and conceded for all teams
        for (Team team : teams) {
            team.setGoalsScored(0);
            team.setGoalsConceded(0);
            team.setPoints(0);
            team.setWin(0);
            team.setDraw(0);
            team.setLoss(0);
            team.setMatchesPlayed(0); // Reset matches played
        }

        // Update goals scored and conceded based on match results
        for (MatchResults result : matchResults) {
            Team homeTeam = result.getMatch().getHomeTeam();
            Team awayTeam = result.getMatch().getAwayTeam();
            int homeGoals = result.getHomeTeamGoals();
            int awayGoals = result.getAwayTeamGoals();

            homeTeam.setGoalsScored(homeTeam.getGoalsScored() + homeGoals);
            homeTeam.setGoalsConceded(homeTeam.getGoalsConceded() + awayGoals);
            awayTeam.setGoalsScored(awayTeam.getGoalsScored() + awayGoals);
            awayTeam.setGoalsConceded(awayTeam.getGoalsConceded() + homeGoals);

            if (homeGoals > awayGoals) {
                homeTeam.setPoints(homeTeam.getPoints() + 3);
                homeTeam.incrementWins();
                awayTeam.incrementLoss();
            } else if (awayGoals > homeGoals) {
                awayTeam.setPoints(awayTeam.getPoints() + 3);
                awayTeam.incrementWins();
                homeTeam.incrementLoss();
            } else {
                homeTeam.setPoints(homeTeam.getPoints() + 1);
                awayTeam.setPoints(awayTeam.getPoints() + 1);
                homeTeam.incrementDraws();
                awayTeam.incrementDraws();
            }

            homeTeam.incrementMatchesPlayed();
            awayTeam.incrementMatchesPlayed();
        }

        // Sort teams based on points, goal difference, and goals scored
        teams.sort((team1, team2) -> {
            int pointsCompare = Integer.compare(team2.getPoints(), team1.getPoints()); // Sort in descending order of points
            if (pointsCompare != 0) {
                return pointsCompare;
            }
            int goalDifferenceCompare = Integer.compare(team2.goalDifference(), team1.goalDifference());
            if (goalDifferenceCompare != 0) {
                return goalDifferenceCompare;
            }
            return Integer.compare(team2.getGoalsScored(), team1.getGoalsScored()); // Sort by goals scored
        });
    }
    public List<String> getStandings() {
        updateStandings();
        List<String> standings = new ArrayList<>();
        standings.add("Team\tMatches Played\tWins\tDraw\tLoss\tPoints\tGoal Difference\tGoals Scored\tGoals Conceded");
        for (Team team : teams) {
            standings.add(String.format("%-15s%-15d%-6d%-6d%-6d%-8d%-16d%-14d%-16d",
                    team.getTeamName(), team.getMatchesPlayed(), team.getWin(), team.getDraw(), team.getLoss(),
                    team.getPoints(), team.goalDifference(), team.getGoalsScored(), team.getGoalsConceded()));
        }
        return standings;
    }
    public List<String> getTeamStandings(String teamName) {
        updateStandings();
        List<String> standings = new ArrayList<>();
        standings.add("Team\tMatches Played\tWins\tDraw\tLoss\tPoints\tGoal Difference\tGoals Scored\tGoals Conceded");
        for (Team team : teams) {
            if (team.getTeamName().equalsIgnoreCase(teamName)) {
                standings.add(String.format("%-15s%-15d%-6d%-6d%-6d%-8d%-16d%-14d%-16d",
                        team.getTeamName(), team.getMatchesPlayed(), team.getWin(), team.getDraw(), team.getLoss(),
                        team.getPoints(), team.goalDifference(), team.getGoalsScored(), team.getGoalsConceded()));
            }
        }
        return standings;
    }// made for search functionality

    public boolean isPlayerNameInUse(String playerName) {
        for (Team team : teams) {
            if (team.hasPlayerWithName(playerName)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return "League Name:" + leagueName + "\n" + "Teams: " + teams;
    }
}
