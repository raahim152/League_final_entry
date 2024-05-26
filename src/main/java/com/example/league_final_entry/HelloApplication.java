package com.example.league_final_entry;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;


public class HelloApplication extends Application {

    private League league;

    public HelloApplication() {
        this.league = Utility.loadLeague("LeagueData.ser");
        if (league == null) {
            // Handle case where loading fails
            System.err.println("Failed to load league data. Initializing with default values.");
            this.league = new League("Premier League"); // Create a default league
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        GridPane signInPanel = new GridPane();
        signInPanel.setAlignment(Pos.CENTER);
        signInPanel.setPadding(new Insets(10));
        signInPanel.setHgap(10);
        signInPanel.setVgap(10);
        signInPanel.setMaxHeight(300);
        signInPanel.setMaxWidth(300);

        signInPanel.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white");

        Label headingLabel = new Label("League System");
        headingLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        GridPane.setColumnSpan(headingLabel, 2);
        signInPanel.add(headingLabel, 0, 0);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        signInPanel.add(usernameLabel, 0, 1);
        signInPanel.add(usernameField, 1, 1);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        signInPanel.add(passwordLabel, 0, 2);
        signInPanel.add(passwordField, 1, 2);


        Button signInButton = new Button("Sign In");
        signInPanel.add(signInButton, 1, 3);

        Label messageText = new Label();
        signInPanel.add(messageText, 1, 4);

        StackPane root = new StackPane();
        root.setPadding(new Insets(10));

        Image backgroundImage = new Image("E:\\OOP\\League_Final_Entry\\League_Final_Entry\\src\\main\\java\\com\\example\\league_final_entry\\OIP.jpeg");
        BackgroundSize backgroundSize = new BackgroundSize(500, 500, false, false, false, false);
        BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImg);
        root.setBackground(background);

        root.getChildren().add(signInPanel);
        Scene signInScene = new Scene(root, 500, 500);
        primaryStage.setScene(signInScene);
        primaryStage.show();

        signInButton.setOnAction(e -> {
            String userName = usernameField.getText();
            String password = passwordField.getText();

            if (validateUserInfo(userName, password)) {
                primaryStage.setTitle("League Management System");
                VBox mainMenuPanel = createMainMenuPanel(primaryStage);
                BorderPane borderPane = new BorderPane();
                borderPane.setCenter(mainMenuPanel);

                Scene scene = new Scene(borderPane, 500, 500);
                primaryStage.setScene(scene);
            } else {
                messageText.setText("Invalid username or password");
            }
        });

    }
    private boolean validateUserInfo(String userName, String password){
        return "admin".equals(userName) && "admin".equals(password);
    }
    private VBox createMainMenuPanel(Stage primaryStage){
        VBox mainMenuPanel = new VBox(10);
        mainMenuPanel.setPadding(new Insets(10));
        mainMenuPanel.setAlignment(Pos.CENTER);

        Image backgroundImage1 = new Image("E:\\OOP\\League_Final_Entry\\League_Final_Entry\\src\\main\\java\\com\\example\\league_final_entry\\OIP.jpeg");
        BackgroundSize backgroundSize1 = new BackgroundSize(500, 500, false, false, false, false);
        BackgroundImage backgroundImg1 = new BackgroundImage(backgroundImage1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize1);
        Background background1 = new Background(backgroundImg1);
        mainMenuPanel.setBackground(background1);

        Button manageTeamsBtn = new Button("Manage Teams");
        Button generateFixturesBtn = new Button("Generate Fixtures");
        Button simulateMatchesBtn = new Button("Simulate Matches");
        Button displayStandingsBtn = new Button("Display Standings");
        Button displayFixturesBtn = new Button("Display Fixtures");
        Button displayResultsBtn = new Button("Display Results");
        Button saveData = new Button("Save Data");
        Button exitBtn = new Button("Exit");
        Label check = new Label();

        manageTeamsBtn.setOnAction(e -> openTeamManagementWindow());
        generateFixturesBtn.setOnAction(e -> {
            Label response = new Label();
            if (league.getTeams().size() < 2 ){
                response.setText("Insufficient Teams, need at least 2 for league to begin");
            } else if (league.getTeams().size() % 2 != 0) {
                response.setText("The league cannot start with an odd number of teams.");
            } else {
                Stage generateFixturesStage = new Stage();
                VBox generateFixturesPanel = new VBox(10);
                generateFixturesPanel.setPadding(new Insets(10));

                Image backgroundImage = new Image("E:\\OOP\\League_Final_Entry\\League_Final_Entry\\src\\main\\java\\com\\example\\league_final_entry\\OIP.jpeg");
                BackgroundSize backgroundSize = new BackgroundSize(500, 500, false, false, false, false);
                BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
                Background background = new Background(backgroundImg);
                generateFixturesPanel.setBackground(background);

                Button generateFixturesButton = new Button("Generate Fixtures");
                generateFixturesButton.setOnAction(event -> {
                    List<Match> fixtures = league.generateFixtures();
                    response.setText("Generated Fixtures");
                });

                TextArea fixturesTextArea = new TextArea();
                fixturesTextArea.setEditable(false);
                fixturesTextArea.setPrefHeight(200);

                Button displayFixturesButton = new Button("Display Fixtures");
                displayFixturesButton.setOnAction(event -> {
                    List<String> displayFixtures = league.displayFixtures();
                    StringBuilder fixturesText = new StringBuilder();
                    for (String fixture : displayFixtures) {
                        fixturesText.append(fixture).append("\n");
                    }
                    fixturesTextArea.setText(fixturesText.toString());
                });

                Button returnButton = new Button("Return");
                returnButton.setOnAction(event -> generateFixturesStage.close());

                generateFixturesPanel.getChildren().addAll(
                        generateFixturesButton, response, displayFixturesButton,
                        fixturesTextArea, returnButton); // Added fixturesTextArea

                Scene generateFixturesScene = new Scene(generateFixturesPanel, 500, 500);
                generateFixturesStage.setScene(generateFixturesScene);
                generateFixturesStage.setTitle("Generate Fixtures");
                generateFixturesStage.show();
            }
        });
        displayFixturesBtn.setOnAction(e -> {
            Label response = new Label();
            if (league.getFixtures().isEmpty()) {
                response.setText("Fixtures Not Generated, Please generate fixtures first.");
            } else {
                Stage displayFixturesStage = new Stage();
                VBox displayFixturesPanel = new VBox(10);
                displayFixturesPanel.setPadding(new Insets(10));

                Image backgroundImage = new Image("E:\\OOP\\League_Final_Entry\\League_Final_Entry\\src\\main\\java\\com\\example\\league_final_entry\\OIP.jpeg");
                BackgroundSize backgroundSize = new BackgroundSize(500, 500, false, false, false, false);
                BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
                Background background = new Background(backgroundImg);
                displayFixturesPanel.setBackground(background);

                TextArea fixturesTextArea = new TextArea();
                fixturesTextArea.setEditable(false);
                fixturesTextArea.setPrefHeight(200);

                Button returnButton = new Button("Return");
                returnButton.setOnAction(event -> displayFixturesStage.close());

                List<String> displayFixtures = league.displayFixtures();
                StringBuilder fixturesText = new StringBuilder();
                for (String fixture : displayFixtures) {
                    fixturesText.append(fixture).append("\n");
                }
                fixturesTextArea.setText(fixturesText.toString());

                TextField searchField = new TextField();
                Button searchButton = new Button("Search");
                searchButton.setOnAction(event -> {
                    String searchTerm = searchField.getText().trim();
                    List<String> teamFixtures = league.getTeamFixtures(searchTerm);
                    if (teamFixtures.isEmpty()) {
                        response.setText("No fixtures found for team: " + searchTerm);
                    } else {
                        StringBuilder fixturesTexts = new StringBuilder();
                        for (String fixture : teamFixtures) {
                            fixturesTexts.append(fixture).append("\n");
                        }
                        fixturesTextArea.setText(fixturesTexts.toString());
                    }
                });


                displayFixturesPanel.getChildren().addAll(
                        fixturesTextArea,searchButton,searchField, returnButton);

                Scene displayFixturesScene = new Scene(displayFixturesPanel, 500, 500);
                displayFixturesStage.setScene(displayFixturesScene);
                displayFixturesStage.setTitle("Display Fixtures");
                displayFixturesStage.show();
            }
        });
        simulateMatchesBtn.setOnAction(e -> {
            Label response = new Label();
            if (league.getTeams().size() < 2) {
                response.setText("Insufficient Teams, need at least 2 for league to begin.");
            } else if (league.getTeams().size() % 2 != 0) {
                response.setText("The league cannot start with an odd number of teams.");
            } else if (league.getFixtures().isEmpty()) {
                response.setText("Fixtures Not Generated, Please generate fixtures first.");
            } else {
                Stage simulateFixturesStage = new Stage();
                VBox simulateFixturesPanel = new VBox(10);
                simulateFixturesPanel.setPadding(new Insets(10));

                Image backgroundImage = new Image("E:\\OOP\\League_Final_Entry\\League_Final_Entry\\src\\main\\java\\com\\example\\league_final_entry\\OIP.jpeg");
                BackgroundSize backgroundSize = new BackgroundSize(500, 500, false, false, false, false);
                BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
                Background background = new Background(backgroundImg);
                simulateFixturesPanel.setBackground(background);

                TextArea fixturesTextArea = new TextArea();
                fixturesTextArea.setEditable(false);
                fixturesTextArea.setPrefHeight(200);

                Button simulateButton = new Button("Simulate");
                simulateButton.setOnAction(event -> {
                    league.simulateMatches();
                    response.setText("Matches Simulated");
                });

                Button displayResultsButton = new Button("Display Results");
                displayResultsButton.setOnAction(event -> {
                    StringBuilder fixturesText = new StringBuilder();
                    List<String> matchResults = league.displayResults();  // Assuming displayResults returns List<String>
                    for (String result : matchResults) {
                        fixturesText.append(result).append("\n");
                    }
                    fixturesTextArea.setText(fixturesText.toString());
                });


                Button returnButton = new Button("Return");
                returnButton.setOnAction(event -> simulateFixturesStage.close());

                simulateFixturesPanel.getChildren().addAll(
                        response, simulateButton, displayResultsButton,
                        fixturesTextArea,returnButton);

                Scene simulateFixturesScene = new Scene(simulateFixturesPanel, 500, 500);
                simulateFixturesStage.setScene(simulateFixturesScene);
                simulateFixturesStage.setTitle("Simulate Fixtures");
                simulateFixturesStage.show();
            }
        });
        displayResultsBtn.setOnAction(e -> {
            Stage displayResults = new Stage();
            VBox dispResults = new VBox(10);
            dispResults.setPadding(new Insets(10));

            Image backgroundImage = new Image("E:\\OOP\\League_Final_Entry\\League_Final_Entry\\src\\main\\java\\com\\example\\league_final_entry\\OIP.jpeg");
            BackgroundSize backgroundSize = new BackgroundSize(500, 500, false, false, false, false);
            BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImg);
            dispResults.setBackground(background);

            Label response = new Label();
            TextArea fixturesTextArea = new TextArea();
            fixturesTextArea.setEditable(false);
            fixturesTextArea.setPrefHeight(200);

            Button displayResultsButton = new Button("Display Results");
            displayResultsButton.setOnAction(event -> {
                StringBuilder fixturesText = new StringBuilder();
                List<String> matchResults = league.displayResults();
                for (String result : matchResults) {
                    fixturesText.append(result).append("\n");
                }
                fixturesTextArea.setText(fixturesText.toString());
            });

            TextField searchField = new TextField();
            Button searchButton = new Button("Search");
            searchButton.setOnAction(event -> {
                String searchTerm = searchField.getText().trim();
                List<String> teamFixtures = league.getMatchResults(searchTerm);
                if (teamFixtures.isEmpty()) {
                    response.setText("No fixtures found for team: " + searchTerm);
                } else {
                    StringBuilder fixturesTexts = new StringBuilder();
                    for (String fixture : teamFixtures) {
                        fixturesTexts.append(fixture).append("\n");
                    }
                    fixturesTextArea.setText(fixturesTexts.toString());
                }
            });

            Button returnBack= new Button("Return");
            returnBack.setOnAction(g ->displayResults.close());

            dispResults.getChildren().addAll(displayResultsButton,fixturesTextArea, searchField, searchButton, returnBack);
            Scene dispRes = new Scene(dispResults, 500, 500);
            displayResults.setScene(dispRes);
            displayResults.setTitle("Display Results");
            displayResults.show();
        });
        displayStandingsBtn.setOnAction(e -> {
            Label response = new Label();
            if (league.getFixtures().isEmpty()) {
                response.setText("Fixtures Not Generated, Please generate fixtures first.");
            } else {
                Stage displayStandingsStage = new Stage();
                VBox displayStandingsPanel = new VBox(10);
                displayStandingsPanel.setPadding(new Insets(10));

                Image backgroundImage = new Image("E:\\OOP\\League_Final_Entry\\League_Final_Entry\\src\\main\\java\\com\\example\\league_final_entry\\OIP.jpeg");
                BackgroundSize backgroundSize = new BackgroundSize(800, 600, false, false, false, false);
                BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
                Background background = new Background(backgroundImg);
                displayStandingsPanel.setBackground(background);

                TableView<Team> standingsTable = new TableView<>();
                standingsTable.setEditable(false);

                TableColumn<Team, String> teamNameColumn = new TableColumn<>("Team");
                teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));

                TableColumn<Team, Integer> matchesPlayedColumn = new TableColumn<>("Matches Played");
                matchesPlayedColumn.setCellValueFactory(new PropertyValueFactory<>("matchesPlayed"));

                TableColumn<Team, Integer> winsColumn = new TableColumn<>("Wins");
                winsColumn.setCellValueFactory(new PropertyValueFactory<>("win"));

                TableColumn<Team, Integer> drawsColumn = new TableColumn<>("Draw");
                drawsColumn.setCellValueFactory(new PropertyValueFactory<>("draw"));

                TableColumn<Team, Integer> lossesColumn = new TableColumn<>("Loss");
                lossesColumn.setCellValueFactory(new PropertyValueFactory<>("loss"));

                TableColumn<Team, Integer> pointsColumn = new TableColumn<>("Points");
                pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

                TableColumn<Team, Integer> goalDifferenceColumn = new TableColumn<>("Goal Difference");
                goalDifferenceColumn.setCellValueFactory(cellData ->
                        new SimpleIntegerProperty(cellData.getValue().goalDifference()).asObject());

                TableColumn<Team, Integer> goalsScoredColumn = new TableColumn<>("Goals Scored");
                goalsScoredColumn.setCellValueFactory(new PropertyValueFactory<>("goalsScored"));

                TableColumn<Team, Integer> goalsConcededColumn = new TableColumn<>("Goals Conceded");
                goalsConcededColumn.setCellValueFactory(new PropertyValueFactory<>("goalsConceded"));

                standingsTable.getColumns().addAll(teamNameColumn, matchesPlayedColumn, winsColumn, drawsColumn, lossesColumn, pointsColumn, goalDifferenceColumn, goalsScoredColumn, goalsConcededColumn);

                // Load data into the table
                standingsTable.setItems(FXCollections.observableArrayList(league.getTeams()));

                TextField searchField = new TextField();
                searchField.setPromptText("Search for a team...");
                Button searchButton = new Button("Search");
                searchButton.setOnAction(event -> {
                    String searchTerm = searchField.getText().trim();
                    Team searchedTeam = league.findTeamByName(searchTerm);
                    if (searchedTeam != null) {
                        standingsTable.setItems(FXCollections.observableArrayList(searchedTeam));
                        response.setText("");
                    } else {
                        response.setText("No standings found for team: " + searchTerm);
                        standingsTable.setItems(FXCollections.observableArrayList(league.getTeams()));
                    }
                });

                Button returnButton = new Button("Return");
                returnButton.setOnAction(event -> displayStandingsStage.close());

                HBox searchPanel = new HBox(10, searchField, searchButton);
                searchPanel.setAlignment(Pos.CENTER);
                VBox bottomPanel = new VBox(10, searchPanel, response, returnButton);
                bottomPanel.setAlignment(Pos.CENTER);

                displayStandingsPanel.getChildren().addAll(standingsTable, bottomPanel);

                Scene displayStandingsScene = new Scene(displayStandingsPanel, 800, 600);
                displayStandingsStage.setScene(displayStandingsScene);
                displayStandingsStage.setTitle("Display Standings");
                displayStandingsStage.show();
            }
        });
        saveData.setOnAction(e -> {
            if (league != null) {
                Utility.saveLeague(league,"LeagueData.ser");
                check.setText("Data has been saved");
            } else {
                check.setText("Cannot save null data");
            }
        });
        exitBtn.setOnAction(e -> primaryStage.close());


        mainMenuPanel.getChildren().addAll(manageTeamsBtn,  generateFixturesBtn,displayFixturesBtn,simulateMatchesBtn,displayResultsBtn, displayStandingsBtn,saveData,exitBtn,check);

        return mainMenuPanel;

    }
    private VBox createTeamManagementPanel(Stage primaryStage){
        VBox teamsManagementPanel = new VBox(10);
        teamsManagementPanel.setPadding(new Insets(10));
        teamsManagementPanel.setAlignment(Pos.CENTER);

        Button addTeamBtn = new Button("Add Team");
        Button removeTeamBtn = new Button("Remove Team");
        Button editTeamBtn = new Button("Edit Team");
        Button addPlayerBtn = new Button("Add Player");
        Button removePlayerBtn = new Button("Remove Player");
        Button editPlayerBtn = new Button("Edit Player Information");
        Button showTeamInfoBtn = new Button("Show Team Information");
        Button returnButton = new Button("Return to Menu");

        returnButton.setOnAction(e -> primaryStage.close());

        addTeamBtn.setOnAction(e -> {
            Stage addTeamStage = new Stage();
            VBox addTeamPanel = new VBox(10);
            addTeamPanel.setPadding(new Insets(10));

            Image backgroundImage = new Image("E:\\OOP\\League_Final_Entry\\League_Final_Entry\\src\\main\\java\\com\\example\\league_final_entry\\OIP.jpeg");
            BackgroundSize backgroundSize = new BackgroundSize(500, 500, false, false, false, false);
            BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImg);
            addTeamPanel.setBackground(background);

            TextField teamNameField = new TextField();
            teamNameField.setAlignment(Pos.CENTER);
            Label response = new Label();
            response.setAlignment(Pos.TOP_CENTER);
            Button next = new Button("Next");
            next.setAlignment(Pos.CENTER);
            Button returnBack = new Button("Return");
            returnBack.setAlignment(Pos.CENTER);
            returnBack.setOnAction(g -> addTeamStage.close());

            next.setOnAction(f -> {
                String teamName = teamNameField.getText();
                if (teamName.isEmpty()) {
                    response.setText("Team Name cannot be empty");
                } else if (league.doesTeamExist(teamName)) {
                    response.setText("Team Name already exists");
                } else if (league.getTeams().size() > 10) {
                    response.setText("League is full. Remove teams or add to another league.");
                } else {
                    // Proceed to add player screen
                    Stage addPlayerStage = new Stage();
                    VBox addPlayerPanel = new VBox(10);
                    addPlayerPanel.setPadding(new Insets(10));

                    Image backgroundImage1 = new Image("E:\\OOP\\League_Final_Entry\\League_Final_Entry\\src\\main\\java\\com\\example\\league_final_entry\\OIP.jpeg");
                    BackgroundSize backgroundSize1 = new BackgroundSize(500, 500, false, false, false, false);
                    BackgroundImage backgroundImg1 = new BackgroundImage(backgroundImage1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize1);
                    Background background1 = new Background(backgroundImg1);
                    addPlayerPanel.setBackground(background1);

                    TextField playerNameField = new TextField();
                    playerNameField.setAlignment(Pos.CENTER);
                    TextField playerPositionField = new TextField();
                    playerPositionField.setAlignment(Pos.CENTER);
                    TextField playerRatingField = new TextField();
                    playerRatingField.setAlignment(Pos.CENTER);
                    Label playerResponse = new Label();
                    playerResponse.setAlignment(Pos.TOP_CENTER);
                    Button addPlayer = new Button("Add Player");
                    addPlayer.setAlignment(Pos.CENTER);
                    Button returnToTeam = new Button("Return");
                    returnToTeam.setAlignment(Pos.CENTER);
                    returnToTeam.setOnAction(h -> addPlayerStage.close());

                    addPlayer.setOnAction(g -> {
                        String playerName = playerNameField.getText();
                        String playerPosition = playerPositionField.getText();
                        String playerRating = playerRatingField.getText();

                        if (playerName.isEmpty() || playerPosition.isEmpty() || playerRating.isEmpty()) {
                            playerResponse.setText("All fields must be filled");
                        } else {
                            try {
                                double rating = Double.parseDouble(playerRating);
                                Team newTeam = new Team(teamName);
                                if ((newTeam.addPlayer(new Player(playerName, playerPosition, rating), league, playerResponse)) == false ) {
                                    league.addTeam(newTeam);
                                }
                                //playerResponse.setText(playerName + " has been added to " + teamName);

                            } catch (NumberFormatException ex) {
                                playerResponse.setText("Player rating must be a number");
                            }
                        }
                    });

                    addPlayerPanel.getChildren().addAll(
                            new Label("Enter Player Name:"), playerNameField,
                            new Label("Enter Player Position:"), playerPositionField,
                            new Label("Enter Player Rating:"), playerRatingField,
                            addPlayer, returnToTeam, playerResponse,response
                    );

                    Scene addPlayerScene = new Scene(addPlayerPanel, 300, 350);
                    addPlayerStage.setScene(addPlayerScene);
                    addPlayerStage.setTitle("Add Player");
                    addPlayerStage.show();
                }
            });

            addTeamPanel.getChildren().addAll(
                    new Label("Enter Team Name:"), teamNameField,
                    next, returnBack, response
            );

            Scene addTeamScene = new Scene(addTeamPanel, 300, 350);
            addTeamStage.setScene(addTeamScene);
            addTeamStage.setTitle("Add Team");
            addTeamStage.show();
        });
        removeTeamBtn.setOnAction(e -> {
            Stage removeTeamStage = new Stage();
            VBox removeTeamPanel = new VBox(10);
            removeTeamPanel.setPadding(new Insets(10));

            Image backgroundImage = new Image("E:\\OOP\\League_Final_Entry\\League_Final_Entry\\src\\main\\java\\com\\example\\league_final_entry\\OIP.jpeg");
            BackgroundSize backgroundSize = new BackgroundSize(500, 500, false, false, false, false);
            BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImg);
            removeTeamPanel.setBackground(background);

            TextField teamNameField = new TextField();
            teamNameField.setAlignment(Pos.CENTER);
            Label response = new Label();
            response.setAlignment(Pos.TOP_CENTER);

            Button remove = new Button("Remove Team");
            remove.setAlignment(Pos.CENTER);
            Button returnBack = new Button("Return");
            returnBack.setAlignment(Pos.CENTER);
            returnBack.setOnAction(g -> removeTeamStage.close());

            remove.setOnAction(f -> {
                String teamName = teamNameField.getText();
                if (teamName.isEmpty()) {
                    response.setText("Team Name cannot be empty");
                } else if (!league.doesTeamExist(teamName)) {
                    response.setText("Team Name does not exist");
                } else if (league.getTeams().size() <= 2) {
                    response.setText("Cannot remove team. League must have at least 2 teams.");
                } else {
                    Team teamToRemove = league.getTeamByName(teamName);
                    if (teamToRemove != null) {
                        league.removeTeam(teamToRemove);
                        response.setText(teamName + " has been removed.");
                    } else {
                        response.setText("Error removing team.");
                    }
                }
            });

            removeTeamPanel.getChildren().addAll(teamNameField, response, remove, returnBack);

            Scene removeTeamScene = new Scene(removeTeamPanel, 300, 350);
            removeTeamStage.setScene(removeTeamScene);
            removeTeamStage.setTitle("Remove Team");
            removeTeamStage.show();
        });
        editTeamBtn.setOnAction(e -> {
            Stage editTeamStage = new Stage();
            VBox editTeamPanel = new VBox(10);
            editTeamPanel.setPadding(new Insets(10));

            Image backgroundImage = new Image("E:\\OOP\\League_Final_Entry\\League_Final_Entry\\src\\main\\java\\com\\example\\league_final_entry\\OIP.jpeg");
            BackgroundSize backgroundSize = new BackgroundSize(500, 500, false, false, false, false);
            BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImg);
            editTeamPanel.setBackground(background);

            TextField currentTeamNameField = new TextField();
            currentTeamNameField.setAlignment(Pos.CENTER);
            currentTeamNameField.setPromptText("Current Team Name");
            TextField newTeamNameField = new TextField();
            newTeamNameField.setAlignment(Pos.CENTER);
            newTeamNameField.setPromptText("New Team Name");
            Label response = new Label();
            response.setAlignment(Pos.CENTER);

            Button edit = new Button("Edit Team");
            edit.setAlignment(Pos.CENTER);
            Button returnBack = new Button("Return");
            returnBack.setAlignment(Pos.CENTER);
            returnBack.setOnAction(g -> editTeamStage.close());

            edit.setOnAction(f -> {
                String currentTeamName = currentTeamNameField.getText();
                String newTeamName = newTeamNameField.getText();
                if (currentTeamName.isEmpty() || newTeamName.isEmpty()) {
                    response.setText("Team names cannot be empty");
                } else if (!league.doesTeamExist(currentTeamName)) {
                    response.setText("Current team name does not exist");
                } else if (league.doesTeamExist(newTeamName)) {
                    response.setText("New team name already exists");
                } else {
                    Team teamToEdit = league.getTeamByName(currentTeamName);
                    if (teamToEdit != null) {
                        teamToEdit.setTeamName(newTeamName);
                        response.setText("Team name updated from " + currentTeamName + " to " + newTeamName);
                    } else {
                        response.setText("Error editing team.");
                    }
                }
            });

            editTeamPanel.getChildren().addAll(currentTeamNameField, newTeamNameField, response, edit, returnBack);

            Scene editTeamScene = new Scene(editTeamPanel, 300, 350);
            editTeamStage.setScene(editTeamScene);
            editTeamStage.setTitle("Edit Team");
            editTeamStage.show();
        });

        addPlayerBtn.setOnAction(e -> {
            Stage addPlayerStage = new Stage();
            VBox addPlayerPanel = new VBox(10);
            addPlayerPanel.setPadding(new Insets(10));

            Image backgroundImage = new Image("E:\\OOP\\League_Final_Entry\\League_Final_Entry\\src\\main\\java\\com\\example\\league_final_entry\\OIP.jpeg");
            BackgroundSize backgroundSize = new BackgroundSize(500, 500, false, false, false, false);
            BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImg);
            addPlayerPanel.setBackground(background);

            ComboBox<String> teamComboBox = new ComboBox<>();
            teamComboBox.setPromptText("Select Team");
            for (Team team : league.getTeams()) {
                teamComboBox.getItems().add(team.getTeamName());
            }

            TextField playerNameField = new TextField();
            playerNameField.setAlignment(Pos.CENTER);
            TextField playerPositionField = new TextField();
            playerPositionField.setAlignment(Pos.CENTER);
            TextField playerRatingField = new TextField();
            playerRatingField.setAlignment(Pos.CENTER);
            Label response = new Label();
            response.setAlignment(Pos.CENTER);
            Button add = new Button("Add Player");
            Button returnBack = new Button("Return");
            returnBack.setOnAction(g -> addPlayerStage.close());

            add.setOnAction(f -> {
                String teamName = teamComboBox.getValue();
                String playerName = playerNameField.getText();
                String playerPosition = playerPositionField.getText();
                String playerRating = playerRatingField.getText();

                if (teamName == null || teamName.isEmpty() || playerName.isEmpty() || playerPosition.isEmpty() || playerRating.isEmpty()) {
                    response.setText("All fields must be filled");
                } else if (!league.doesTeamExist(teamName)) {
                    response.setText("Team does not exist");
                } else {
                    try {
                        double rating = Double.parseDouble(playerRating);
                        Team team = league.getTeamByName(teamName);

                        if (team != null) {
                            boolean nameExistsInOtherTeam = team.hasPlayerWithName(playerName);
                            if (nameExistsInOtherTeam) {
                                response.setText("Player name is already used in another team");
                            } else {
                                if ((team.addPlayer(new Player(playerName, playerPosition, rating), league, response)) == false ) {
                                    returnBack.setOnAction(g -> addPlayerStage.close());
                                }
                            }
                        } else {
                            response.setText("Team not found");
                        }
                    } catch (NumberFormatException ex) {
                        response.setText("Player rating must be a number");
                    }
                }
            });

            addPlayerPanel.getChildren().addAll(teamComboBox, new Label("Enter Player Name:"), playerNameField, new Label("Enter Player Position:"), playerPositionField, new Label("Enter Player Rating:"), playerRatingField, add, returnBack, response);
            Scene addPlayerScene = new Scene(addPlayerPanel, 300, 350);
            addPlayerStage.setScene(addPlayerScene);
            addPlayerStage.setTitle("Add Player");
            addPlayerStage.show();
        });
        removePlayerBtn.setOnAction(e -> {
            Stage removePlayerStage = new Stage();
            VBox removePlayerPanel = new VBox(10);
            removePlayerPanel.setPadding(new Insets(10));

            Image backgroundImage = new Image("E:\\OOP\\League_Final_Entry\\League_Final_Entry\\src\\main\\java\\com\\example\\league_final_entry\\OIP.jpeg");
            BackgroundSize backgroundSize = new BackgroundSize(500, 500, false, false, false, false);
            BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImg);
            removePlayerPanel.setBackground(background);

            ComboBox<String> teamComboBox = new ComboBox<>();
            teamComboBox.setPromptText("Select Team");
            for (Team team : league.getTeams()) {
                teamComboBox.getItems().add(team.getTeamName());
            }

            ComboBox<String> playerComboBox = new ComboBox<>();
            playerComboBox.setPromptText("Select Player");

            Label response = new Label();
            response.setAlignment(Pos.CENTER);
            Button removeBtn = new Button("Remove Player");
            removeBtn.setAlignment(Pos.CENTER);
            Button returnBack = new Button("Return");
            returnBack.setAlignment(Pos.CENTER);
            returnBack.setOnAction(g -> removePlayerStage.close());

            removeBtn.setOnAction(f -> {
                String selectedTeam = teamComboBox.getValue();
                String selectedPlayer = playerComboBox.getValue();

                if (selectedTeam == null || selectedTeam.isEmpty()) {
                    response.setText("Please select a team.");
                    return;
                }
                if (selectedPlayer == null || selectedPlayer.isEmpty()) {
                    response.setText("Please select a player.");
                    return;
                }

                Team team = league.findTeamByName(selectedTeam);
                if (team == null) {
                    response.setText("Selected Team not found.");
                    return;
                }

                Player player = team.findPlayerByName(selectedPlayer);
                if (player == null) {
                    response.setText("Selected player not found in team");
                    return;
                }

                team.removePlayer(player);

                response.setText("Player removed: "+ selectedPlayer + " from team: "  + selectedTeam);
                playerComboBox.getItems().clear();
            });

            teamComboBox.setOnAction(event -> {
                String selectedTeam = teamComboBox.getValue();
                playerComboBox.getItems().clear();
                if (selectedTeam != null) {
                    Team team = league.findTeamByName(selectedTeam);
                    if (team != null) {
                        for (Player player : team.getPlayers()) {
                            playerComboBox.getItems().add(player.getName());
                        }
                    }
                }
            });

            removePlayerPanel.getChildren().addAll(teamComboBox, playerComboBox,response,removeBtn, returnBack);

            Scene removePlayerScene = new Scene(removePlayerPanel, 300, 350);
            removePlayerStage.setScene(removePlayerScene);
            removePlayerStage.setTitle("Remove Player");
            removePlayerStage.show();
        });
        editPlayerBtn.setOnAction(e -> {
            Stage editPlayerStage = new Stage();
            VBox editPlayerPanel = new VBox(10);
            editPlayerPanel.setPadding(new Insets(10));

            Image backgroundImage = new Image("E:\\OOP\\League_Final_Entry\\League_Final_Entry\\src\\main\\java\\com\\example\\league_final_entry\\OIP.jpeg");
            BackgroundSize backgroundSize = new BackgroundSize(500, 500, false, false, false, false);
            BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImg);
            editPlayerPanel.setBackground(background);

            ComboBox<String> teamComboBox = new ComboBox<>();
            teamComboBox.setPromptText("Select Team");
            for (Team team : league.getTeams()) {
                teamComboBox.getItems().add(team.getTeamName());
            }

            ComboBox<String> playerComboBox = new ComboBox<>();
            playerComboBox.setPromptText("Select Player");

            Label nameLabel = new Label("Name:");
            nameLabel.setAlignment(Pos.CENTER);
            TextField nameField = new TextField();
            nameField.setAlignment(Pos.CENTER);

            Label positionLabel = new Label("Position:");
            positionLabel.setAlignment(Pos.CENTER);
            TextField positionField = new TextField();
            positionField.setAlignment(Pos.CENTER);

            Label ratingLabel = new Label("Rating:");
            ratingLabel.setAlignment(Pos.CENTER);
            TextField ratingField = new TextField();
            ratingField.setAlignment(Pos.CENTER);
            Label response = new Label();
            response.setAlignment(Pos.CENTER);

            Button editBtn = new Button("Edit Player");
            Button returnBack = new Button("Return");
            returnBack.setOnAction(g -> editPlayerStage.close());

            editBtn.setOnAction(f -> {
                String selectedTeam = teamComboBox.getValue();
                String selectedPlayer = playerComboBox.getValue();

                if (selectedTeam == null || selectedTeam.isEmpty()) {
                    response.setText("Please select a team.");
                    return;
                }
                if (selectedPlayer == null || selectedPlayer.isEmpty()) {
                    response.setText("Please select a player.");
                    return;
                }

                Team team = league.findTeamByName(selectedTeam);
                if (team == null) {
                    response.setText("Selected Team not found.");
                    return;
                }

                Player player = team.findPlayerByName(selectedPlayer);
                if (player == null) {
                    response.setText("Selected player not found in team");
                    return;
                }

                String newName = nameField.getText();
                String newPosition = positionField.getText();
                double newRating = Double.parseDouble(ratingField.getText());


                player.setName(newName);
                player.setPosition(newPosition);
                player.setRating(newRating);

                response.setText("Player details updated for " + selectedPlayer);
            });

            teamComboBox.setOnAction(event -> {
                String selectedTeam = teamComboBox.getValue();
                playerComboBox.getItems().clear();
                if (selectedTeam != null) {
                    Team team = league.findTeamByName(selectedTeam);
                    if (team != null) {
                        for (Player player : team.getPlayers()) {
                            playerComboBox.getItems().add(player.getName());
                        }
                    }
                }
            });

            editPlayerPanel.getChildren().addAll(teamComboBox, playerComboBox, nameLabel, nameField,
                    positionLabel, positionField, ratingLabel, ratingField, response, editBtn, returnBack);

            Scene editPlayerScene = new Scene(editPlayerPanel, 300, 350);
            editPlayerStage.setScene(editPlayerScene);
            editPlayerStage.setTitle("Edit Player");
            editPlayerStage.show();
        });

        showTeamInfoBtn.setOnAction(e -> {
            Stage showTeamInfoStage = new Stage();
            VBox showTeamInfoPanel = new VBox(10);
            showTeamInfoPanel.setPadding(new Insets(10));

            Image backgroundImage = new Image("E:\\OOP\\League_Final_Entry\\League_Final_Entry\\src\\main\\java\\com\\example\\league_final_entry\\OIP.jpeg");
            BackgroundSize backgroundSize = new BackgroundSize(600, 400, false, false, false, false);
            BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImg);
            showTeamInfoPanel.setBackground(background);

            ComboBox<String> teamComboBox = new ComboBox<>();
            teamComboBox.setPromptText("Select Team");
            for (Team team : league.getTeams()) {
                teamComboBox.getItems().add(team.getTeamName());
            }

            Label teamNameLabel = new Label();
            Label numPlayersLabel = new Label();
            Label avgRatingLabel = new Label();

            TableView<Player> playerTableView = new TableView<>();
            playerTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn<Player, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<Player, String> positionColumn = new TableColumn<>("Position");
            positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
            TableColumn<Player, Double> ratingColumn = new TableColumn<>("Rating");
            ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
            playerTableView.getColumns().addAll(nameColumn, positionColumn, ratingColumn);

            TextField searchField = new TextField();
            Button searchBtn = new Button("Search");
            Button returnBack = new Button("Return");

            returnBack.setOnAction(f -> showTeamInfoStage.close());
            searchBtn.setOnAction(g -> {
                String searchText = searchField.getText().toLowerCase();
                if (!searchText.isEmpty()) {
                    ObservableList<Player> filteredPlayers = playerTableView.getItems().filtered(player ->
                            player.getName().toLowerCase().contains(searchText) ||
                                    player.getPosition().toLowerCase().contains(searchText) ||
                                    String.valueOf(player.getRating()).toLowerCase().contains(searchText));
                    playerTableView.setItems(filteredPlayers);
                }
            });

            teamComboBox.setOnAction(event -> {
                String selectedTeam = teamComboBox.getValue();
                if (selectedTeam != null) {
                    Team team = league.findTeamByName(selectedTeam);
                    if (team != null) {
                        teamNameLabel.setText("Team Name: " + team.getTeamName());
                        numPlayersLabel.setText("Number of Players: " + team.getPlayers().size());
                        double totalRating = team.getPlayers().stream().mapToDouble(Player::getRating).sum();
                        double avgRating = totalRating / team.getPlayers().size();
                        avgRatingLabel.setText("Average Rating: " + avgRating);

                        playerTableView.setItems(FXCollections.observableArrayList(team.getPlayers()));
                    }
                }
            });

            showTeamInfoPanel.getChildren().addAll(teamComboBox, teamNameLabel, numPlayersLabel, avgRatingLabel,
                    playerTableView, searchField, searchBtn, returnBack);

            Scene showTeamInfoScene = new Scene(showTeamInfoPanel, 600, 400);
            showTeamInfoStage.setScene(showTeamInfoScene);
            showTeamInfoStage.setTitle("Team Information");
            showTeamInfoStage.show();
        });


        teamsManagementPanel.getChildren().addAll(addTeamBtn,removeTeamBtn,editTeamBtn,addPlayerBtn,removePlayerBtn,editPlayerBtn,showTeamInfoBtn,returnButton);
        return teamsManagementPanel;
    }
    private void openTeamManagementWindow() {
        Stage teamManagementStage = new Stage();
        VBox teamManagementPanel = createTeamManagementPanel(teamManagementStage);


        Image backgroundImage = new Image("E:\\OOP\\League_Final_Entry\\League_Final_Entry\\src\\main\\java\\com\\example\\league_final_entry\\OIP.jpeg");
        BackgroundSize backgroundSize = new BackgroundSize(500, 500, false, false, false, false);
        BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImg);
        teamManagementPanel.setBackground(background);

        Scene teamManagementScene = new Scene(teamManagementPanel, 500, 500);
        teamManagementStage.setScene(teamManagementScene);
        teamManagementStage.setTitle("Team Managemenadmint");
        teamManagementStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}