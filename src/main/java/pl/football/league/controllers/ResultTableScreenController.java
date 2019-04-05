package pl.football.league.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Match;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

public class ResultTableScreenController {
    private MainScreenController mainController;
    private EntityManager entityManager;
    private Fan currentUser;

    @FXML
    private VBox vbox;

    @FXML
    void initialize() {
        entityManager.getTransaction().begin();
        List<Match> matches = entityManager.createQuery("select M from Match M").getResultList();
        entityManager.getTransaction().commit();

        HBox hBox;
        for(Match m:matches){
            hBox = new HBox();
            hBox.setSpacing(0);
            hBox.setAlignment(Pos.CENTER);

            Label home = TableControls.LabelVGrow(150, m.getMatchID().getHome().getName());
            hBox.setHgrow(home, Priority.ALWAYS);
            home.setOnMouseClicked((MouseEvent event)->{
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/teamScreen.fxml"));
                    TeamScreenController teamScreenController = new TeamScreenController();
                    Team teamHome = entityManager.find(Team.class, m.getMatchID().getHome().getTeamID());

                    teamScreenController.setDependencies(teamHome, entityManager, mainController, currentUser);
                    loader.setController(teamScreenController);
                    tryLoader(loader);
            });

            Label away = TableControls.LabelVGrow(150, m.getMatchID().getAway().getName());
            hBox.setHgrow(away, Priority.ALWAYS);
            away.setOnMouseClicked((MouseEvent event)->{
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/teamScreen.fxml"));
                    TeamScreenController teamScreenController = new TeamScreenController();
                    Team teamAway = entityManager.find(Team.class, m.getMatchID().getAway().getTeamID());

                    teamScreenController.setDependencies(teamAway, entityManager, mainController, currentUser);
                    loader.setController(teamScreenController);
                    tryLoader(loader);
            });

            Label result = TableControls.LabelVGrow(150, m.getResultHome() + " : " + m.getResultAway());
            hBox.setHgrow(result, Priority.ALWAYS);

            Label date = TableControls.LabelVGrow(150, m.getMatchDate().toString());
            hBox.setHgrow(date, Priority.ALWAYS);

            hBox.getChildren().addAll(home, new Separator(Orientation.VERTICAL), result, new Separator(Orientation.VERTICAL),
                    away, new Separator(Orientation.VERTICAL), date);
            vbox.getChildren().add(hBox);
        }
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
}