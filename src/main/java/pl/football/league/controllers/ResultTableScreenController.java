package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Match;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class ResultTableScreenController {
    private MainScreenController mainController;
    private EntityManager entityManager;
    private Fan currentUser;
    private List<Match> matches;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label homeLabel;

    @FXML
    private Label awayLabel;

    @FXML
    private Label dateLabel;

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

    private void tryLoader(FXMLLoader loader){
        try {
            Parent root = loader.load();
            mainController.getBorderPane().setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillTable(){
        int i = 0;
        gridPane.getChildren().remove(0, gridPane.getChildren().size());
        for(Match m:matches){
            Label homeLabel = TableControls.LabelVGrow(150, m.getMatchID().getHome().getName());
            gridPane.setHgrow(homeLabel, Priority.ALWAYS);
            homeLabel.setOnMouseClicked(event->{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/teamScreen.fxml"));
                TeamScreenController teamScreenController = new TeamScreenController();
                Team teamHome = entityManager.find(Team.class, m.getMatchID().getHome().getTeamID());

                teamScreenController.setDependencies(teamHome, entityManager, mainController, currentUser);
                loader.setController(teamScreenController);
                tryLoader(loader);
            });

            Label awayLabel = TableControls.LabelVGrow(150, m.getMatchID().getAway().getName());
            gridPane.setHgrow(awayLabel, Priority.ALWAYS);
            awayLabel.setOnMouseClicked(event->{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/teamScreen.fxml"));
                TeamScreenController teamScreenController = new TeamScreenController();
                Team teamAway = entityManager.find(Team.class, m.getMatchID().getAway().getTeamID());

                teamScreenController.setDependencies(teamAway, entityManager, mainController, currentUser);
                loader.setController(teamScreenController);
                tryLoader(loader);
            });

            Label resultLabel = TableControls.Label(150, m.getResultHome() + " : " + m.getResultAway());
            resultLabel.setMaxWidth(1.7976931348623157E308);
            resultLabel.setMaxHeight(1.7976931348623157E308);
            gridPane.setHgrow(resultLabel, Priority.ALWAYS);

            Label dateLabel = TableControls.Label(150, m.getMatchDate().toString());
            dateLabel.setMaxWidth(1.7976931348623157E308);
            dateLabel.setMaxHeight(1.7976931348623157E308);
            gridPane.setHgrow(dateLabel, Priority.ALWAYS);

            gridPane.addRow(i, homeLabel, resultLabel, awayLabel, dateLabel);
            i++;
        }
    }

    private void setSorts(){
        matches = entityManager.createQuery("select M from Match M").getResultList();

        homeLabel.setOnMouseClicked(event -> {
            matches.sort((Match m1, Match m2) -> {
                return m1.getMatchID().getHome().getName().compareTo(m2.getMatchID().getHome().getName());
            });
            fillTable();
        });

        awayLabel.setOnMouseClicked(event -> {
            matches.sort((Match m1, Match m2) -> {
                return m1.getMatchID().getAway().getName().compareTo(m2.getMatchID().getAway().getName());
            });
            fillTable();
        });

        dateLabel.setOnMouseClicked(event -> {
            matches.sort(Comparator.comparing(Match::getMatchDate));
            fillTable();
        });
    }
}