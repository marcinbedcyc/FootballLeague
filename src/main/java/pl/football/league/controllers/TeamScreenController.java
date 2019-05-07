package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.football.league.entities.Coach;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

public class TeamScreenController {
    private Team team;
    private Coach coach;
    private EntityManager entityManager;
    private MainScreenController mainScreenController;
    private Fan currentUser;
    private List<Footballer> footballers;

    @FXML
    private Label name;

    @FXML
    private Label coachLabel;

    @FXML
    private Label date;

    @FXML
    private Label points;

    @FXML
    private Label wins;

    @FXML
    private Label draws;

    @FXML
    private Label loses;

    @FXML
    private VBox footbalersVBox;

    @FXML
    private VBox fansVBox;

    @FXML
    private Button supportButton;

    @FXML
    private Button stopSupportButton;

    @FXML
    void initialize() {
        footballers = entityManager.createQuery("select f from Footballer f").getResultList();
        setTeamInfo();
        setFootballers();
        supportButton.setDisable(false);
        stopSupportButton.setDisable(true);
        setFans();
        coachLabel.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/coachScreen.fxml"));
            CoachScreenController coachScreenController = new CoachScreenController();

            coachScreenController.setDependencies(team.getCoach(),entityManager, mainScreenController, currentUser);
            loader.setController(coachScreenController);
            tryLoader(loader);
        });
    }

    private void setFans(){
        fansVBox.getChildren().remove(0, fansVBox.getChildren().size());

        if(team.getTeamFans().isEmpty()){
            Label emptyLabel = TableControls.Label(150, "Brak kibiców");
            emptyLabel.setMaxWidth(1.7976931348623157E308);
            fansVBox.getChildren().add(emptyLabel);
        }
        else {
            for (Fan f : team.getTeamFans()) {
                Label fanLabel = TableControls.LabelVGrow(150, f.getNickname());
                fanLabel.setOnMouseClicked(event->{
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/fanScreen.fxml"));
                    FanScreenController fanScreenController = new FanScreenController();

                    fanScreenController.setDependencies(f, entityManager, mainScreenController, currentUser);
                    loader.setController(fanScreenController);
                    tryLoader(loader);
                });
                if (f.getFanID() == currentUser.getFanID()) {
                    supportButton.setDisable(true);
                    stopSupportButton.setDisable(false);
                }
                fansVBox.getChildren().add(fanLabel);
            }
        }
    }

    private void setFootballers(){
        for(Footballer f : footballers){
            if(f.getTeam().getTeamID() == team.getTeamID()){
                Label footballerLabel = TableControls.LabelVGrow(150, f.getName() + " " + f.getSurname());
                footballerLabel.setOnMouseClicked(event->{
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/footballerScreen.fxml"));
                    FootballerScreenController footballerScreenController = new FootballerScreenController();
                    Footballer footballer = entityManager.find(Footballer.class, f.getFootballerID());

                    footballerScreenController.setDependency(footballer, currentUser, entityManager, mainScreenController);
                    loader.setController(footballerScreenController);
                    tryLoader(loader);
                });
                footbalersVBox.getChildren().add(footballerLabel);
            }
        }
    }

    @FXML
    void stopSupport() {
        entityManager.getTransaction().begin();
        team.getTeamFans().remove(currentUser);
        currentUser.getSupportedTeams().remove(team);
        entityManager.getTransaction().commit();
        supportButton.setDisable(false);
        stopSupportButton.setDisable(true);
        this.setFans();
    }

    @FXML
    void support() {
        entityManager.getTransaction().begin();
        team.getTeamFans().add(currentUser);
        currentUser.getSupportedTeams().add(team);
        entityManager.getTransaction().commit();
        supportButton.setDisable(true);
        stopSupportButton.setDisable(false);
        this.setFans();
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public MainScreenController getMainScreenController() {
        return mainScreenController;
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public Fan getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Fan currentUser) {
        this.currentUser = currentUser;
    }

    public void setDependencies(Team team, EntityManager entityManager, MainScreenController mainScreenController, Fan user){
        setCurrentUser(user);
        setMainScreenController(mainScreenController);
        setEntityManager(entityManager);
        setTeam(team);
    }

    private void tryLoader(FXMLLoader loader){
        try {
            Parent root = loader.load();
            mainScreenController.getBorderPane().setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTeamInfo(){
        name.setText(team.getName());
        coachLabel.setText(team.getCoach().getName() + " " + team.getCoach().getSurname());
        String result;
        if(team.getCreationDate() == null){
            result = "-";
        }
        else {
            result = team.getCreationDate().toString();
        }
        date.setText(result);
        points.setText(String.valueOf(team.getPoints()));
        wins.setText(String.valueOf(team.getWins()));
        draws.setText(String.valueOf(team.getDraws()));
        loses.setText(String.valueOf(team.getLoses()));
    }
}
