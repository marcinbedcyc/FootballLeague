package pl.football.league.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import pl.football.league.entities.Coach;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;


public class TableScreenController {
    private MainScreenController mainController;
    private EntityManager entityManager;
    private Fan currentUser;

    @FXML
    private VBox vbox;

    @FXML
    void initialize(){
        List<Team> teams = entityManager.createQuery("select t from Team t").getResultList();
        teams.sort((Team o1, Team o2)-> {return o2.getPoints() - o1.getPoints();});
        HBox hbox;

        for(Team t: teams) {
            hbox = new HBox();
            hbox.setSpacing(0);
            hbox.setAlignment(Pos.CENTER);

            Label number = TableControls.Label(60, String.valueOf(teams.indexOf(t)+1));

            Label teamName = TableControls.LabelVGrow(200, t.getName());
            hbox.setHgrow(teamName, Priority.ALWAYS);
            teamName.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/teamScreen.fxml"));
                    TeamScreenController teamScreenController = new TeamScreenController();
                    Team team = entityManager.find(Team.class, t.getTeamID());

                    teamScreenController.setDependencies(team, entityManager,mainController, currentUser);
                    loader.setController(teamScreenController);
                    tryLoader(loader);
                }
            });

            Label points = TableControls.Label(30, String.valueOf(t.getPoints()));

            Label wins = TableControls.Label(20, String.valueOf(t.getWins()));

            Label draws = TableControls.Label(20, String.valueOf(t.getDraws()));

            Label loses = TableControls.Label(20, String.valueOf(t.getLoses()));

            Label coachLabel = TableControls.LabelVGrow(200, t.getCoach().getName() + " " + t.getCoach().getSurname());
            coachLabel.setOnMouseClicked((MouseEvent event)-> {
                    FXMLLoader loader = new FXMLLoader((this.getClass().getResource("/fxml/coachScreen.fxml")));
                    CoachScreenController coachScreenController = new CoachScreenController();
                    Coach coach = entityManager.find(Coach.class, t.getCoach().getCoachID());

                    coachScreenController.setDependencies(coach, entityManager);
                    loader.setController(coachScreenController);
                    tryLoader(loader);
            });
            hbox.setHgrow(coachLabel, Priority.ALWAYS);

            hbox.getChildren().addAll(number, new Separator(Orientation.VERTICAL), teamName, new Separator(Orientation.VERTICAL),
                    points, new Separator(Orientation.VERTICAL), wins, new Separator(Orientation.VERTICAL), draws,
                    new Separator(Orientation.VERTICAL), loses, new Separator(Orientation.VERTICAL), coachLabel);
            vbox.getChildren().add(hbox);
        }
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Fan getCurrentUser() {
        return currentUser;
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
