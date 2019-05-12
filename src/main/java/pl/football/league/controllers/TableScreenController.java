package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import pl.football.league.entities.Coach;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class TableScreenController {
    private MainScreenController mainController;
    private EntityManager entityManager;
    private Fan currentUser;
    private List<Team> teams;

    @FXML
    private VBox vbox;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label teamLabel;

    @FXML
    private Label pointsLabel;

    @FXML
    private Label winsLabel;

    @FXML
    private Label drawsLabel;

    @FXML
    private Label losesLabel;

    @FXML
    private Label coachLabel;

    @FXML
    void initialize() {
        setSorts();
        fillTable();
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setCurrentUser(Fan currentUser) {
        this.currentUser = currentUser;
    }

    public void setDependencies(MainScreenController mainScreenController, EntityManager entityManager, Fan currentUser){
        setMainController(mainScreenController);
        setEntityManager(entityManager);
        setCurrentUser(currentUser);
    }

    private void tryLoader(FXMLLoader loader) {
        try {
            Parent root = loader.load();
            mainController.getBorderPane().setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillTable() {
        gridPane.getChildren().remove(0, gridPane.getChildren().size());
        int i = 0;

        for (Team t : teams) {
            Label number = TableControls.Label(60, String.valueOf(teams.indexOf(t) + 1));

            Label teamName = TableControls.LabelVGrow(200, t.getName());
            gridPane.setHgrow(teamName, Priority.ALWAYS);
            gridPane.setVgrow(teamName, Priority.ALWAYS);
            teamName.setOnMouseClicked(event -> {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/teamScreen.fxml"));
                    TeamScreenController teamScreenController = new TeamScreenController();
                    Team team = entityManager.find(Team.class, t.getTeamID());

                    teamScreenController.setDependencies(team, entityManager, mainController, currentUser);
                    loader.setController(teamScreenController);
                    tryLoader(loader);
            });

            Label points = TableControls.Label(30, String.valueOf(t.getPoints()));

            Label wins = TableControls.Label(20, String.valueOf(t.getWins()));

            Label draws = TableControls.Label(20, String.valueOf(t.getDraws()));

            Label loses = TableControls.Label(20, String.valueOf(t.getLoses()));

            Label coachLabel = TableControls.LabelVGrow(200, t.getCoach().getName() + " " + t.getCoach().getSurname());
            coachLabel.setOnMouseClicked( event -> {
                FXMLLoader loader = new FXMLLoader((this.getClass().getResource("/fxml/coachScreen.fxml")));
                CoachScreenController coachScreenController = new CoachScreenController();
                Coach coach = entityManager.find(Coach.class, t.getCoach().getCoachID());

                coachScreenController.setDependencies(coach, entityManager, mainController, currentUser);
                loader.setController(coachScreenController);
                tryLoader(loader);
            });
            gridPane.setHgrow(coachLabel, Priority.ALWAYS);
            gridPane.setVgrow(coachLabel, Priority.ALWAYS);

            gridPane.addRow(i, number, teamName, points, wins, draws, loses, coachLabel);
            i++;
        }
    }

    private void setSorts(){
        teams = entityManager.createQuery("select t from Team t").getResultList();
        teams.sort((Team o1, Team o2) -> {
            return o2.getPoints() - o1.getPoints();
        });

        teamLabel.setOnMouseClicked(event -> {
            teams.sort(Comparator.comparing(Team::getName));
            fillTable();
        });

        pointsLabel.setOnMouseClicked(event -> {
            teams.sort((Team o1, Team o2) -> {
                return o2.getPoints() - o1.getPoints();
            });
            fillTable();
        });

        winsLabel.setOnMouseClicked(event -> {
            teams.sort((Team o1, Team o2) -> {
                return o2.getWins() - o1.getWins();
            });
            fillTable();
        });

        drawsLabel.setOnMouseClicked(event -> {
            teams.sort((Team o1, Team o2) -> {
                return o2.getDraws() - o1.getDraws();
            });
            fillTable();
        });

        losesLabel.setOnMouseClicked(event -> {
            teams.sort((Team o1, Team o2) -> {
                return o2.getLoses() - o1.getLoses();
            });
            fillTable();
        });

        coachLabel.setOnMouseClicked(event -> {
            teams.sort((Team o1, Team o2) -> {
                return o1.getCoach().getSurname().compareTo(o2.getCoach().getSurname());
            });
            fillTable();
        });
    }
}
