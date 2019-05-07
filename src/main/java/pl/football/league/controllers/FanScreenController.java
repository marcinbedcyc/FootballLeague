package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Set;

public class FanScreenController {
    private Fan fan;
    private Fan currentUser;
    private EntityManager entityManager;
    private MainScreenController mainScreenController;
    private Set<Footballer> supportedFootballers;
    private Set<Team> supportedTeams;

    @FXML
    private Label titleLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private Label nicknameLabel;

    @FXML
    private VBox footballersVBox;

    @FXML
    private VBox teamsVBox;

    @FXML
    void initialize() {
        setFanInfo();
        supportedFootballers = fan.getSupportedFootballers();
        supportedTeams = fan.getSupportedTeams();

        setFootballers();
        setTeams();
    }

    public MainScreenController getMainScreenController() {
        return mainScreenController;
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public void setFan(Fan fan) {
        this.fan = fan;
    }

    public void setCurrentUser(Fan currentUser) {
        this.currentUser = currentUser;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setDependencies(Fan fan, EntityManager entityManager, MainScreenController mainScreenController, Fan currentUser){
        setFan(fan);
        setEntityManager(entityManager);
        setMainScreenController(mainScreenController);
        setCurrentUser(currentUser);
    }

    private void setFanInfo(){
        titleLabel.setText(fan.getName() + " " + fan.getSurname() + " ( " + fan.getNickname() + " )");
        nameLabel.setText(fan.getName());
        surnameLabel.setText(fan.getSurname());
        nicknameLabel.setText(fan.getNickname());

        String result = String.valueOf(fan.getAge());
        if(result == null)  result = "-";
        ageLabel.setText(result);
    }

    private void tryLoader(FXMLLoader loader){
        try {
            Parent root = loader.load();
            mainScreenController.getBorderPane().setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setFootballers(){
        if(supportedFootballers.isEmpty()){
            Label emptyLabel = TableControls.Label(150, "Brak wspieranych piłkarzy");
            emptyLabel.setMaxWidth(1.7976931348623157E308);
            footballersVBox.getChildren().add(emptyLabel);
        }
        else {
            for (Footballer f : supportedFootballers) {
                Label footballerLabel = TableControls.LabelVGrow(150, f.getName() + " " + f.getSurname());
                footballerLabel.setOnMouseClicked(event -> {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/footballerScreen.fxml"));
                    FootballerScreenController footballerScreenController = new FootballerScreenController();
                    Footballer footballer = entityManager.find(Footballer.class, f.getFootballerID());

                    footballerScreenController.setDependency(footballer, currentUser, entityManager, mainScreenController);
                    loader.setController(footballerScreenController);
                    tryLoader(loader);
                });
                footballersVBox.getChildren().add(footballerLabel);
            }
        }
    }

    private void setTeams(){
        if(supportedTeams.isEmpty()){
            Label emptyLabel = TableControls.Label(150, "Brak wspieranych drużyn");
            emptyLabel.setMaxWidth(1.7976931348623157E308);
            teamsVBox.getChildren().add(emptyLabel);
        }
        else {
            for (Team t : supportedTeams) {
                Label teamLabel = TableControls.LabelVGrow(150, t.getName());
                teamLabel.setOnMouseClicked(event -> {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/teamScreen.fxml"));
                    TeamScreenController teamScreenController = new TeamScreenController();
                    Team team = entityManager.find(Team.class, t.getTeamID());

                    teamScreenController.setDependencies(team, entityManager, mainScreenController, currentUser);
                    loader.setController(teamScreenController);
                    tryLoader(loader);
                });
                teamsVBox.getChildren().add(teamLabel);
            }
        }
    }
}
